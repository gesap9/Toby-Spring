package springbook.sql;

import org.springframework.context.annotation.Import;
import springbook.context.SqlServiceContext;

/**
 * Created by gesap on 2017-03-14.
 */
@Import(value= SqlServiceContext.class)
public @interface EnableSqlService {
}
