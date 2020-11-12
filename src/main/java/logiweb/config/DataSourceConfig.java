package logiweb.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManager() throws NamingException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

        factoryBean.setJpaVendorAdapter(adapter);
        factoryBean.setPackagesToScan("logiweb.*");

        Properties jpaProp = new Properties();
        jpaProp.put("hibernate.connection.CharSet", "utf8");
        jpaProp.put("hibernate.connection.characterEncoding", "utf8");
        jpaProp.put("hibernate.connection.useUnicode", "true");
        factoryBean.setJpaProperties(jpaProp);

        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws NamingException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager().getObject());
        return transactionManager;
    }

    @Bean
    public DataSource dataSource() throws NamingException {
        InitialContext cxt = new InitialContext();
        DataSource dataSource = (DataSource) cxt.lookup("java:/jdbc/logiweb");

        return dataSource;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
