package com.example.itemShopApi.security.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTokenizerTest {

    @Autowired
    JwtTokenizer jwtTokenizer;
    
    @Value("${jwt.secretKey}") //application.yml에 시크릿값
    String accessSecret;//12345678901234567890123456789012
    public final Long ACCESS_TOKEN_EXPIRE_COUNT = 30 * 60 * 1000L; //30분

    /**
     * test1 builder에 회원정보를 넣어서 JWT 토큰 생성
     * @throws Exception
     */
    @Test
    public void createToken() throws Exception{//JWT 토큰 생성
        String email = "urstory@gamil.com";
        List<String> roles = List.of("ROLE_USER"); // [ROLE_USER]
        Long id = 1L;
        //Jws를 사용해서 Claims 를 생성해서 setSubject 즉 payload 단만 가져온다
        //payload 에 정해진 구조중에 sub에 email을 세팅한다.
        Claims claims = Jwts.claims().setSubject(email); // JWT토큰의 payload에 들어갈 내용 claims 를 설정
        /**
         * claims
         * --sub -- email
         * roles -- ["ROLE_USER"]
         * memberId -- 1L
         */
        //없는값들은 put으로 밀어넣어준다.
        claims.put("roles" , roles);
        claims.put("memberId" , id);

        //application yml 에 시크릿 키 필드 값을 가져온다
        byte[] accessSecret = this.accessSecret.getBytes(StandardCharsets.UTF_8);

        //JWT 를 생성하는 부분
        //builder 를 사용해서 jwtBuilder 로 Jwt를 생성해준다.
        //이런걸 builder 패턴이라고 한다. 계속 필요한것들을 추가해가며 필드하나를 만들어준다.
        //chaning 방법.
        String JwtToken = Jwts.builder() //builder 는 JwtBuilder 를 반환 Builder패턴
                .setClaims(claims) //claims가 추가된 JwtBuilder를 리턴
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + this.ACCESS_TOKEN_EXPIRE_COUNT)) //현재 시간으로부터 30분 뒤에 만료
                .signWith(Keys.hmacShaKeyFor(accessSecret)) //결과에 서명까지 포함시킨 JwtBuilder 리턴
                //ㄴ 이부분이 키 값을 넣어줘서 조작을 방지시키는 부분이다.
                .compact(); //최종적으로 만들어진 JWT토큰을 반환한다.

        System.out.println("text1 Result = " + JwtToken);


    }

    /**
     * text2 서명이 같은지 검증한다.
     * 1. 일단 jwt의 변조 확인을 위해서 생성할떄와 같은 key값이 필요하다
     * 2. 발급받은 jwt 를 넣어준다.
     * 3. 여기서  권한정보 등이 들어있는 payload 부분을 가져와야한다.
     * 4. parserBuilder 로 분석해서 값을 받아온다.
     * @throws Exception
     */
    @Test
    public void parseToken() throws Exception{
        byte[] accessSecret = this.accessSecret.getBytes(StandardCharsets.UTF_8);
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cnN0b3J5QGdhbWlsLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJtZW1iZXJJZCI6MSwiaWF0IjoxNzExODE5ODUzLCJleHAiOjE3MTE4MjE2NTN9.CRh-_ULfOQVVHxn8L0nqLHSuU9OwFLvzZ__CPh8uJn4";

        //parserBuilder 를 활용하여 jwt를 분석
        Claims claims = Jwts.parserBuilder() //JwtParserBuilder를 반환
                .setSigningKey(Keys.hmacShaKeyFor(accessSecret))// 일단 키값을 세팅해준다
                .build() //parserBuilder 의 build 이니 여기서 parser를 생성한다
                .parseClaimsJws(jwtToken) //분석할 JWT 를 넣어준다
                .getBody(); //거기서 payload만들 받아온다
        //! 조작을 방지하기 위해 시크릿키는 절대 노출되어서는 안된다!

        System.out.println(claims.getSubject());
        System.out.println(claims.get("roles"));
        System.out.println(claims.get("memberId"));
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.getExpiration());


    }

    //이 test1 test2는 JwtTokenizer 의 동작방식을 설명한것이다.

    @Test
    public void createJWT(){
        String jwtToken = jwtTokenizer.createAccessToken(1L , "urstory@gmail.com" , "김성박" , List.of("ROLE_USER"));
    }

    @Test
    public void parseJWT(){
        Claims claims = jwtTokenizer.parseAccessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cnN0b3J5QGdtYWlsLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJ1c2VySWQiOjEsImlhdCI6MTcxMTc5NDA1MiwiZXhwIjoxNzExNzk0OTUyfQ.jsUHdEwwSC7AgEbvU3HYSeJAVNOnl4Ifh8VoG9fPE3g");
        System.out.println(claims.getSubject());
        System.out.println(claims.get("roles"));
        System.out.println(claims.get("memberId"));
    }


}