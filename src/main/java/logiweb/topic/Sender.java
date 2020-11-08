package logiweb.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSProducer;
import javax.jms.Topic;

@Component
public class Sender {
    @Autowired
    private JMSProducer jmsProducer;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Topic topic;

    public void send(String message) {
        //jmsProducer.send(topic, message);

        jmsTemplate.send(topic, s -> s.createTextMessage(message));
//        jmsTemplate.send(s -> s.createTextMessage(message));

    }
}
