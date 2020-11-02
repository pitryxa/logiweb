package logiweb.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSProducer;
import javax.jms.Topic;

@Component
public class Sender {
    @Autowired
    private JMSProducer jmsProducer;

    @Autowired
    private Topic topic;

    public void send(String message) {
        jmsProducer.send(topic, message);
    }
}
