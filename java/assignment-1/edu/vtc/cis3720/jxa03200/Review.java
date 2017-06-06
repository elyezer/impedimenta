package edu.vtc.cis3720.jxa03200;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** An annotation for indicating that a code review has been performed. */
@Repeatable(Reviews.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Review {
    /** Who performed the review? */
    public String name();

    /** When was the review performed? */
    // TODO: Maybe check the strings passed to this argument. We use the String
    // type because annotations may use a tightly restricted set of types. But
    // that restrains our ability to validate the values passed by the user.
    public String date();

    /** What issues were checked for? */
    public Issue issue();

    /** Were changes made during this review? */
    public boolean changed() default false;
}
