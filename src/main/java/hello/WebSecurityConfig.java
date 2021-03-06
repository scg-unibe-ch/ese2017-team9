package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = CustomUserDetailsService.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(  "/aui", "/", "/user", "/customer", "/delivery", "/tour", "/editTour", "/editUser", "/editDelivery", "/editCustomer", "/addDeliveryPopUp", "/areYouSurePopUp").access("hasRole('ROLE_ADMIN')")
                .antMatchers( "/sui", "/driverTours", "/driverTourDeliveries", "/driverDelivery", "/driverDeliveryStatus").access("hasRole('ROLE_USER')")
                .antMatchers("/myProfile", "/editMyProfile").access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
                .anyRequest().permitAll()
                .and()
                .formLogin().successHandler(successHandler).loginPage("/login")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/login?noAccess");
    }

}
