package ir.bki.notificationservice.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NotificationContextListener {
    private final Logger LOGGER = LoggerFactory.getLogger(NotificationContextListener.class);
    // Test
    @EventListener({ContextRefreshedEvent.class})
    public void handleContextStartEvent(ContextRefreshedEvent refreshedEvent) {
        LOGGER.info("Service Startup Date..... " + new Date(refreshedEvent.getApplicationContext().getStartupDate()));

    }

}
