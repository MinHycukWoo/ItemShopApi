package com.example.itemShopApi.security.jwt.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface IfLogin {
}

/**
 * 로그인정보를 읽어올때 사용하는 어노테이션
 * Bearer 토큰값이 httpHeader 에 담겨져 오게되는데
 * JWT Filter에서 요청정보에서 Beader부분을 가지고오고 토큰을 잘라서 리턴한다.
 * 스프링의 AbstractAuthenticationToken 을 상속받아 JWT토큰을 만들어준다
 * 토큰의 정보를 읽어서 정보를 초기화해서 Fiter에 반환하면
 * Filter 에서 Provider에 던져서 다시 JWT를 만들어서 반환한다
 *
 * 그 객체안에는 JWT안의 내용을 가지고 id , role 등을
 * 최종적으로 SecurityContextHolder에 Authentication 객체를 넣어서
 * 언제든지 꺼내올수 있도록 해준다.
 *
 * 
 * 사용자 지정 어노테이션
 * @Target(ElementType.PARAMETER) 어노테이션 적용대상
 * @IfLogin 어노테이션이 생성될 수 있는 위치를 지정합니다
 * PARAMETER 는 메소드의 파라미터로 선언된 객체에서만 사용할 수 있게 해줍니다.
 *
 *
 * @Retention(RetentionPolicy.RUNTIME) 어노테이션 유지정책
 * 해당 어노테이션의 정보가 런타임시에도 유지됨을 의미
 *
 * @interface
 * 어노테이션 클래스를 지정할때 사용합니다.
 */

