package ir.bki.notificationservice.log;

public enum LoggerNamesEnum {


    kafka("kafkaLogger"),

    http("httpLogger"),

//    ibm("ibm"),

//    redis("redis"),

    magfa("magfa");


//    notificationApi("notificationApi");
//
//    root("root");  ??
//
//    stacktrace("stacktrace"); ??







    String desc;

    LoggerNamesEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
