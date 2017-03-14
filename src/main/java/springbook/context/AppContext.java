package springbook.context;

import org.springframework.context.annotation.*;
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
public class AppContext {

    @Bean
    public DataSource dataSource() {
    /*<bean id="dataSource" class="org.springframework.jdbc.datasource.SimpleDriverDataSource">
        <property name="driverClass" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost/testdb"/>
        <property name="username" value="com"/>
        <property name="password" value="com01"/>
    </bean>*/
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/testdb");
        dataSource.setUsername("com");
        dataSource.setPassword("com01");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
    /*<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>*/
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
