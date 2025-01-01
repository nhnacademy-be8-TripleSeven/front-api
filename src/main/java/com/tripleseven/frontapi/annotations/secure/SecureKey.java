package com.tripleseven.frontapi.annotations.secure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // 필드에서만 사용
@Retention(RetentionPolicy.RUNTIME) // 런타임에도 유지
public @interface SecureKey {
    String value(); // YML의 변수 키 이름
}