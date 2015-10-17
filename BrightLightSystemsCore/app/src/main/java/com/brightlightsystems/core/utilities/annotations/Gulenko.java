package com.brightlightsystems.core.utilities.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation that describes a review for Michael Gulenko
 * @author Michael Gulenko
 *
 */
@Retention(value = RetentionPolicy.RUNTIME) // make available to reflections
@Target(value = {ElementType.TYPE, ElementType.METHOD,
                 ElementType.FIELD,ElementType.CONSTRUCTOR,
                 ElementType.LOCAL_VARIABLE})

@Documented
public @interface Gulenko
{
    /**Indicates what was modified*/
    String description();
}