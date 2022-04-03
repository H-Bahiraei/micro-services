package ir.bki.otpservice.util;

import com.google.gson.Gson;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * * We got this class from Paypal
 */

@EqualsAndHashCode
public class GsonModel {
    /**
     * Returns a JSON string corresponding to object state
     *
     * @return JSON representation
     */
    public String toJSON() {
        return JSONFormatter.toJSON(this);
    }
    public String toJSON(Gson gson) {
        return JSONFormatter.toJSON(gson,this);
    }
    public String toJSONNormal() {
        return JSONFormatter.toJSONNormal(this);
    }
    public String toJSONFull() {
        return JSONFormatter.toJSONFull(this);
    }

    public String toJSONFaster() {
        return JSONFormatter.toJSONFaster(this);
    }

    public Map toMAP() {return JSONFormatter.toMap(this);
    }

    @Override
    public String toString() {
        return toJSON();
    }

}
