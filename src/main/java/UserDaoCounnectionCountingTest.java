import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

/**
 * Created by gesap on 2017-01-19.
 */
public class UserDaoCounnectionCountingTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(CountingDaoFactory.class);

        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("12323");

        dao.add(user);

        System.out.println(user.getId() + "등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + "조회 성공");

        CountingConnectionMaker ccm = context.getBean("connectionMaker",
                CountingConnectionMaker.class);

        System.out.println("Connection counter : " +
                ccm.getCounter());

    }
}
