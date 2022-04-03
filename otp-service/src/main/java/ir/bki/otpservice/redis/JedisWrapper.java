package ir.bki.otpservice.redis;

/**
 * @author Mahdi Sharifi
 * @version 1.0.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 9/19/2021
 */
//CMD -> redis-cli -h 10.0.84.140 -p 6379 -a AaA@BBcdfe1
//       redis-cli -h 10.0.84.140 -p 6379 -a AaA@BBcdfe1 monitor
//       redis-benchmark -c 10 -n 100000 -q -h 10.0.84.140 -p 6379 -a AaA@BBcdfe1
//https://www.alibabacloud.com/help/doc-detail/98726.htm

import ir.bki.otpservice.util.GsonModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import redis.clients.jedis.*;

import java.net.InetAddress;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
@Slf4j
public enum JedisWrapper {
    INSTANCE;

    JedisWrapper() {
    }

    private static final AtomicInteger failedCount = new AtomicInteger(0);
//    private static final Logger LOGGER = Logger.getRe(ir.bki.redis.alibaba.JedisWrapper.class);
//    private static final Logger LOGGER_STACKTRACE = Logger.getLogger(ir.bki.redis.alibaba.JedisWrapper.class);

    private static final String MASTER_NAME = "mymaster";
    private static final String PASSWORD = "Mani3280@";
    private static final Set<String> sentinels;

    static {
        sentinels = new HashSet<>();
        sentinels.add("10.0.84.139:26379");
        sentinels.add("10.0.84.140:26379");
        sentinels.add("10.0.84.143:26379");
    }

    //setting up a pool of connections to connect to the Redis server
    //a pool that is thread safe and reliable as long as you return the resource to the pool when you are done with it
    //This allows you to talk to redis from multiple threads while still getting the benefits of reused connections.
    //The JedisPool object is thread-safe and can be used from multiple threads at the same time.
    //This pool should be configured once and reused.
    //Make sure to return the Jedis instance back to the pool when done, otherwise you will leak the connection.
    //https://gist.github.com/JonCole/925630df72be1351b21440625ff2671f
    /**
     * We have seen a few cases where connections in the pool get into a bad state. As a failsafe,
     * you may want to re-create the JedisPool if you see connection errors that continue for longer than 30 seconds.
     */
//    private static JedisSentinelPool pool = new JedisSentinelPool(MASTER_NAME, sentinels, buildPoolConfig(), Protocol.DEFAULT_TIMEOUT, "MPCRedis_UAT","riuerofoeihfoi09280828rplwj08r-023",0); // TODO ADD TO JMX for Recreate,
    private static JedisPool pool = new JedisPool(buildPoolConfig(),"127.0.0.1", 6379, 5,PASSWORD);

    public static void considerDestroyAndCreatePool() {
        if (failedCount.get() == 2) {
            recreatePool();
            failedCount.set(0);
        }
    }

    public static void recreatePool() {//TODO use in JMX

        try {
            pool.close();
        } catch (Exception ex) {
            log.error("Exception in close pool. " + ex.getMessage());
            log.error("Exception in close pool. ", ex);
        }
        destroyPool();
        log.error("Creating pool..." + sentinels);
        pool = new JedisPool(buildPoolConfig(),"127.0.0.1", 6379, 5,PASSWORD); // TODO ADD TO JMX for Recreate
        RedisMonitoring monitoring = new RedisMonitoring();
        log.error("Create Pool Success. Metrics are:" + monitoring.toString());
    }

    private static JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        /**
         * https://www.alibabacloud.com/help/doc-detail/98726.htm
         * The maximum number of connections.
         * To set a proper value of maxTotal, take note of the following factors:
         *
         * 1-The expected concurrent connections based on your business requirements.
         * 2-The amount of time that is consumed by the client to run the command.
         * 3-The limit of Redis resources. For example, if you multiply maxTotal by the number of nodes (ECS instances),
         * the product must be smaller than the supported maximum number of connections in Redis.
         * You can view the maximum connections on the Instance Information page in the ApsaraDB for Redis console.
         * 4-The resource that is consumed to create and release connections.
         * If the number of connections that are created and released for a request is large,
         * the processes that are performed to create and release connections are adversely affected.
         * */
        poolConfig.setMaxTotal(60);
        /**
         * https://www.alibabacloud.com/help/doc-detail/98726.htm
         * maxIdle is the actual maximum number of connections required by workloads
         * The connection pool achieves its best performance when maxTotal = maxIdle.
         * https://medium.com/geekculture/the-pooling-of-connections-in-redis-e8188335bf64
         * This is the max number of connections that can be idle in the pool without being quickly closed.
         * If not set, the default value is 8. The suggested value for this is same as maxTotal to
         * help avoid unnecessary connection costs when your application has many bursts of load in a
         * short period of time. If a connection is idle for a long time, it will still be evicted until the
         * idle connection count hits minIdle. The minIdle is explained below.
         * */
        poolConfig.setMaxIdle(20);
        /**
         * his is the number of connections which are ready for immediate use. They remain in the pool even
         * when the load has decreased. If not set, the default is 0.
         * When choosing a value, consider your steady-state simultaneous requests to Redis.
         * For instance, if your application is calling into Redis from 10 threads simultaneously, then
         * you should set this to at least 10 ( a bit higher to give you some room).
         * */
        poolConfig.setMinIdle(20);
        /**
         * Controls whether or not the connection is tested before it is returned from the pool.
         * The default is false. Setting to true may increase resilience to connection blips but may also have
         * a performance cost when taking connections from the pool.
         *
         * Specifies whether to validate connections by using the PING command before the connections are borrowed from the pool.
         * Invalid connections are removed from the pool.
         * We recommend that you set this parameter to false when the workload is heavy.
         * This allows you to reduce the overhead of a ping test.
         * */
        poolConfig.setTestOnBorrow(true);
        /**
         * https://www.alibabacloud.com/help/doc-detail/98726.htm
         * Specifies whether to validate connections by using the PING command before the connections are returned to the pool.
         *  Invalid connections are removed from the pool.
         *We recommend that you set this parameter to false when the workload is heavy.
         * This allows you to reduce the overhead of a ping test.
         *  */
        poolConfig.setTestOnReturn(true);
        /**
         * Specifies whether to validate connections by running the PING command during the process of idle resource detection.
         * Invalid connections are evicted.
         * */
        poolConfig.setTestWhileIdle(true);
        poolConfig.setTestOnCreate(true);
        /**
         * The minimum idle time of a resource in the resource pool. Unit: milliseconds.
         * When the upper limit is reached, the idle resource is evicted.
         * Default: 1,800,000 (30 minutes)
         * The default value is suitable for most cases.
         * You can also use the configuration in JeidsPoolConfig based on your business requirements.
         * */
        poolConfig.setMinEvictableIdleTime(Duration.ofMinutes(10));
        /**
         * Specifies the cycle of idle resources detection. Unit: milliseconds.
         *We recommend that you set this parameter to a proper value.
         * You can also use the default configuration in JedisPoolConfig.
         * */
        poolConfig.setTimeBetweenEvictionRuns(Duration.ofSeconds(30));
        /**
         * The number of resources to be detected within each cycle.
         * */
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }

    public static void set(String key, String value) {
        Jedis jedis = pool.getResource();
        jedis.set(key, value);
        returnConnection(jedis);
    }
    public static void set(String key, String value,long timeoutSecond) {
        Jedis jedis = pool.getResource();
        jedis.set(key, value);
        jedis.expire(key,timeoutSecond);
        returnConnection(jedis);
    }
    public static void set(String key, String value,Duration duration) {
        set(key,value,duration.getSeconds());
    }

    public static String get(String key) {
        Jedis jedis = pool.getResource();
        String result = jedis.get(key);
        returnConnection(jedis);
        return result;
    }
    public static long dbSize() {
        Jedis jedis = pool.getResource();
        long result = JedisWrapper.getJedis().dbSize();
        returnConnection(jedis);
        return result;
    }

    public static void setHash(String key,String field, String value) {
        Jedis jedis = pool.getResource();
        jedis.hset(key,field, value);
        returnConnection(jedis);
    }
    public static String getHash(String key,String field) {
        Jedis jedis = pool.getResource();
        String result = jedis.hget(key ,field);
        returnConnection(jedis);
        return result;
    }
    public static Map<String, String> getAllHash(String key) {
        Jedis jedis = pool.getResource();
        Map<String, String> result = jedis.hgetAll(key);
        returnConnection(jedis);
        return result;
    }
    public static JedisPool getPool() {
        return pool;
    }

    // you should take care of destroying the pool to avoid leaks when the application is being shutdown.
    public static void destroyPool() {
        try {
            pool.destroy();
        } catch (Exception ex) {
            log.error("Exception in close pool. " + ex.getMessage());
            log.error("Exception in close pool. ", ex);
        }
    }

    /**
     * Get Redis Connection
     * https://docs.redis.com/latest/rs/references/client_references/client_java/
     * Jedis isn’t thread-safe, and the same Jedis instance shouldn’t be used from different threads.
     * <p>
     * Don't use the same Jedis connection instance from multiple threads at the same time.
     * Using the same Jedis instance from multiple threads at the same time will result in socket connection errors/resets
     * or strange error messages like "expected '$' but got ' '".
     * https://gist.github.com/JonCole/925630df72be1351b21440625ff2671f
     */
    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void returnConnection(Jedis jedis) {
        pool.returnResource(jedis);
    }
    public static String callPing() {
        Jedis jedis = getJedis();
        String pong=jedis.ping();
//        if (pong.equalsIgnoreCase("PONG")) {
//            System.out.println("PING-> PONG");
//        }
        returnConnection(jedis);
        return pong;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class RedisMonitoring extends GsonModel {

//        The number of instances currently idle in this pool, -1 if the pool is inactive.
        private Integer connectionIdle;
        //The number of instances currently borrowed from this pool, -1 if the pool is inactive.
        private Integer connectionActive;
        //Returns an estimate of the number of threads currently blocked waiting for a resource from this pool.
        private Integer connectionWaiters;
        //Returns the maximum waiting time spent by threads to obtain a resource from this pool.
        private Long maxBorrowWaitTime;
        //Returns the mean waiting time spent by threads to obtain a resource from this pool.
        private Long meanBorrowWaitTime;
        private String ping;//PONG
        private Long dbSize;
        //        private String info;
        private String master;
        private String exception;
        private String serverAddress;

        private Map<String, Object> server = new HashMap<>();
        private Map<String, Object> clients = new HashMap<>();
        private Map<String, Object> memory = new HashMap<>();
        private Map<String, Object> persistence = new HashMap<>();
        private Map<String, Object> stats = new HashMap<>();
        private Map<String, Object> replication = new HashMap<>();
        private Map<String, Object> cpu = new HashMap<>();
        private Map<String, Object> modules = new HashMap<>();
        private Map<String, Object> errorStats = new HashMap<>();
        private Map<String, Object> cluster = new HashMap<>();
        private Map<String, Object> keyspace = new HashMap<>();
        private Map<String, Object> etc = new HashMap<>();

        public RedisMonitoring() {
            long start= System.currentTimeMillis();
            try {
                try {
                    JedisPool pool = getPool();
                    connectionIdle = pool.getNumIdle();
                    connectionActive = pool.getNumActive();
                    connectionWaiters = pool.getNumWaiters();
                    maxBorrowWaitTime = pool.getMaxBorrowWaitTimeMillis();
                    meanBorrowWaitTime = pool.getMeanBorrowWaitTimeMillis();
                    meanBorrowWaitTime = pool.getMeanBorrowWaitTimeMillis();
                } catch (Exception ex) {
                   ex.printStackTrace();
                    log.error("EXCEPTION GET METRICS: " + ex.getMessage());
                }
                Jedis jedis = getJedis();
                try {
                    String ping = jedis.ping();
                    if ("PONG".equalsIgnoreCase(ping)) {
                        ping = ping;
                        failedCount.set(0);
                    } else {
                        failedCount.incrementAndGet();
                        log.error("ERROR! We Expected PONG But Received " + ping + " ;failedCount: " + failedCount.get());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    failedCount.incrementAndGet();
                    log.error("Exception When We Calling Redis Ping. failedCount: " + failedCount.get() + " ;" + ex.getMessage());
                    log.error("Exception When We Calling Redis Ping. ", ex);
                }
                try {
                    dbSize = jedis.dbSize();

//                    System.out.println(jedis.info());
                    String[] infoArray = Objects.requireNonNull(jedis.info()).split("\\r\\n\\r\\n# ");
                    for (int i = 0; i < infoArray.length; i++) {
                        String[] childArray = Objects.requireNonNull(infoArray[i]).split("\r\n");
//                        System.err.println("------------R:" + i + ": " + " ;child.count(" + i + "): " + childArray.length);//+ infoArray[i]
                        String parent = "etc";
                        for (int j = 0; j < childArray.length; j++) {
                            String child = childArray[j];
                            String[] details = child.split(":");
                            try {
                                if (details.length == 1) {
                                    parent = child.trim();
                                }
                                if (details.length > 1) {
                                    String key = details[0].trim();
                                    Object value = details[1].trim();
                                    try {
                                        if (!"rdb_last_save_time".equalsIgnoreCase(key)
                                        ) {
                                            double temp  = Double.parseDouble(details[1].trim());
                                            value = Math.round(temp*100.0)/100.0;
                                        }
                                    }catch (Exception ignored){
                                    }

                                    switch (parent.toLowerCase()) {
                                        case "# server":
                                            server.put(key, value);
                                            break;
                                        case "clients":
                                            clients.put(key, value);
                                            break;
                                        case "memory":
                                            //mem_fragmentation_ratio
                                            if("used_memory_peak_perc".equalsIgnoreCase(key) ||  "used_memory_dataset_perc".equalsIgnoreCase(key)){
                                                String temp=(String) value;
                                                if(temp.length()>1) {
                                                    double newvalue = Double.parseDouble(temp.substring(0,temp.length()-1));
                                                    memory.put(key+"ent", newvalue);
                                                }
                                            }
                                            try {
                                                if ("used_memory".equalsIgnoreCase(key) || "used_memory_rss".equalsIgnoreCase(key)
                                                        || "used_memory_peak".equalsIgnoreCase(key) || "used_memory_overhead".equalsIgnoreCase(key)
                                                        || "used_memory_startup".equalsIgnoreCase(key) || "used_memory_dataset".equalsIgnoreCase(key)
                                                        || "allocator_allocated".equalsIgnoreCase(key) || "allocator_active".equalsIgnoreCase(key)
                                                        || "allocator_resident".equalsIgnoreCase(key)
                                                ) {
                                                    double temp = (Double) value;
                                                    double newValue = temp / 1024;
                                                    newValue = Math.round(newValue*100.0)/100.0;
                                                    stats.put(key + "_kb", newValue);
                                                    newValue/=1024;
                                                    newValue = Math.round(newValue*100.0)/100.0;
                                                    stats.put(key + "_mb",newValue);
                                                    newValue/=1024;
                                                    newValue = Math.round(newValue*100.0)/100.0;
                                                    stats.put(key + "_gb", newValue);
                                                }
                                            }catch (Exception ignored){}
                                            memory.put(key, value);
                                            break;
                                        case "persistence":
                                            try {
                                                if ("aof_current_size".equalsIgnoreCase(key) ||  "aof_base_size".equalsIgnoreCase(key)
                                                ) {
                                                    double temp = (Double) value;
                                                    double newValue = temp / 1000;
                                                    newValue = Math.round(newValue*100.0)/100.0;
                                                    stats.put(key + "_kilo", newValue);

                                                }
                                            }catch (Exception ignored){}
                                            try {
                                                if ("rdb_last_save_time".equalsIgnoreCase(key)
                                                ) {
                                                    long temp =  Long.parseLong(value+"");
                                                    stats.put(key + "_date", new Date(temp*1000));
//                                                    System.out.println("#KEY: "+key+" ;temp: "+temp+" ;value: "+value);

                                                }
                                            }catch (Exception ignored){
                                                ignored.printStackTrace();
                                            }

                                            persistence.put(key, value);
                                            break;
                                        case "stats":
//                                            keyspace_hits:2144728
//                                            keyspace_misses:0
                                            try {
                                                if ("keyspace_hits".equalsIgnoreCase(key) || "keyspace_misses".equalsIgnoreCase(key)
                                                        || "total_writes_processed".equalsIgnoreCase(key) || "total_reads_processed".equalsIgnoreCase(key)
                                                        || "total_connections_received".equalsIgnoreCase(key) || "total_commands_processed".equalsIgnoreCase(key)
                                                ) {
                                                    double temp = (Double) value;
                                                    double newValue = temp / 1000;
                                                    newValue = Math.round(newValue*100.0)/100.0;
                                                    stats.put(key + "_kilo", newValue);
                                                }
                                            }catch (Exception ignored){}
                                            try {
                                                if ("total_net_input_bytes".equalsIgnoreCase(key) || "total_net_output_bytes".equalsIgnoreCase(key)
                                                ) {
                                                    double temp = (Double) value;
                                                    double newValue = temp / (1024);
                                                    newValue = Math.round(newValue*100.0)/100.0;
                                                    stats.put(key + "_kb", newValue);
                                                    newValue/=1024;
                                                    newValue= Math.round(newValue*100.0)/100.0;
                                                    stats.put(key + "_mb", newValue);
                                                }
                                            }catch (Exception ignored){}
                                            stats.put(key, value);
                                            break;
                                        case "replication":
                                            replication.put(key, value);
                                            break;
                                        case "cpu":
                                            cpu.put(key, value);
                                            break;
                                        case "modules":
                                            modules.put(key, value);
                                            break;
                                        case "errorstats":
                                            try {
                                                //errorstat_XXX: count=XXX

//                                            errorstat_ERR:count=3

                                                key += "_count";
                                                String temp = (String) value;
                                                if (temp.length() > 1) {
                                                    value = Long.parseLong(temp.substring(6));//_count
                                                }
//                                            }
                                            }catch (Exception ex){
                                                log.error("Exception get errorstats counts: "+ex.getMessage());
                                            }
                                            errorStats.put(key, value);
                                            break;
                                        case "cluster":
                                            cluster.put(key, value);
                                            break;
                                        case "keyspace":
                                            //"keyspace":{"db0":"keys\u003d91,expires\u003d0,avg_ttl\u003d0"}
                                            //db0:keys=91,expires=0,avg_ttl=0
                                            if(key.startsWith("db")){
//                                                System.out.println("#key1: "+key+" ;value: "+value);
                                                String temp=(String) value;
                                                String[] fields=temp.split(",");
                                                for (String item:fields) {
                                                    //keys=91
                                                    String[] itemValues=item.split("=");
                                                    if(itemValues.length>0) {
                                                        keyspace.put(key+"_"+itemValues[0], Long.parseLong(itemValues[1]));
                                                    }

                                                }
                                            }
//                                            else
//                                                System.out.println("#key2: "+key+" ;value: "+value);
                                            break;
                                        case "etc":
                                        default:
                                            etc.put(key, value);
                                    }
                                }

                            } catch (Exception ex) {
                                log.error("Exception when putting data in Map of Redis monitoring." ,ex);
                            }
                        }
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                    exception = ex.getMessage();
                } finally {
                    returnConnection(jedis);
                }
                try {
                    this.serverAddress = InetAddress.getLocalHost().getHostAddress();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                exception = ex.getMessage();
            }
            System.out.println("EALPSED TIME: "+(System.currentTimeMillis()-start));
        }
    }
}
