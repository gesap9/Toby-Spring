import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by gesap on 2017-01-31.
 */
public class TestUserService extends UserService {
    private String id;
    public TestUserService(String id){
        this.id = id;
    }


    public void upgradeLevels() throws Exception {


        TransactionSynchronizationManager.initSynchronization();
        Connection c = DataSourceUtils.getConnection(dataSource);
        c.setAutoCommit(false);

        try {
            List<User> users = userDao.getAll();
            for (User user : users) {
                if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
                    if(user.getId().equals(this.id)){
                        throw new TestUserServiceException();

                    }else {
                        userLevelUpgradePolicy.upgradeLevel(user);
                    }
                }
            }
            c.commit();
        } catch (Exception e) {
            c.rollback();
            throw e;
        } finally {

            DataSourceUtils.releaseConnection(c,dataSource);
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }
    }
}
