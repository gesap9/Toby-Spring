import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.context.annotation.Bean;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
/**
 * Created by gesap on 2017-02-08.
 */
public class AspectjExpressionTest {
    @Test
    public void methodSignaturePointcut() throws SecurityException, NoSuchMethodException{
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(
                "execution(public int " +
                        "TargetImpl.minus(int,int) " +
                        "throws java.lang.RuntimeException)"
        );
        System.out.println(TargetImpl.class);
        assertThat(pointcut.getClassFilter().matches(TargetImpl.class) &&
        pointcut.getMethodMatcher().matches(
                TargetImpl.class.getMethod("minus", int.class, int.class), null),is(true
        ));

        assertThat(pointcut.getClassFilter().matches(TargetImpl.class) &&
        pointcut.getMethodMatcher().matches(
                TargetImpl.class.getMethod("plus", int.class, int.class), null),is(false
        ));

        assertThat(pointcut.getClassFilter().matches(Bean.class) &&
        pointcut.getMethodMatcher().matches(
                TargetImpl.class.getMethod("method"),null),is(false
        ));

    }
}
