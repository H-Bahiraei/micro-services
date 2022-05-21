package ir.bki.otpservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import ir.bki.otpservice.util.JSONFormatter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/9/2022
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T>  {

    @JsonProperty("status")
    private int status;
    private String message;
    private  String path;//transient
    @JsonProperty("information_link")
    private String informationLink;
    private String parameters;
    @JsonIgnore
    private transient int httpStatus;
    @JsonProperty("elapsed_time")
    private Long elapsedTime;
    @SerializedName("count")
    private Long count;// use when we need paging
    @JsonProperty("pages_count")
    private Long pagesCount;// use when we need paging

    private Long errorCode;

    private List<T> payload=new ArrayList<>();

//      "links": [
//    {
//        "rel": "feed",
//            "href": "https://example.org/friends/rss"
//    },
//    {
//        "rel": "queries",
//            "href": "https://example.org/friends/?queries"
//    },
//    {
//        "rel": "template",
//            "href": "https://example.org/friends/?template"
//    }
//    ]

    @JsonIgnore
    public int getHttpStatus() {
//        if (status ==0) return 200;
        return httpStatus;
    }

    public ResponseDto() {
        elapsedTime=System.currentTimeMillis();
    }

    public ResponseDto(List<T> payload) {
        this.payload = payload;
        elapsedTime=System.currentTimeMillis();
        httpStatus=200;
    }

    public ResponseDto(int status) {
        this.status = status;
        elapsedTime=System.currentTimeMillis();
    }

    public boolean isSuccess(){
        return (status ==0 || (httpStatus>=200 && httpStatus<=299) );
    }

    public static void main(String[] args) {
        ResponseDto responseDto=new ResponseDto();
        System.out.println(JSONFormatter.fromJSON("",ResponseDto.class));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseDto{");
        sb.append("statusCode=").append(status);
        sb.append(", message='").append(message).append('\'');
        sb.append(", httpStatus=").append(httpStatus);
        sb.append(", errorCode=").append(errorCode);
        sb.append(", count=").append(count);
        sb.append(", elapsedTime=").append(elapsedTime);
        if(payload!=null) {
            sb.append(", payload.size=").append(payload.size());
//            sb.append(", payload.HK=").append(payload.get(0));
        }
        sb.append('}');
        return sb.toString();
    }
}