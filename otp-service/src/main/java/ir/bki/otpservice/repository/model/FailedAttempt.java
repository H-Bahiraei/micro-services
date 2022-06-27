package ir.bki.otpservice.repository.model;

// TODO Comment : when mistake in auth create this obj and set in redis

import lombok.Data;

import java.util.Date;

/**
 * @author H-Bahiraei
 * Created on 6/27/2022
 */

@Data
public class FailedAttempt {

    private String id;
    private String mobileNo; //TODO INDEX
    private Date createdAt;
    private Date expireAt; //TODO INDEX
    private String pairData;

    public FailedAttempt() {
    }

}
