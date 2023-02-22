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
                .id(10L)
                .username("admin1")
                .password("{bcrypt}$2a$10$sjL4PxC.0Iz2nVqs0TGh4uI1XQODdfuV5LqpZeU35dxvx2h.v5U0i")
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
