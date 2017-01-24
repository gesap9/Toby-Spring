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


    public void add(User user) throws ClassNotFoundException, SQLException {
        StatementStrategy st = new AddStatement(user);
        jdbcContextWithStatementStrategy(st);
    }

    public void deleteAll() throws SQLException {
        //전략생성
        StatementStrategy st = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(st);
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
