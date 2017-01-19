/**
 * Created by gesap on 2017-01-19.
 */
public class DaoFactory {
    public UserDao userDao(){
        ConnectionMaker connectionMaker = new DConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);

        return userDao;
    }
}
