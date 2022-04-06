package ir.bki.notificationservice.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//https://micrometer.io/docs/concepts#_meters
//@Configuration
//public class MetricsRegistryConfig {
//
//	@Value("${spring.application.name}")
//	String appName;
//
//	@Bean
//    MeterRegistryCustomizer<ElasticMeterRegistry> configureMetricsRegistry(){
//		return registry -> registry.config().commonTags("appName", appName);
//	}
//
//	@Bean
//	public TimedAspect timedAspect(MeterRegistry registry) {
//		return new TimedAspect(registry);
//	}
//
//}