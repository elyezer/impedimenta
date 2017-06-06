package edu.vtc.cis3720.jxa03200;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** An annotation for indicating that code reviews have been performed. */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Reviews {
    /** The reviews that have been performed. */
    public Review[] value();
}
