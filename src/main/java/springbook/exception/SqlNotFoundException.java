package springbook.exception;

/**
 * Created by gesap on 2017-03-03.
 */
public class SqlNotFoundException extends RuntimeException {
    public SqlNotFoundException(String message) {
        super(message);
    }

    public SqlNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
