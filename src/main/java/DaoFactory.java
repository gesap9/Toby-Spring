import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by gesap on 2017-01-19.
 */
@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao(){
        UserDao userDao = new UserDao(connectionMaker());
        return userDao;
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }


}