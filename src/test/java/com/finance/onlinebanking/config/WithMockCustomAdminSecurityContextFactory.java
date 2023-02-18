package com.finance.onlinebanking.config;

import com.finance.onlinebanking.domain.user.entity.Role;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.global.config.security.UserAdapter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomAdminSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomAdmin> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomAdmin annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserEntity admin = UserEntity.builder()
                .id(1L)
                .username("admin1")
                .password("{bcrypt}$2a$10$ghRpD3/D.kI0n/bbwN0EVuAHBgCBke9KVmjo/AncFeo/bDblzZAl.")
                .build();
        admin.addRole(Role.USER);
        admin.addRole(Role.ADMIN);

        UserAdapter principal = new UserAdapter(admin);

        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
