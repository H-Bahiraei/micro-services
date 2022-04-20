package ir.bki.notificationservice.log;

public enum LoggerNamesEnum {

    ROOT("ROOT"),
    HTTP("http"),
    RAHYAB("rahyab"),
    MAGFA("magfa");

    String desc;

    LoggerNamesEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
