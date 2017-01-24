import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;

/**
 * Created by gesap on 2017-01-24.
 */
public class CalcSumTest {
    @Test
    public void sumOfNumbers() throws IOException{
        Calculator calculator = new Calculator();
        int sum = calculator.calcSum(getClass().getResource("numbers.txt").getPath());
        assertThat(sum, is(10));
    }
}
