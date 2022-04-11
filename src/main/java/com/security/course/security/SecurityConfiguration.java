package com.security.course.security;

import com.security.course.authentication.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.security.course.security.UserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final StudentService studentService;

    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder, StudentService studentService) {
        this.passwordEncoder = passwordEncoder;
        this.studentService = studentService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
                /* Allows only those with STUDENT_ROLE to access API*/
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/courses")
                    .passwordParameter("password")
                    .usernameParameter("username")
                .and()
                .rememberMe()
                // You can use these two to extend your session using remember-me
                    .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                    .key("secure-key")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID","remember-me")
                    .logoutSuccessUrl("/login");
    }

    /**
     * These are the custom configurations that require to configured
     * in order to use the custom StudentService class that implements UserDetailService
     * for authentication
     */

    // This method wires all the things up
    @Override
    public void configure(AuthenticationManagerBuilder builder){
        builder.authenticationProvider(daoAuthenticationProvider());
    }

    // This method provides all the resources
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // this line allows passwords to be decoded
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(studentService);

        return provider;
    }

}
