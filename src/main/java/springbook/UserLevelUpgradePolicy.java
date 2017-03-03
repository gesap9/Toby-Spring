package springbook;

/**
 * Created by gesap on 2017-01-31.
 */
public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(User user);
    void upgradeLevel(User user);
}
