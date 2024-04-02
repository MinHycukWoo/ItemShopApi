package com.example.itemShopApi.security.jwt.util;


import com.example.itemShopApi.security.jwt.token.JwtAuthenticationToken;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


import java.util.Collection;
import java.util.Iterator;

public class IfLoginArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * ArgumentResolver 는 컨트롤러에 들어가는 값이 있다
     * 그럼 그거에따라 어떻게 동작해서 값을 넣어주겟다는것이다.
     * 즉
     * 컨트롤러의 파라미터를 보고 값을 조작해서 넣어줄 수 있다.
     */

    /**
     * supportsParameter()
     * 컨트롤러 메소드의 특정 파라미터를 지원하는지 판단합니다.
     * 즉 @IfLogin이 붙어있고 파라미터 클래스타입인 IfLogin.class인 경우 참을 반환합니다.
     *
     * resolveArgument()
     * 파라미터에 전달할 객체를 생성합니다.
     * 여기서는 세션에 객체를 가져오게 됩니다.
     *
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(IfLogin.class) != null
                && parameter.getParameterType() == LoginUserDto.class;
        /**
         * 파라미터에 IfLogin 어노테이션이 있고 
         * 그 파라미터가 받는 값의 형태가 LoginUserDto.class; 일 경우
         * 값을 넣어줄지 판단하게 된다.
         */
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = null;

        try{
            authentication = SecurityContextHolder.getContext().getAuthentication();
        }catch(Exception ex){
            return null;
        }
        if(authentication == null){
            return null;
        }

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        LoginUserDto loginUserDto = new LoginUserDto();
        System.out.println("jwtAuthenticationToken ++" + jwtAuthenticationToken);
        Object principal = jwtAuthenticationToken.getPrincipal();//LoginInfoDto
        System.out.println("principal ++" + principal); //test7@gmail.com?
        if(principal == null){
            return null;
        }
        LoginInfoDto loginInfoDto = (LoginInfoDto) principal;
        loginUserDto.setMemberId(loginInfoDto.getMemberId());
        loginUserDto.setEmail(loginInfoDto.getEmail());
        //loginUserDto.setEmail((String) principal);
        /*
        LoginInfoDto loginInfoDto = (LoginInfoDto) principal;
        loginUserDto.setEmail(loginInfoDto.getEmail());
        loginUserDto.setMemberId(loginInfoDto.getMemberId());
        loginUserDto.setName(loginInfoDto.getName());

         */
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        while(iterator.hasNext()){
            GrantedAuthority grantedAuthority = iterator.next();
            String role = grantedAuthority.getAuthority();
            //System.out.println(role);
            loginUserDto.addRole(role);
        }

        return loginUserDto;
    }
}
