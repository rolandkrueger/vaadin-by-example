package de.oio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
public class VaadinSecuredWithSpringSecurityApplication extends WebSecurityConfigurerAdapter {

    private static Logger LOG = LoggerFactory.getLogger(VaadinSecuredWithSpringSecurityApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(new Object[]{VaadinSecuredWithSpringSecurityApplication.class,}, args);
    }

    /**
     * Configure Spring Security for this application.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                // permit access to any resource, access restrictions are handled at the level of Vaadin views
                .authorizeRequests()
                .antMatchers("/**").permitAll()
        .and()
                        // disable CSRF (Cross-Site Request Forgery) since Vaadin implements its own mechanism for this
                .csrf().disable()
                // let Vaadin be responsible for creating and managing its own sessions
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
        // @formatter:on
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                final Throwable cause = exception.getCause();
                LOG.error("An error occurred: {}", exception.getClass().getName());
                exception.printStackTrace();
                if (cause != null) {
                    LOG.error("        Caused by: {}", cause.getClass().getName());
                }
                response.sendRedirect("/error");
            }
        };
    }
}
