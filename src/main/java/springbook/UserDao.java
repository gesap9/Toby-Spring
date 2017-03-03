package springbook;

import java.util.List;

/**
 * Created by gesap on 2017-01-26.
 */
public interface UserDao {
    void add(final User user);

    void deleteAll();

    int getCount();

    User get(String id);

    List<User> getAll();

    void update(User user);
}
