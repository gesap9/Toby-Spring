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

        //동일성확인을 위한 DaoFactory사용
        DaoFactory daoFactory = new DaoFactory();

        UserDao dao1 = daoFactory.userDao();
        UserDao dao2 = daoFactory.userDao();

        ApplicationContext context =
                new GenericXmlApplicationContext("applicationContext.xml");



        UserDao dao3 = context.getBean("userDao", UserDao.class);
        UserDao dao4 = context.getBean("userDao", UserDao.class);

        System.out.println(dao1);
        System.out.println(dao2);
        System.out.println(dao3);
        System.out.println(dao4);

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

        /* 실행결과
        UserDao@df27fae
        UserDao@24a35978
        UserDao@16f7c8c1
        UserDao@16f7c8c1
        whiteship등록 성공
        백기선
        12323
        whiteship조회 성공
         */
    }
}
