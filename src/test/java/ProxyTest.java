import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
/**
 * Created by gesap on 2017-02-03.
 */
public class ProxyTest {
    @Test
    public void simpleProxy(){
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("Toby"), is("Hello Toby") );
        assertThat(hello.sayHi("Toby"), is("Hi Toby") );
        assertThat(hello.sayThankYou("Toby"), is("Thank you Toby") );
    }

    @Test
    public void uppercaseProxy(){
        Hello proxiedHello = new HelloUppercase(new HelloTarget());
        assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
        assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
        assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));

    }
}
