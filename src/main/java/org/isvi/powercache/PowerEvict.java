package org.isvi.powercache;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME )
public @interface PowerEvict {

    @AliasFor("dumper")
    String value() default "";

    @AliasFor("value")
    String dumper() default "";

    boolean beforeExecution() default false;
}
