package org.isvi.powercache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a class that can get the value from the cache
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME )
public @interface Getter { }
