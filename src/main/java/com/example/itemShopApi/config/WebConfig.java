package com.example.itemShopApi.config;

import com.example.itemShopApi.security.jwt.util.IfLoginArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 *  @author Leo Woo
 *  * @version 1.0, 2024.03.29 소스 수정
 *  * @see    None
 */
//가장 기본적인 Spring MVC에 대한 설정파일
//웹에 대한 설정파일
@Configuration
public class WebConfig implements WebMvcConfigurer {

    //CORS는 개발자라면 반드시 이해해야할 내용이다.
    //CORS 에 대한 설정 , CORS는 Cross Origin Resource Sharing의 약자
    //CORS 설정은 만약 프론트 , 백 개발을 한다면
    //프론트엔드는 3000번 백엔드는 8080으로 동작
    //프론트엔드 127.0.0.1:3000 에서 8080api를 호출할수 있도록 설정
    //그래서 3000에서 요청을 받아 httpMethod어떤걸 허용할것인지 설정
    //이 설정이 없으면 프론트에서 api를 호출할수가없다
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET","POST","PATCH","PUT","OPTION","DELETE")
                .allowCredentials(true);
    }

    /**
     * ArgumentResolvers를 사용하기위해선 아래 설정을 꼭 해주어야 한다.
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(new IfLoginArgumentResolver());
    }

}
