package com.example.oauth2_asadullaev_behzod_b1.config;

import com.example.oauth2_asadullaev_behzod_b1.entity.CustomOauth2User;
import com.example.oauth2_asadullaev_behzod_b1.filter.JwtFilterProvider;
import com.example.oauth2_asadullaev_behzod_b1.service.CustomOAuth2UserService;
import com.example.oauth2_asadullaev_behzod_b1.service.CustomUserDetailService;
import com.example.oauth2_asadullaev_behzod_b1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilterProvider filterProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final CustomUserDetailService customUserDetailService;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .addFilterBefore(filterProvider, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/auth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                .anyRequest()
                .authenticated()
                .and().
                oauth2Login()
                .loginPage("/api/auth/login")
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
                        String token = userService.authenticate(principal);
                       if(token != null) {
                           Cookie cookie = new Cookie("token", token);
                           cookie.setMaxAge(100);
                           response.addCookie(cookie);
                           response.sendRedirect("/api/auth/cabinet");
                       }
                    }
                })
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder);
    }
}
