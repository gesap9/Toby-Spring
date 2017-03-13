package springbook.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by gesap on 2017-02-06.
 */
@Deprecated
public class TransactionAdvice implements MethodInterceptor {
    private PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public TransactionAdvice() {
        System.out.println(this.getClass().getSimpleName() + " executed!");
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {

        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{
            Object ret = invocation.proceed();
            this.transactionManager.commit(status);
            return ret;
        }catch (RuntimeException e){
            this.transactionManager.rollback(status);
            throw e;
        }
    }
}
