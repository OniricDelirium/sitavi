package com.veganSitavi;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

//import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class ProjectConfig implements WebMvcConfigurer{

    @Bean
    public LocaleResolver localeResolver(){
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.getDefault());
        slr.setLocaleAttributeName("session.current.locale");
        slr.setTimeZoneAttributeName("session.current.timezone");
        return slr;
    }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registro){
        registro.addInterceptor(localeChangeInterceptor());
    }
    
    
    /* El siguiente método se utiliza para publicar en la nube, independientemente  */
    
    @Bean
    public SpringResourceTemplateResolver templateResolver_0() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/templates");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setOrder(0);
        resolver.setCheckExistence(true);
        return resolver;
    }
    
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
//        http.authorizeHttpRequests((request) -> request.requestMatchers("/", "/index", "/error/**","/carrito/**",
//                        "/reportes/**", "/registro/**", "/js/**", "/webjars/**")
//                .permitAll()
//                .requestMatchers("/producto/nuevo", "/producto/guardar/**","/producto/eliminar/**",
//                        "/producto/modificar/**", "/categoria/nuevo/**","/categoria/guardar/**","/categoria/eliminar/**",
//                        "/categoria/modificar/**")
//                .hasRole("ADMIN")
//                .requestMatchers("/producto/listado", "/categoria/listado","/pruebas/**")
//                
//                .hasRole("USER")).formLogin((form)-> form.loginPage("/login").permitAll())
//                .logout((logout) -> logout.permitAll());
//        return http.build();
//    }

    
//   @Bean
//   public UserDetailsService users() {
//       UserDetails admin = User.builder().username("leo").password("{noop}123")
//               .roles("USER","ADMIN").build();
//       UserDetails usuario = User.builder().username("pedro").password("{noop}123")
//               .roles("USER").build();
//       
//       return new InMemoryUserDetailsManager(admin,usuario);
//   }
    
    
}


