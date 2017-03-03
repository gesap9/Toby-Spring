/**
 * Created by gesap on 2017-03-02.
 */
public class SqlRetrievalFailureException extends RuntimeException{
    public SqlRetrievalFailureException(String message){
        super(message);
    }
    public SqlRetrievalFailureException(String message, Throwable cause){
        super(message, cause);
    }
}
