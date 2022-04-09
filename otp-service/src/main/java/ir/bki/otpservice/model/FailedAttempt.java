package ir.bki.otpservice.model;

// TODO Comment : when mistake in auth create this obj and set in redis

import lombok.Data;

import java.util.Date;

@Data
public class FailedAttempt {

    private String id;
    private String mobileNo; //TODO INDEX
    private Long appId;
    private Date createdAt;
    private Date expireAt; //TODO INDEX
    private Integer opCode;
    private String service;

    public FailedAttempt() {
    }

    public FailedAttempt(String mobileNo, long appId, Date createdAt, Date expireAt, int opCode) {
        this.mobileNo = mobileNo;
        this.appId = appId;
        this.createdAt = createdAt;
        this.expireAt = expireAt;
        this.opCode = opCode;
    }
}
