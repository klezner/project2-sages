package com.sages.project2.common;


import com.sages.project2.domain.ports.in.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userAuthoritiesMapper(this.userAuthoritiesMapper())))
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        http.csrf().disable();
        return http.build();
    }

    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority -> {
                if (authority instanceof OAuth2UserAuthority oauth2UserAuthority) {

                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

                    String login = (String) userAttributes.get("login");
                    if (userService.isAdmin(login)) {
                        mappedAuthorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList(
                                "ROLE_ADMIN"));
                    } else {
                        mappedAuthorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList(
                                "ROLE_USER"));
                    }
                }
            });

            return mappedAuthorities;
        };
    }

}
