package springbook.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springbook.dao.UserDao;

import javax.annotation.Resource;
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
@Import({SqlServiceContext.class, TestAppContext.class, ProductionAppContext.class})
public class AppContext {
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


}
