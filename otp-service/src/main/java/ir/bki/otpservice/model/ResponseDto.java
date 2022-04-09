package ir.bki.otpservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.istack.NotNull;
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

    @JsonProperty("status_code")
    private int statusCode;
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


    public int getHttpStatus() {
        if (statusCode==0) return 200;
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

    public ResponseDto(int statusCode) {
        this.statusCode = statusCode;
        elapsedTime=System.currentTimeMillis();
    }

//    public boolean isSuccess(){
//        return (statusCode==0 || (httpStatus>=200 && httpStatus<=299) );
//    }

    public static void main(String[] args) {
        ResponseDto responseDto=new ResponseDto();
        System.out.println(JSONFormatter.fromJSON("",ResponseDto.class));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseDto{");
        sb.append("statusCode=").append(statusCode);
        sb.append(", message='").append(message).append('\'');
        sb.append(", httpStatus=").append(httpStatus);
        sb.append(", count=").append(count);
        sb.append(", elapsedTime=").append(elapsedTime);
        if(payload!=null)
            sb.append(", payload.size=").append(payload.size());
        sb.append('}');
        return sb.toString();
    }
}