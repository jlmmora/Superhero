package com.plexus.w2m.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
//La anotación @Target nos dice dónde se aplicará nuestra anotación.
// ElementType.Method, significa que solo funcionará en métodos.
public @interface LogExecutionTime {
}