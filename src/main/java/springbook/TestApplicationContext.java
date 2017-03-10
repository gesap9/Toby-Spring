package springbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.mail.MailSender;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.xml.bind.Unmarshaller;
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
public class TestApplicationContext {
    /* XML에 정의된 BEAN을 쓰기 위해 Autowired로 Spring에서 주입받도록 한다.
    @Autowired
    SqlService sqlService;*/

    /* XML에 정의된 Bean을 쓰는데 전용 태그를 사용하고 있기 때문에 Resource로 주입받도록 한다.
    *  Resource는 이름 기준으로 매핑된다.*/
    @Resource
    DataSource embeddedDatabase;

    @Autowired
    UserDao userDao;

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

    /*@Bean
    public UserDao userDao() {
    *//*<bean id="userDao" class="springbook.UserDaoJdbc">
        <property name="dataSource" ref="dataSource"/>
        <property name="sqlService" ref="sqlService"/>
    </bean>*//*
        UserDaoJdbc dao = new UserDaoJdbc();
        *//*dao.setDataSource(dataSource());*//*
        *//*dao.setSqlService(sqlService());*//*
        return dao;
    }*/

/*
    @Bean
    public UserService userService() {
        */
/*<bean id="userService" class="springbook.UserServiceImpl">
        <property name="userDao" ref="userDao"/>
        <property name="userLevelUpgradePolicy" ref="userLevelUpgradePolicy"/>
    </bean>*//*

        UserServiceImpl service = new UserServiceImpl();
        service.setUserDao(this.userDao);
        service.setUserLevelUpgradePolicy(userLevelUpgradePolicy());
        return service;
    }
*/

    @Bean
    public UserService testUserService() {
        /*<bean id="testUserService" class="springbook.TestUserService" parent="userService"/>*/
        TestUserService testService = new TestUserService();
        testService.setUserDao(this.userDao);
        testService.setUserLevelUpgradePolicy(userLevelUpgradePolicy());
        return testService;
    }

    @Bean
    public MailSender mailSender() {
        /*<bean id="mailSender" class="springbook.DummyMailSender">
    </bean>*/
        return new DummyMailSender();
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
    /*<bean id="userLevelUpgradePolicy" class="springbook.UserLevelUpgradePolicyImpl">
        <property name="userDao" ref="userDao"/>
        <property name="mailSender" ref="mailSender"/>
    </bean>*/
        UserLevelUpgradePolicyImpl userLevelUpgradePolicy = new UserLevelUpgradePolicyImpl();
        userLevelUpgradePolicy.setUserDao(this.userDao);
        userLevelUpgradePolicy.setMailSender(mailSender());
        return userLevelUpgradePolicy;
    }

    @Bean
    public SqlService sqlService() {
         /*<bean id="sqlService" class="springbook.OxmSqlService">
        <property name="unmarshaller" ref="unmarshaller"/>
        <property name="sqlmap" value="classpath:sqlmap.xml"/>
        <property name="sqlRegistry" ref="sqlRegistry"/>

    </bean>*/
        OxmSqlService sqlService = new OxmSqlService();
        sqlService.setUnmarshaller(unmarshaller());
        sqlService.setSqlRegistry(sqlRegistry());
        return sqlService;
    }

    @Bean
    public SqlRegistry sqlRegistry() {
         /*<bean id="sqlRegistry" class="springbook.EmbeddedDbSqlRegistry">
        <property name="dataSource" ref="embeddedDatabase"/>

    </bean>*/
        EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
        sqlRegistry.setDataSource(embeddedDatabase());
        return sqlRegistry;
    }

    @Bean
    public org.springframework.oxm.Unmarshaller unmarshaller() {
  /*      <bean id="unmarshaller" class="org.springframework.oxm.jaxb.Jaxb2Marshaller">
        <property name="contextPath" value="springbook"/>
    </bean>*/

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("springbook");
        return marshaller;
    }

    @Bean
    public DataSource embeddedDatabase(){
            /*<jdbc:embedded-database id="embeddedDatabase" type="HSQL">
        <jdbc:script location="classpath:schema.sql"/>
    </jdbc:embedded-database>*/
            return new EmbeddedDatabaseBuilder()
                    .setName("embeddedDatabase")
                    .setType(EmbeddedDatabaseType.HSQL)
                    .addScript("classpath:schema.sql")
                    .build();
    }
}
