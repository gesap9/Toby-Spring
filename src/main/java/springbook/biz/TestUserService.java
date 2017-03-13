package springbook.biz;

import springbook.exception.TestUserServiceException;

import java.util.List;

/**
 * Created by gesap on 2017-01-31.
 */
public class TestUserService extends UserServiceImpl {
    private String id = "madnite1";


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

    public List<User> getAll(){
        for(User user:super.getAll()){
            super.update(user);
        }
        return null;
    }
}
