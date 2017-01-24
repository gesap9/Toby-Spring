import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by gesap on 2017-01-24.
 */
public class UserDaoDeleteAll extends UserDao {

    protected PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("delete from users");
        return ps;
    }
    //DAO 로직마다 상속을 통해 새로운 클래스를 만들어야 된다는 단점이 있음
}
