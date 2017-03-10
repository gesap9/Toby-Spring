package springbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by gesap on 2017-01-18.
 */

@Component
public class UserDaoJdbc implements UserDao {
    @Autowired
    private SqlService sqlService;

    /*public void setSqlService(SqlService sqlService) {
        this.sqlService = sqlService;
    }*/

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

    @Autowired /*DataSource를 주입가능한 Bean이 있는지 보고 주입*/
    public void setDataSource(DataSource dataSource)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //Parameter를 final로 선언해줘야 한다.
    public void add(final User user) {
        this.jdbcTemplate.update(
                this.sqlService.getSql("userAdd")
                , user.getId(), user.getName(), user.getPassword()
                , user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail());
    }

    public void deleteAll() {
        this.jdbcTemplate.update(this.sqlService.getSql("userDeleteAll"));
    }


    public int getCount() {
        return this.jdbcTemplate.queryForInt(this.sqlService.getSql("userGetCount"));
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
                this.sqlService.getSql("userGet"),
                new Object[]{id},
                userMapper
        );
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
                this.sqlService.getSql("userGetAll"),
                userMapper
        );
    }

    public void update(User user) {
        this.jdbcTemplate.update(
                this.sqlService.getSql("userUpdate")
                , user.getName(), user.getPassword(), user.getLevel().intValue(), user.login, user.getRecommend(), user.getEmail(), user.getId());
    }
}
