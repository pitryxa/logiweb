package logiweb.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Configuration
public class MessageConfigQueue {
    private final String SEC_CRED_LOGIN = "root";
    private final String SEC_CRED_PASS = "root";

    @Bean
    public Context context() throws NamingException {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
        props.put("jboss.naming.client.ejb.context", true);
        props.put(Context.SECURITY_PRINCIPAL, SEC_CRED_LOGIN);
        props.put(Context.SECURITY_CREDENTIALS, SEC_CRED_PASS);
        return new InitialContext(props);
    }

    @Bean
    public JMSProducer jmsProducerQueue(@Qualifier("jmsContextQueue") JMSContext context) {
        return context.createProducer();
    }

    @Bean
    public JMSContext jmsContextQueue(QueueConnectionFactory connectionFactory) {
        return connectionFactory.createContext(SEC_CRED_LOGIN, SEC_CRED_PASS);
    }

    @Bean
    public QueueConnectionFactory connectionFactoryQueue() throws NamingException {
        return (QueueConnectionFactory) context().lookup("jms/RemoteConnectionFactory");
    }

    @Bean
    public Queue queue() throws Exception {
//        Properties props = new Properties();
//        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
//        props.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
//        props.put("jboss.naming.client.ejb.context", true);
//        props.put(Context.SECURITY_PRINCIPAL, SEC_CRED_LOGIN);
//        props.put(Context.SECURITY_CREDENTIALS, SEC_CRED_PASS);
//        Context context = new InitialContext(props);

        QueueConnectionFactory factory = (QueueConnectionFactory) context().lookup("jms/RemoteConnectionFactory");



        Queue queue;

        try (QueueConnection queueConnection = factory.createQueueConnection(SEC_CRED_LOGIN, SEC_CRED_PASS);
             QueueSession session = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE)) {
            queueConnection.start();
            queue = session.createQueue("DLQ");
        }

        return queue;
    }
}
