/**
 * Created by gesap on 2017-01-25.
 */
public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
