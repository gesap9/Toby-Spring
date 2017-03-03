import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.Message;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by gesap on 2017-02-03.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class FactoryBeanTest {
    @Autowired
    ApplicationContext context;

    @Autowired
    Message message2;

    @Test
    public void getMessageFromFactoryBean(){
        Object message = context.getBean("message");
        assertThat(message, is(Message.class));
        assertThat(((Message)message).getText(), is("Factory Bean"));
    }

    @Test
    public void getMessageFromFactoryBeanDirectly(){

        assertThat(message2, is(Message.class));
        assertThat((message2).getText(), is("Factory Bean2"));
    }
}
