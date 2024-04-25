package com.example.bidhub.config;

import com.example.bidhub.jwt.JwtProvider;
import com.example.bidhub.jwt.TokenExceptionResponse;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {
    private final JwtProvider jwtProvider;
    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable()) // rest 서버라서 csrf 사용 안 함
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/**").permitAll()
//                                .requestMatchers("/member/signup", "/member/login", "/member/token").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement((sessionManagement) -> // jwt 토큰을 사용할 예정이므로 세션을 사용하지 않는다.
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
//                .addFilterBefore(new TokenAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling((exceptions) ->
//                        exceptions
//                                .authenticationEntryPoint(jwtException())) ;
        return http.build();
    }
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // 401, 403 Error Handler
    private AuthenticationEntryPoint jwtException() {

        AuthenticationEntryPoint ap = (request, response, authException)->{

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            TokenExceptionResponse res = new TokenExceptionResponse();

            String message = (String) request.getAttribute("TokenException");
            if(message!=null) { // UnAuthenticated
                response.setStatus(401);
                res.setResult(message);
            }
            else {
                response.setStatus(403);
                res.setResult(authException.getMessage());
            }
            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(res));
        };
        return ap;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000/")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Authorization", "Content-Type")
                .exposedHeaders("Custom-Header")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
