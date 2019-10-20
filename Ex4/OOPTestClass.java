package OOP.Solution;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OOPTestClass {
    enum OOPTestClassType {
        ORDERED,
        UNORDERED;

        public OOPTestClassType change(){
            if (this.equals(ORDERED)){
                return UNORDERED;
            }
            return ORDERED;
        }
    }
    OOPTestClassType value() default OOPTestClassType.UNORDERED;
}
