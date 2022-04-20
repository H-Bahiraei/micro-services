package ir.bki.notificationservice.config;

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