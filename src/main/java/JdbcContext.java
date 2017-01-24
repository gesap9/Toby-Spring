import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by gesap on 2017-01-24.
 */
public class JdbcContext {
    private DataSource dataSource;

    public void executeSql(final String query) throws SQLException {
        //전략생성
        workWithStatementStrategy(
                new StatementStrategy() {
                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                        PreparedStatement ps = c.prepareStatement(query);
                        return ps;
                    }
                }
        );
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //JDBC CONTEXT
    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
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
}
