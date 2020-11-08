package logiweb.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSProducer;
import javax.jms.Queue;

@Component
public class SenderQueue {
    @Autowired
    private JMSProducer jmsProducer;

    @Autowired
    private Queue queue;

    public void send(String message) {
        jmsProducer.send(queue, message);
    }
}
