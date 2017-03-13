package springbook.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.mail.MailSender;
import springbook.biz.*;
import springbook.dao.UserDao;

/**
 * Created by gesap on 2017-03-08.
 */
@Configuration
@Profile("test")
public class TestAppContext {
    @Autowired
    UserDao userDao;

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
        userLevelUpgradePolicy.setUserDao(this.userDao);
        userLevelUpgradePolicy.setMailSender(mailSender());
        return userLevelUpgradePolicy;
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }

}
