import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gesap on 2017-02-01.
 */
@Transactional
public interface UserService {
    void add(User user);
    @Transactional(readOnly = true)
    User get(String id);
    @Transactional(readOnly = true)
    List<User> getAll();
    void deleteAll();
    void update(User user);
    void upgradeLevels();
}
