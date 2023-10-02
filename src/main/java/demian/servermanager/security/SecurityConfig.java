package demian.servermanager.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService users() {
        UserDetails staff = User.builder()
                .username("staff")
                .password("{noop}1111")
                .roles("STAFF")
                .build();
        UserDetails manager = User.builder()
                .username("manager")
                .password("{noop}1111")
                .roles("MANAGER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}1111")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(staff, manager, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((authz) -> authz
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()   //정적자원 접근허용
                    .requestMatchers("/main").permitAll()
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/manager").hasRole("MANAGER")
                    .requestMatchers("/staff").hasRole("STAFF")
                    .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .defaultSuccessUrl("/main", true)
                        .loginProcessingUrl("/login")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                );
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }

}
