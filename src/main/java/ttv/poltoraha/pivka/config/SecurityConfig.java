package ttv.poltoraha.pivka.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ttv.poltoraha.pivka.serviceImpl.UserDetailsServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * В текущем проекте система секьюрки представляет собой следующее:
 * У нас есть пользователи, которые хранятся в БД. Логин/пароль
 * У нас есть конфиг тут, где через authorizeHttpRequests можно вводить ограничения,
 * чтобы не давать обычным пользакам добавлять новые книги
 *
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                // без этой штуки вам не даст авторизоваться в веб-окошке бд h2
                .csrf()
                .disable()
                .cors()
                .disable()
                .headers(headers -> headers.frameOptions().sameOrigin());

//        http.authorizeRequests().requestMatchers("/admin/**").hasRole("ADMIN")
//                .requestMatchers("/**").permitAll().anyRequest().authenticated()
//                .and().formLogin().permitAll().and().logout().permitAll().and().httpBasic();
//                http.cors().disable().csrf().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        /*
        Возможно так, но мне ГПТ написал весь код и потом еще час объяснял что я тупой, потому что нихуя не понял.

        Из его объяснений я понял следующее:
        Пароль должен как-то шифроваться, условно через {noop}, {bcrypt} и тд. Суть в том, что перед паролем стоит тип
        шифрования в фигурных скобках. У нас есть 'password', который является noop, то есть без шифрования. Однако из-за
        отсутствия фигурных скобок, спринг в душе не ебёт чё с этим паролем делать.

        Мне, по сути, нужно было написать декриптер, который бы приравнивал такие записи без '{}' к noop.

        Соответственно я создаю объект DelegatingPasswordEncoder, в который передаю 2 параметра:
        1. Шифрование, которое используется по умолчанию для КОДИРОВАНИЯ;
        2. Мапу всех доступных шифрований, чтобы если че, мы знали как наш пароль расшифровать.

        Далее у этой хуйни есть метод .setDefaultPasswordEncoderForMatches(), который применяется если в мапе нет
        подходящего метода шифрования. Мы как бы говорим, чтобы если ничего не нашлось - используй то, что я тебе скажу
        (в данному случае 'noop').

        Оно вроде логично, но мне не нравится, что идея ругается на NoOpPasswordEncoder и то, что он устарел. Небезопасно,
        в прод такое нельзя.
         */


        String defaultEncoderId = "noop";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("bcrypt", new BCryptPasswordEncoder());

        DelegatingPasswordEncoder delegatingPasswordEncoder =
                new DelegatingPasswordEncoder(defaultEncoderId, encoders);

        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());

        return delegatingPasswordEncoder;
    }
}
