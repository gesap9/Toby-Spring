import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.context.annotation.Bean;
import springbook.AspectBean;
import springbook.TargetImpl;

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
                        "springbook.TargetImpl.minus(int,int) " +
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

    public void pointcutMatches(String expression, Boolean expected, Class<?> clazz,
                                String methodName, Class<?>... args) throws Exception{
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);

        assertThat(pointcut.getClassFilter().matches(clazz)
        && pointcut.getMethodMatcher().matches(clazz.getMethod(methodName,args), null),is(expected));


    }
    public void targetClassPointcutMatches(String expression, boolean... expected) throws Exception{
        pointcutMatches(expression, expected[0], TargetImpl.class, "hello");
        pointcutMatches(expression, expected[1], TargetImpl.class, "hello", String.class);
        pointcutMatches(expression, expected[2], TargetImpl.class, "plus", int.class, int.class);
        pointcutMatches(expression, expected[3], TargetImpl.class, "minus",  int.class, int.class);
        pointcutMatches(expression, expected[4], TargetImpl.class, "method");
        pointcutMatches(expression, expected[5], AspectBean.class, "method");
    }

    @Test
    public void pointcut() throws Exception{
        targetClassPointcutMatches("execution(* *(..))", true,true,true,true,true,true);
    }
}
