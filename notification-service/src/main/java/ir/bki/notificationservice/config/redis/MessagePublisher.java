package ir.bki.notificationservice.config.redis;

public interface MessagePublisher {
    void publish(final String message);
}