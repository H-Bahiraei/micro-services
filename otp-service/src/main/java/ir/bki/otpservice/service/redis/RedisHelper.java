package ir.bki.otpservice.service.redis;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/6/2022
 */
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
//https://programmer.group/example-of-using-redis-in-spring-boot.html
public interface RedisHelper {
    /**
     * Hash Structure Add Element* @param key * @param hashKey hashKey * @param domain element
     */
    void hashPut(String key, String field, String value);
    /**
     * Hash Structure gets all key-value pairs for the specified key * @param key * @return
     */
    Map<String, String> getAllh(String key);
    /**
     * Hash Structure gets a single element * @param key * @param hashKey * @return
     */
    String get(String key, String hashKey);
    void del(String key, String hashKey);
     void del(String key);
    /**
     * List Structure adds elements to the tail (Right)* @param key * @param domain * @return
     */
    Long listPush(String key, String domain);
    /**
     * List Structure adds elements to the header (Left)* @param key * @param domain * @return
     */
    Long listUnshift(String key, String domain);
    /**
     * List Structure Gets All Elements* @param key * @return
     */
    List<String> listFindAll(String key);
    /**
     * List Structure removes and gets the first element of the array * @param key * @return
     */
    String listLPop(String key);
    void put(String key, String value);
    /**
     * Get Object Entity Class
     * @param key
     * @return
     */
    String get(String key);
    void remove(String key);
    /**
     * Set expiration time* @param key key* @param timeout time* @param timeUnit time unit
     */
    boolean expire(String key, long timeout, TimeUnit timeUnit);
    boolean expire(String key, long timeout);
}