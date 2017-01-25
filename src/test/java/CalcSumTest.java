import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;

/**
 * Created by gesap on 2017-01-24.
 */
public class CalcSumTest {
    Calculator calculator;
    String numFilepath;

    @Before
    public void setUp(){
        this.calculator = new Calculator();
        this.numFilepath = getClass().getResource("numbers.txt").getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {
        assertThat(calculator.calcSum(this.numFilepath), is(10));
    }

    @Test
    public void mutiplyOfNumbers() throws IOException{
        assertThat(calculator.calcMultiply(this.numFilepath), is(24));
    }
}
