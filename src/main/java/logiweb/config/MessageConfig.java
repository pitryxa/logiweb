package logiweb.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Configuration
public class MessageConfig {
    private static final Logger logger = Logger.getLogger(MessageConfig.class);
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
    public JMSProducer jmsProducer(@Qualifier("jmsContext") JMSContext context) {
        return context.createProducer();
    }

    @Bean
    public JMSContext jmsContext(TopicConnectionFactory connectionFactory) {
        return connectionFactory.createContext(SEC_CRED_LOGIN, SEC_CRED_PASS);
    }

    @Bean
    public TopicConnectionFactory connectionFactory() throws NamingException {
        TopicConnectionFactory connectionFactory = (TopicConnectionFactory) context().lookup("jms/RemoteConnectionFactory");

        UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter =
                new UserCredentialsConnectionFactoryAdapter();
        userCredentialsConnectionFactoryAdapter.setUsername(SEC_CRED_LOGIN);
        userCredentialsConnectionFactoryAdapter.setPassword(SEC_CRED_PASS);
        userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(connectionFactory);

        return userCredentialsConnectionFactoryAdapter;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws NamingException {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setDefaultDestinationName("Logiweb");
        return jmsTemplate;
    }

    @Bean
    public Topic topic() throws Exception {
//        Properties props = new Properties();
//        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
//        props.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
//        props.put("jboss.naming.client.ejb.context", true);
//        props.put(Context.SECURITY_PRINCIPAL, SEC_CRED_LOGIN);
//        props.put(Context.SECURITY_CREDENTIALS, SEC_CRED_PASS);
//        Context context = new InitialContext(props);

//        TopicConnectionFactory factory = (TopicConnectionFactory) context().lookup("jms/RemoteConnectionFactory");
        TopicConnectionFactory factory = connectionFactory();



        Topic topic;

        try (TopicConnection topicConnection = factory.createTopicConnection(SEC_CRED_LOGIN, SEC_CRED_PASS);
             TopicSession session = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE)) {
            topicConnection.start();
            topic = session.createTopic("Logiweb");
        }
        logger.info("Connection to topic is established");
        return topic;
    }
}
