import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by gesap on 2017-01-18.
 */
public class UserDaoJdbc implements UserDao {
    //읽기 전용 정보이기 떄문에 멤버 변수로 사용해도 상관없다.
    //이 변수에는 ConnectionMaker Type의 싱글톤 오브젝트가 들어있다.
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userMapper =
            new RowMapper<User>() {
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    user.setLevel(Level.valueOf(rs.getInt("level")));
                    user.setLogin(rs.getInt("login"));
                    user.setRecommend(rs.getInt("recommend"));
                    user.setEmail(rs.getString("email"));
                    return user;
                }
            };

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //Parameter를 final로 선언해줘야 한다.
    public void add(final User user) {
        this.jdbcTemplate.update(
                "insert into users(id, name, password, level, login, recommend, email) values(?,?,?,?,?,?,?)"
                , user.getId(), user.getName(), user.getPassword()
                , user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail());
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }


    public int getCount() {
        return this.jdbcTemplate.queryForInt("select count(*) from users");
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id},
                userMapper
        );
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id",
                userMapper
        );
    }

    public void update(User user) {
        this.jdbcTemplate.update(
                "update users set name = ?, password = ?, level = ?, login = ?, recommend = ?, email = ? where id = ?"
                ,user.getName(),user.getPassword(),user.getLevel().intValue(),user.login,user.getRecommend(), user.getEmail() ,user.getId());
    }
}
