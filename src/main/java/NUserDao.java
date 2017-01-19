import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by gesap on 2017-01-19.
 */
public class NUserDao extends UserDao {

    protected Connection getConnection() throws ClassNotFoundException, SQLException {
        return null;
    }
}
