package ir.bki.otpservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import ir.bki.otpservice.util.JSONFormatter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/9/2022
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName = "responsedtoindex")
public class ResponseDto<T>  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Field(type = FieldType.Text, name = "message")
    private String message;

    @Field(type = FieldType.Text, name = "path")
    private  String path;//transient

    @JsonProperty("information_link")
    @Field(type = FieldType.Text, name = "informationLink")
    private String informationLink;

    @Field(type = FieldType.Text, name = "parameters")
    private String parameters;

    @JsonIgnore
    @Field(type = FieldType.Integer, name = "httpStatus")
    private transient int httpStatus;

    @JsonProperty("elapsed_time")
    @Field(type = FieldType.Long, name = "elapsedTime")
    private Long elapsedTime;

    @JsonProperty("time")
    @Field(type = FieldType.Text, name = "time")
    private String time= LocalDateTime.now()+"";

    @Field(type = FieldType.Long, name = "count")
    @SerializedName("count")
    private Long count;// use when we need paging


    @JsonProperty("pages_count")
    @Field(type = FieldType.Long, name = "pagesCount")
    private Long pagesCount;// use when we need paging

    @Field(type = FieldType.Long, name = "errorCode")
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

//    public ResponseDto(int status) {
//        this.status = status;
//        elapsedTime=System.currentTimeMillis();
//    }
//
//    public boolean isSuccess(){
//        return (status ==0 || (httpStatus>=200 && httpStatus<=299) );
//    }

    public static void main(String[] args) {
        ResponseDto responseDto=new ResponseDto();
        System.out.println(JSONFormatter.fromJSON("",ResponseDto.class));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseDto{");
//        sb.append("statusCode=").append(status);
        sb.append("time: ").append(time);
        sb.append(", elapsedTime=").append(elapsedTime);
        sb.append(", path: ").append(path);
        sb.append(", message='").append(message).append('\'');
        sb.append(", httpStatus=").append(httpStatus);
        sb.append(", errorCode=").append(errorCode);
        sb.append(", count=").append(count);
        for ( Object p : payload ){
            sb.append(", payload=").append(p);
        }

        if(payload!=null) {
            sb.append(", payload.size=").append(payload.size());
//            sb.append(", payload.HK=").append(payload.get(0));
        }
        sb.append('}');
        return sb.toString();
    }
}