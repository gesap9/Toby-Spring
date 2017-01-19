/**
 * Created by gesap on 2017-01-19.
 */
public class DaoFactory {
    public UserDao userDao(){
        UserDao userDao = new UserDao(connectionMaker());
        return userDao;
    }

    private ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }


}
