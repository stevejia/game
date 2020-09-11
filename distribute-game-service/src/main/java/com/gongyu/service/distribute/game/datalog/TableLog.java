package com.gongyu.service.distribute.game.datalog;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableLog {
    String value() default "";
}
