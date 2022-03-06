package com.knuipalab.dsmp.util.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(
        {ElementType.ANNOTATION_TYPE,ElementType.METHOD}
)
@Retention(RetentionPolicy.RUNTIME) //어노테이션이 런타임 중에도 살아있습니다. 대부분 RUNTIME 어노테이션을 사용하는 것으로 보입니다.
public @interface ExeTimer {

}
