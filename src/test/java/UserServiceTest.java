import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import springbook.biz.*;
import springbook.context.AppContext;
import springbook.dao.UserDao;
import springbook.exception.TestUserServiceException;

import java.util.Arrays;
import java.util.List;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by gesap on 2017-01-31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {AppContext.class})
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserService testUserService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserLevelUpgradePolicy userLevelUpgradePolicy;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    ApplicationContext context;


    List<User> users;

    @Before
    public void setUp() {
        users = Arrays.asList(
                new User("bumjin", "박범진", "p1", Level.BASIC, UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER - 1, 0, "bumjin@mail.com"),
                new User("joytouch", "강명성", "p2", Level.BASIC, UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER, 0, "joytouch@mail.com"),
                new User("erwins", "신승한", "p3", Level.SILVER, 60, UserServiceImpl.MIN_RECOMMEND_FOR_GOLD - 1, "erwins@mail.com"),
                new User("madnite1", "이상호", "p4", Level.SILVER, 60, UserServiceImpl.MIN_RECOMMEND_FOR_GOLD, "madnitel1@mail.com"),
                new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "green@mail.com")

        );
    }

    @Test
    public void transactionSync() {
        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDefinition);

        userService.add(users.get(0));
        userService.add(users.get(1));
        assertThat(userDao.getCount(), is(2));

        transactionManager.rollback(txStatus);

        assertThat(userDao.getCount(), is(0));
    }

    @Test(expected = TransientDataAccessResourceException.class)
    public void readOnlyTransactionAttribute() {
        testUserService.getAll();
    }

    @Test
    public void advisorAutoProxyCreator() {
        System.out.println(testUserService.getClass());
        System.out.println(java.lang.reflect.Proxy.class);
        //java.lang.reflect.Proxy prc = testUserService;

        //assertThat(testUserService, is(java.lang.reflect.Proxy.class));

    }

    @Test
    public void upgradeAllOrNothing() throws Exception {
        userDao.deleteAll();
        for (User user : users) userDao.add(user);

        try {
            this.testUserService.upgradeLevels();
            fail("springbook.exception.TestUserServiceException excepted");
        } catch (TestUserServiceException e) {
            System.out.println("springbook.exception.TestUserServiceException raised");
        }

        //madnite1
        checkLevelUpgraded(users.get(3), false);

    }


    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
    }

    @Test
    public void upgradeLevels() throws Exception {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        UserDao mockUserDao = mock(UserDao.class);
        when(mockUserDao.getAll()).thenReturn(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MailSender mockMailSender = mock(MailSender.class);

        UserLevelUpgradePolicyImpl userLevelUpgradePolicy = new UserLevelUpgradePolicyImpl();
        userLevelUpgradePolicy.setUserDao(mockUserDao);
        userLevelUpgradePolicy.setMailSender(mockMailSender);
        userServiceImpl.setUserLevelUpgradePolicy(userLevelUpgradePolicy);

        userServiceImpl.upgradeLevels();

        verify(mockUserDao, times(2)).update(Matchers.any(User.class));
        verify(mockUserDao, times(2)).update(Matchers.any(User.class));
        verify(mockUserDao).update(users.get(1));
        assertThat(users.get(1).getLevel(), is(Level.SILVER));
        verify(mockUserDao).update(users.get(3));
        assertThat(users.get(3).getLevel(), is(Level.GOLD));

        ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mockMailSender, times(2)).send(mailMessageArg.capture());
        List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
        assertThat(mailMessages.get(0).getTo()[0], is(users.get(1).getEmail()));
        assertThat(mailMessages.get(1).getTo()[0], is(users.get(3).getEmail()));

    }

    private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
        assertThat(updated.getId(), is(expectedId));
        assertThat(updated.getLevel(), is(expectedLevel));
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }

    private void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel(), is(expectedLevel));
    }

    @Test
    public void bean() {
        assertThat(this.userService, is(notNullValue()));
    }


}
