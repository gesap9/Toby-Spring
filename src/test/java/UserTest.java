import org.junit.Before;
import org.junit.Test;
import springbook.Level;
import springbook.User;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by gesap on 2017-01-31.
 */
public class UserTest {
    User user;
    @Before
    public void setUp(){
        user = new User();
    }

    @Test
    public void upgradeLevel(){
        Level[] levels = Level.values();
        for(Level level : levels){
            if(level.nextLevel() == null) continue;
            user.setLevel((level));
            user.upgradeLevel();
            assertThat(user.getLevel(), is(level.nextLevel()));

        }
    }

    @Test(expected = IllegalStateException.class)
    public void cannotUpgradeLevel(){
        Level[] levels = Level.values();
        for(Level level : levels){
            if(level.nextLevel() != null) continue;
            user.setLevel(level);
            user.upgradeLevel();


        }
    }
}
