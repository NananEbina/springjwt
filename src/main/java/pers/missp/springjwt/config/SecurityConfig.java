package pers.missp.springjwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.CorsFilter;
import pers.missp.springjwt.jwt.JwtFilter;
import pers.missp.springjwt.jwt.JwtLoginFilter;
import pers.missp.springjwt.service.MyUserDetailService;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "corsFilter")
    CorsFilter corsFilter;
    @Resource(name = "myUserDetailService")
    MyUserDetailService myUserDetailService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService)
        .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/hello").hasRole("user")
                .antMatchers("/api/user").hasRole("user")
                .antMatchers("/admin").hasRole("admin")
                .antMatchers("/public-api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtLoginFilter("/login",authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(),UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
                .httpBasic();
//                .and()
//                .cors();
    }


}
