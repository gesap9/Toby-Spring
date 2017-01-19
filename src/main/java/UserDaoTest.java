import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by gesap on 2017-01-19.
 */
public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        //UserDao가 사용할 ConnectionMaker 구현 클래스를 결정하고 오브젝트를 만듬
        ConnectionMaker connectionMaker = new DConnectionMaker();

        UserDao dao = new UserDao(connectionMaker);

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



    }
}
