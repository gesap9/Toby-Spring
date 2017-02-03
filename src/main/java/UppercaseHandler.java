import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by gesap on 2017-02-03.
 */
public class UppercaseHandler implements InvocationHandler {
    Hello target;

    public UppercaseHandler(Hello target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String ret = (String)method.invoke(target, args);
        return ret.toUpperCase();

    }
}
