import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by gesap on 2017-01-18.
 */
public class UserDao {
    //읽기 전용 정보이기 떄문에 멤버 변수로 사용해도 상관없다.
    //이 변수에는 ConnectionMaker Type의 싱글톤 오브젝트가 들어있다.

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    //JDBC CONTEXT
    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws  SQLException{
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    //todo
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    //todo
                }
            }
        }
    }

    //Parameter를 final로 선언해줘야 한다.
    public void add(final User user) throws ClassNotFoundException, SQLException {
        jdbcContextWithStatementStrategy(
                new StatementStrategy() {
                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                        PreparedStatement ps = c.prepareStatement(
                                "insert into users(id, name, password) values(?,?,?)");
                        ps.setString(1, user.getId());
                        ps.setString(2, user.getName());
                        ps.setString(3, user.getPassword());

                        return ps;
                    }
                }
        );
    }

    public void deleteAll() throws SQLException {
        //전략생성
        jdbcContextWithStatementStrategy(
                new StatementStrategy() {
                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                        PreparedStatement ps = c.prepareStatement("delete from users");
                        return ps;
                    }
                }
        );
    }

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement(
                    "select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                    //todo
                }
            }
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException e){
                    //todo
                }
            }
            if(c != null){
                try{
                    c.close();
                }catch (SQLException e){
                    //todo
                }
            }

        }


    }

    public User get(String id) throws ClassNotFoundException, SQLException {

        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        User user = null;

        if (rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return user;
    }

}
