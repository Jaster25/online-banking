package com.finance.onlinebanking.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUnAuthorizedUserSecurityContextFactory.class)
public @interface WithMockCustomUnAuthorizedUser {
}
