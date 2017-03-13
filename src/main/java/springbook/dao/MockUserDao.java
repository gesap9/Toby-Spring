package springbook.dao;

import springbook.biz.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gesap on 2017-02-01.
 */
public class MockUserDao implements UserDao {

    private List<User> users;
    private List<User> updated = new ArrayList<User>();

    public MockUserDao(List<User> users) {
        this.users = users;
    }

    public List<User> getUpdated() {
        return updated;
    }

    public List<User> getAll() {
        return this.users;
    }

    public void update(User user) {
        updated.add(user);
    }

    public void add(User user) {
        throw new UnsupportedOperationException();
    }

    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    public int getCount() {
        throw new UnsupportedOperationException();
    }

    public User get(String id) {
        throw new UnsupportedOperationException();
    }


}
