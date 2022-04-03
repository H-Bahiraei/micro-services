package ir.bki.otpservice.model;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 3/14/2022
 */
@Entity
@Table(name = "T_OTP"
        , indexes = {
        @Index(name = "IX_OTP_MOBILE_NO", columnList = "MOBILE_NO")
}
        , uniqueConstraints =
        {
                @UniqueConstraint(name = "UNQ_OTP_UUID", columnNames = "UUID")
        }
)
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class OtpEntity {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "UUID", length = 30)
    protected String uuid;

    @Column(name = "MOBILE_NO",nullable = false)
    private Long mobileNo;

    @Column(name = "LOCKED")
    private Boolean locked;

    @Column(name = "SEED", length = 50)
    private String seed;

    @Lob
    @Column(name = "OTP_KEY", nullable = false)
    private byte[] key;

    @Column(name = "TRY_COUNT")
    private byte tryCount;

    @Column(name = "STATIC_PASSWORD", length = 100)
    private String staticPassword;//for SMS and USSD method

    @Column(name = "PREVIOUS_CODE", length = 20)
    private String previousOTP;

    @Column(name = "LAST_USED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastUsedAt;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "EXPIRED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        OtpEntity otpEntity = (OtpEntity) o;

        return new EqualsBuilder()
                .append(id, otpEntity.id)
                .append(uuid, otpEntity.uuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(uuid)
                .toHashCode();
    }
}
