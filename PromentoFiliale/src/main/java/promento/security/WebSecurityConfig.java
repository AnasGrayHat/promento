package promento.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	
	@Autowired
    private DataSource dataSource ;
	
	
    @Autowired
    CustomSuccessHandler customSuccessHandler;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http.csrf().disable();


//        http.authorizeRequests()
//        .antMatchers("/").hasRole("ADMIN")
//                .anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
//                .successHandler(customSuccessHandler).and().logout()
//                .permitAll().deleteCookies();
        
//		http.formLogin().loginPage("/login").successHandler(customSuccessHandler);
//		http.authorizeRequests().antMatchers("/*").hasRole("ADMIN");

        
        
    	 http.exceptionHandling().accessDeniedPage("/login");
    }

    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
               .usersByUsernameQuery("select username AS principal ,password AS  credentials, enable from user where username=?")
                .authoritiesByUsernameQuery("select username as principal , role  as role  from user where username=?");
        
        
    	
    }
	
    @Override
	public void configure(WebSecurity web) throws Exception{
    	
//    	web.ignoring().antMatchers("/resources/**","/static/**","/js/**","/css/**","/img/**","/vendor/**","/assets/**","/fonts/**");
    	
    }


}
