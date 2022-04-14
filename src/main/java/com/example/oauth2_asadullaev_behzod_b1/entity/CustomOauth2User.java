package com.example.oauth2_asadullaev_behzod_b1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomOauth2User implements OAuth2User {
    private OAuth2User oAuth2User;

    @Override
    public Map<String, Object> getAttributes() {
        return this.oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return this.oAuth2User.getName();
    }

    public String getEmail() {
        return this.oAuth2User.getAttributes().get("email").toString();
    }



}
