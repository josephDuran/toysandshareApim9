package cat.itb.m13.toysandsahre.seguretat;


import cat.itb.m13.toysandsahre.model.serveis.ElMeuUserDetailsService;
import cat.itb.m13.toysandsahre.seguretat.jwt.ElMeuAuthenticationEntryPoint;
import cat.itb.m13.toysandsahre.seguretat.jwt.JWTAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class ConfiguracioSeguretatWeb extends WebSecurityConfigurerAdapter {
    private final ElMeuAuthenticationEntryPoint elmeuEntryPoint;
    private final ElMeuUserDetailsService elmeuUserDetailsService;
    private final PasswordEncoder xifrat;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().anyRequest();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
//                .cors()
//                .and()
                .httpBasic()
                .and()
                //    .exceptionHandling().authenticationEntryPoint(elmeuEntryPoint)
                //    .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //per poder accedir al h2-console
                .authorizeRequests().antMatchers("/").permitAll().and()
                .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//per poder accedir al h2-console
                //  .authorizeRequests().antMatchers("/").permitAll().and()
                //  .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                // .and()
                .csrf().disable()
                // .headers().frameOptions().disable()
                // .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }

}
