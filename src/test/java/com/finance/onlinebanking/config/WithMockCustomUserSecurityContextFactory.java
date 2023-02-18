package com.finance.onlinebanking.config;

import com.finance.onlinebanking.domain.user.entity.Role;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.global.config.security.UserAdapter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .username("user1")
                .password("{bcrypt}$2a$10$ghRpD3/D.kI0n/bbwN0EVuAHBgCBke9KVmjo/AncFeo/bDblzZAl.")
                .build();
        user.addRole(Role.USER);

        UserAdapter principal = new UserAdapter(user);

        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
