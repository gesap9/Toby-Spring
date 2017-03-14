package springbook.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springbook.biz.*;

import javax.sql.DataSource;

/**
 * Created by gesap on 2017-03-08.
 */
@Configuration
/* xml을 import해서 같이 쓸 수 있게 함
@ImportResource("/test-applicationContext.xml")*/
/*<tx:annotation-driven/>*/
@EnableTransactionManagement
@ComponentScan(basePackages = "springbook")
@Import({SqlServiceContext.class})
@PropertySource("/database.properties")
public class AppContext {

    @Autowired
    Environment env;
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        try{
            dataSource.setDriverClass((Class<? extends java.sql.Driver>)Class.forName(env.getProperty("db.driverClass")));

        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;

    }
    @Configuration
    @Profile("production")
    public static class ProductionAppContext {
        @Bean
        public MailSender mailSender(){
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("localhost");
            return mailSender;
        }
    }

    @Configuration
    @Profile("test")
    public static class TestAppContext {
        @Bean
        public UserService testUserService() {
            TestUserService testService = new TestUserService();
            //testService.setUserDao(this.userDao);
            testService.setUserLevelUpgradePolicy(userLevelUpgradePolicy());
            return testService;
        }

        @Bean
        public UserLevelUpgradePolicy userLevelUpgradePolicy() {
            UserLevelUpgradePolicyImpl userLevelUpgradePolicy = new UserLevelUpgradePolicyImpl();
            userLevelUpgradePolicy.setMailSender(mailSender());
            return userLevelUpgradePolicy;
        }

        @Bean
        public MailSender mailSender() {
            return new DummyMailSender();
        }

    }


}
