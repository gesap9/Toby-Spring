import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by gesap on 2017-01-19.
 */
public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        //XML방식
        ApplicationContext context =
                new GenericXmlApplicationContext("applicationContext.xml");

        /*
        //Java 설정 방식
        ApplicationContext context =
                new AnnotationConfigApplicationContext(DaoFactory.class);
        */
        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("12323");

        UserDao dao = context.getBean("userDao", UserDao.class);

        dao.add(user);

        System.out.println(user.getId() + "등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + "조회 성공");


    }
}
