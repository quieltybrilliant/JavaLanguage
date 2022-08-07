package annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Description {
    String value();
}

//@Target({ElementType.METHOD,ElementType.TYPE})
//@Retention(RetentionPolicy.SOURCE)
//@Inherited
//@Documented
//public @interface Description {
//    String value();
//}
