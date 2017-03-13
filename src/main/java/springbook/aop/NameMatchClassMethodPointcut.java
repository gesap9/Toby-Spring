package springbook.aop;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.util.PatternMatchUtils;

/**
 * Created by gesap on 2017-02-06.
 */
@Deprecated
public class NameMatchClassMethodPointcut extends NameMatchMethodPointcut {
    public void setMappedClassName(String mappedClassName){
        this.setClassFilter(new SimpleClassFilter(mappedClassName));

    }
    static class SimpleClassFilter implements ClassFilter{

        String mappedName;

        public SimpleClassFilter(String mappedName) {
            this.mappedName = mappedName;
        }

        public boolean matches(Class<?> clazz) {
            return PatternMatchUtils.simpleMatch(mappedName,
                    clazz.getSimpleName());
        }
    }
}
