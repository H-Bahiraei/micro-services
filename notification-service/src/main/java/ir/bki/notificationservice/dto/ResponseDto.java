package ir.bki.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import io.swagger.v3.oas.annotations.media.Schema;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
//import javax.persistence.GeneratedValue;
/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/9/2022
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "save ResponseDTO")

public class ResponseDto<T> {

    @JsonIgnore
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @JsonProperty("status_code")
    private int statusCode;

    @NotNull(message = "#message can not be null")
    @JsonProperty("message")

    private String message;

    @NotNull(message = "#httpStatus can not be null")
    @JsonProperty("httpStatus")
    private int httpStatus;

    @NotNull(message = "#path can not be null")
    @JsonProperty("path")
    private String path;//transient

    @JsonProperty("information_link")
    private String informationLink;

    private String parameters;

    @NotNull(message = "#reqParams can not be null")
    @JsonIgnore // dont go in json Response
    private Map<String,String> reqParams;

    @NotNull(message = "#elapsedTime can not be null")
    @JsonProperty("elapsed_time")
    private Long elapsedTime;

    @NotNull(message = "#time can not be null")
    @JsonProperty("time") // go to Json Response
    private String time ;

    @SerializedName("count")
    private Long count;// use when we need paging


    @JsonProperty("pages_count")
    private Long pagesCount;// use when we need paging

    @NotNull(message = "#status can not be null")
    @JsonProperty("status")
    private Long status;

    @NotNull(message = "#payload can not be null")
    private List<T> payload = new ArrayList<>();


    public ResponseDto() {
        elapsedTime = System.currentTimeMillis();
    }

    public ResponseDto(List<T> payload) {
        this.payload = payload;
        elapsedTime = System.currentTimeMillis();
//        httpStatus = 200;
    }



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

//    public ResponseDto(int status) {
//        this.status = status;
//        elapsedTime=System.currentTimeMillis();
//    }

//    public boolean isSuccess(){
//        return (status ==0 || (httpStatus>=200 && httpStatus<=299) );
//    }


    public void setReqParams(Map<String, String> reqParams) {  // go to ELK
        this.reqParams = reqParams;
    }


    @JsonIgnore
    public int getHttpStatus() {
//        if (status ==0) return 200;
        return httpStatus;
    }

//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("ResponseDto {");
////        sb.append("statusCode=").append(status);
//        sb.append(" time: ").append(time);
//        sb.append(", elapsedTime=").append(elapsedTime);
//        sb.append(", path: ").append(path);
//        sb.append(", message='").append(message).append('\'');
//        sb.append(", httpStatus=").append(httpStatus);
//        sb.append(", errorCode=").append(errorCode);
//        sb.append(", count=").append(count);
//        for (Object p : payload) {
//            sb.append(", payload=").append(p);
//        }
//        if (payload != null) {
//            sb.append(", payload.size=").append(payload.size());
////            sb.append(", payload.HK=").append(payload.get(0));
//        }
//        sb.append(" }");
//        return sb.toString();
//    }
}