package springbook.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springbook.biz.*;
import springbook.dao.UserDao;
import springbook.sql.SqlMapConfig;

import javax.sql.DataSource;
import java.sql.Driver;

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
public class AppContext implements SqlMapConfig {

    @Value("${db.driverClass}")
    Class<? extends Driver> driverClass;
    @Value("${db.url}")
    String url;
    @Value("${db.username}")
    String username;
    @Value("${db.password}")
    String password;
    @Override
    public Resource getSqlMapResource() {
        return new ClassPathResource("../../sqlmap.xml", UserDao.class);
    }
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(this.driverClass);
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);

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
        public MailSender mailSender() {
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
