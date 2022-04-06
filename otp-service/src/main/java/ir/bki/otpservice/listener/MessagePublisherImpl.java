//package ir.bki.otpservice.listener;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.stereotype.Service;
//
///**
// * @author Mahdi Sharifi
// * @version 2022.1.1
// * https://www.linkedin.com/in/mahdisharifi/
// * @since 4/5/2022
// */
//@Service
//public class MessagePublisherImpl implements MessagePublisher {
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//    @Autowired
//    private ChannelTopic topic;
//
//    public MessagePublisherImpl() {
//    }
//
//    public MessagePublisherImpl(final RedisTemplate<String, Object> redisTemplate, final ChannelTopic topic) {
//        this.redisTemplate = redisTemplate;
//        this.topic = topic;
//    }
//
//    public void publish(final String message) {
//        redisTemplate.convertAndSend(topic.getTopic(), message);
//    }
//
//}
