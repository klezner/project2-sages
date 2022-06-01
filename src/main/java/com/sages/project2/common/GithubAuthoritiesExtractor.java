//package com.sages.project2.common;
//
//import com.sages.project2.domain.ports.in.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//public class GithubAuthoritiesExtractor implements AuthoritiesExtractor {
//
//    private final UserService userService;
//
//    @Override
//    public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
//        String login = (String) map.get("login");
//        if (userService.isAdmin(login)) {
//            return AuthorityUtils.commaSeparatedStringToAuthorityList(
//                    "ROLE_ADMIN");
//        }
//        return AuthorityUtils.commaSeparatedStringToAuthorityList(
//                "ROLE_USER");
//    }
//}
