package ir.bki.otpservice.util.jmx;

import ir.bki.otpservice.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 5/1/22
 */
@Component
@ManagedResource(objectName = "otp-ms-xmanamgement:name=OTP-MS-XManagement", description = "OTP Microservice Management Bean")
@Slf4j
public class ApplicationManagementMBean {
    @Autowired
    private DiscoveryClient discoveryClient;

    @ManagedOperation(description = "#Set Log to Elastic Enable")
    public void setElasticEnable() {
        AppUtil.isEnabledElastic = true;
        log.info("#isEnabledElastic Set TRUE. " + AppUtil.isEnabledElastic);
    }

    @ManagedOperation(description = "#Set Log to Elastic Disable")
    public void setElasticDisable() {
        AppUtil.isEnabledElastic = false;
        log.info("#isEnabledElastic Set FALSE. " + AppUtil.isEnabledElastic);
    }

    @ManagedAttribute(description = "#Get Log to Elastic Status")
    public boolean isElasticEnabled() {
        log.info("#Get Log to Elastic Status: " + AppUtil.isEnabledElastic);
        return AppUtil.isEnabledElastic;
    }


    @ManagedAttribute(description = "#Show Eureka-SERVER information")
    public List<String> getInfEUREKAServer() {
        return fetchInfos(AppUtil.eurekaServer);
    }

    @ManagedAttribute(description = "#Show NOTIFICATION-SERVICE information")
    public List<String> getInfNotificationService() {
        return fetchInfos(AppUtil.notificationService);
    }

    @ManagedAttribute(description = "#Show OTP-SERVICE information")
    public List<String> getInfOTPService() {
        return fetchInfos(AppUtil.otpService);
    }


    private List<String> fetchInfos(String sName) {
        final List<String> services = new ArrayList<String>();
        for (final ServiceInstance r : discoveryClient.getInstances(sName)) {
            services.add(new StringBuilder().append("NAME:").append(sName).append("; ID:").append(r.getServiceId()).append("; URI:").append(r.getUri()).toString());
        }
        return services;
    }


}
