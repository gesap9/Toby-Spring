import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * Created by gesap on 2017-01-31.
 */
public class TestUserService extends UserServiceImpl {
    private String id;
    public TestUserService(String id){
        this.id = id;
    }


    public void upgradeLevels(){
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

    }
}
