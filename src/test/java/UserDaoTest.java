import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.Level;
import springbook.User;
import springbook.UserDao;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by gesap on 2017-01-19.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserDaoTest {

    @Autowired
    private UserDao dao;
    @Autowired
    private DataSource dataSource;
    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp() {
        user1 = new User("park", "박성철", "park123",Level.BASIC,1,0, "park@mail.com");
        user2 = new User("lee", "이노옴", "lee123", Level.SILVER,20,30, "lee@mail.com");
        user3 = new User("kim", "김하가", "kim123",Level.GOLD,33,1,"kim@mail.com");
    }
    @Test
    public void update(){
        dao.deleteAll();
        dao.add(user1);
        dao.add(user2);

        user1.setName("오민규");
        user1.setPassword("12323");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        user1.setEmail("new@mail.com");

        dao.update(user1);

        User user1update = dao.get(user1.getId());
        checkSameUser(user1, user1update);
        User user2same = dao.get(user2.getId());
        checkSameUser(user2,user2same);


    }
    @Test
    public void sqlExceptionTranslate(){
        dao.deleteAll();

        try {
            dao.add(user1);
            dao.add(user1);
        } catch (DuplicateKeyException e) {

            SQLException sqlEx = (SQLException)e.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

            /*
            책에는
            assertThat(set.translate(null,null,sqlEx),
                    is(DuplicateKeyException.class));
            로 되어 있는데 이렇게 하면 오류남
            */
            assertThat(set.translate(null,null,sqlEx),
                    CoreMatchers.instanceOf(DuplicateKeyException.class));
        }

    }

    @Test(expected = DuplicateKeyException.class)
    public void duplicatedKey(){
        dao.deleteAll();

        dao.add(user1);
        dao.add(user1);//강제로 같은 사용자 두 번 등록
    }

    @Test
    public void count() throws SQLException, ClassNotFoundException {

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));

    }

    @Test
    public void addAndGet(){

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User userget1 = dao.get(user1.getId());
        checkSameUser(userget1, user1);

        User userget2 = dao.get(user2.getId());
        checkSameUser(userget2, user2);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("unknown id");
    }

    @Test
    public void getAll() throws SQLException, ClassNotFoundException {
        dao.deleteAll();

        List<User> users0 = dao.getAll();
        assertThat(users0.size(), is(0));

        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertThat(users1.size(), is(1));
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertThat(users2.size(), is(2));
        checkSameUser(user2, users2.get(0));
        checkSameUser(user1, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertThat(users3.size(), is(3));
        checkSameUser(user3, users3.get(0));
        checkSameUser(user2, users3.get(1));
        checkSameUser(user1, users3.get(2));


    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
        assertThat(user1.getLevel(), is(user2.getLevel()));
        assertThat(user1.getLogin(), is(user2.getLogin()));
        assertThat(user1.getRecommend(), is(user2.getRecommend()));
        assertThat(user1.getEmail(), is(user2.getEmail()));
    }


}

