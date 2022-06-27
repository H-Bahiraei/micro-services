package ir.bki.otpservice.repository.model;
import lombok.Data;

import java.util.Date;

// TODO Comment :  obj auth code is cacheAuth

@Data
public class CacheAuth  {

    public CacheAuth(){};

    public CacheAuth(String key, String value, Date expireAt, String hashKey){
        this.key = key;
        this.value = value;
        this.expireAt = expireAt;
        this.hashKey = hashKey;
    }

    private String key;

    private String value;

    private Date expireAt ;

    private String hashKey;

    public String  toStringForLog(){
        StringBuilder s = new StringBuilder("cacheAuth: ");
         if(this.key != null) s.append("key: " + this.key.substring(0, this.key.length() - 5));
         if(this.value != null) s.append(" ;value length: " + this.value.length());
                s.append(" ;hashKey: " + this.hashKey)
                .append(" ;expire at: " + this.expireAt);
        return s.toString();
    }
}
// if(this.key != null) s.append("key: " + this.key.substring(0, this.key.length() - 5));
//         if(this.value != null) s.append(" ;value length: " + this.value.length());