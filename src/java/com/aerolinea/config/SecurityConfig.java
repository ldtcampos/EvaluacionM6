package com.aerolinea.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    

    @Autowired
    DriverManagerDataSource dataSource;
    @Autowired
    Codificar codificar;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select idusuario, clave, true from usuario where idusuario=?")
                .authoritiesByUsernameQuery(
                        "select u.idusuario, concat('ROLE_',r.rol) from usuario u, rol r where u.idrol=r.idrol and u.idusuario=?")
                .passwordEncoder(codificar);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/").hasAuthority("ROLE_ADMIN")
                .antMatchers("/index.xhtml").authenticated()
                .antMatchers("/Pais/**", "/usuarios/**", "/login.xhtml", "/reservacion.xhtml").hasAuthority("ROLE_USUARIO")
                .antMatchers("/Pais/**", "/ventas/**", "/login.xhtml", "/reservacion.xhtml").hasAuthority("ROLE_VENDEDOR")
                .antMatchers("/usuarios/**", "/gerencia/**", "/ventas/**", "/login.xhtml", "/reservacion.xhtml").hasAuthority("ROLE_GERENTE")
                .anyRequest().permitAll().and()
                .formLogin().and()
                .httpBasic();
        http
                .requiresChannel().anyRequest().requiresSecure()
                .and()
                //.formLogin().loginPage("/login.xhtml")
                //.defaultSuccessUrl("/index.xhtml").and()
                //.exceptionHandling().accessDeniedPage("/AccesoDenegado.xhtml")
                //.and()
                .rememberMe()
                .tokenValiditySeconds(3600)
                .and()
                .logout().logoutUrl("/salir")
                .logoutSuccessUrl("/login.xhtml");
        http
                .portMapper() //maps the port 8080(http) to 8443(https)
                .http(8080).mapsTo(8091);

    }
}
