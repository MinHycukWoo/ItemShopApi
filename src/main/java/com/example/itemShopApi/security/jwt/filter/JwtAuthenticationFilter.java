package com.example.itemShopApi.security.jwt.filter;

import com.example.itemShopApi.security.jwt.exception.JwtExceptionCode;
import com.example.itemShopApi.security.jwt.token.JwtAuthenticationToken;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 *  @author Leo Woo
 *  * @version 1.0, 2024.03.29 소스 수정
 *  * @see    None
 */

/*request에 인증값을 판단하는 용도*/
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token="";    //여기 jwt를 받아오게된다.
        log.info("request = {}" , request);
        log.info("request = {}" , getToken(request));
        try{
            token = getToken(request);
            if(StringUtils.hasText(token)){
                getAuthentication(token);
            }
            filterChain.doFilter(request, response);

        }catch(NullPointerException | IllegalStateException e){
            request.setAttribute("exception", JwtExceptionCode.NOT_FOUND_TOKEN.getCode());
            log.error("Not found Token // token:{}" , token);
            log.error("Set Request Exception Code : {}" , request.getAttribute("exception"));
            throw new BadCredentialsException("throw new not found token exception");
        }catch(SecurityException | MalformedJwtException e){
            request.setAttribute("exception", JwtExceptionCode.INVALID_TOKEN.getCode());
            log.error("Invalid Token // token:{}" , token);
            log.error("Set Request Exception Code : {}" , request.getAttribute("exception"));
            throw new BadCredentialsException("throw new invalid token exception");
        }catch(ExpiredJwtException e){
            request.setAttribute("exception", JwtExceptionCode.EXPIRED_TOKEN.getCode());
            log.error("EXPIRED Token // token:{}" , token);
            log.error("Set Request Exception Code : {}" , request.getAttribute("exception"));
            throw new BadCredentialsException("throw new expired token exception");
        }catch(UnsupportedJwtException e){
            request.setAttribute("exception", JwtExceptionCode.UNSUPPORTED_TOKEN.getCode());
            log.error("Unsupported Token // token:{}" , token);
            log.error("Set Request Exception Code : {}" , request.getAttribute("exception"));
            throw new BadCredentialsException("throw new unsupported token exception");
        }catch(Exception e){
            log.error("================================");
            log.error("JwtFilter = doFilterInternal() 오류발생");
            log.error("token:{}" , token);
            log.error("Exception message : {}" , e.getMessage());
            log.error("Exception StackTrace : {");
            e.printStackTrace();
            log.error("}");
            log.error("================================");
            throw new BadCredentialsException("throw new exception");
        }
    }

    private void getAuthentication(String token){
        //log.info("getAuthentication = {}" , token);
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(token);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        log.info("authenticate ++ = {}" , authenticate);
        SecurityContextHolder.getContext()
                .setAuthentication(authenticate);
    }

    private String getToken(HttpServletRequest request){
        String authorication = request.getHeader("Authorization");
        if (StringUtils.hasText(authorication) && authorication.startsWith("Bearer")) {
            String[] arr = authorication.split(" ");
            return arr[1];
            /*http header에서 Authorization을 찾아서 Bearer로 시작한다면 split(" ")공백으로 자른다.*/
        }
        return null;
    }
}
