import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by gesap on 2017-01-24.
 */
public interface BufferedReaderCallback {
    Integer doSomethingWithReader(BufferedReader br) throws IOException;
}
