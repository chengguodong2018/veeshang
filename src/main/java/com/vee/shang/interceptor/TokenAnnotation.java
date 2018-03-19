package com.vee.shang.interceptor;

import java.lang.annotation.*;

/**
 * @author cgd
 * @date 2017/12/18.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface TokenAnnotation {
    String value() default "";
}
