import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by gesap on 2017-01-26.
 */
public interface UserDao {

    public void add(final User user);

    public void deleteAll();


    public int getCount();

    public User get(String id);

    public List<User> getAll();
}
