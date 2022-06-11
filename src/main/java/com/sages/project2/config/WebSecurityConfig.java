//package com.sages.project2;
//
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
//import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
//
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//@Configuration
//@EnableWebSecurity
////WebSecurityConfigurerAdapter is deprecated we should use Spring Security lambda DSL
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private static final String ADMIN_LOGIN = "LukeJaromin";
//    private static final String ROLE_ADMIN = "ROLE_ADMIN";
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login( httpSecurityOAuth2LoginConfigurer ->
//                        httpSecurityOAuth2LoginConfigurer.userInfoEndpoint(
//                                userInfoEndpointConfig ->
//                                        userInfoEndpointConfig.userAuthoritiesMapper(userAuthoritiesMapper())
//                        ));
//    }
//
//    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
//        return authorities -> {
//            final Set<GrantedAuthority> grantedAuthorities = new HashSet<>(authorities);
//            for (GrantedAuthority authority : authorities) {
//                if (authority instanceof OAuth2UserAuthority) {
//                    final Map<String, Object> attributes = ((OAuth2UserAuthority) authority).getAttributes();
//                    final String userLogin = String.valueOf(attributes.get("login"));
//
//                    if (userLogin.equals(ADMIN_LOGIN)) {
//                        System.out.println("Zalogował się " + ADMIN_LOGIN + " przez GitHub, dostał rolę admina!");
//                        grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_ADMIN));
//                    }
//                }
//            }
//            return grantedAuthorities;
//        };
//    }
//
//}
