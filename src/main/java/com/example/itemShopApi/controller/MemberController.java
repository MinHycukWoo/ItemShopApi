package com.example.itemShopApi.controller;

import com.example.itemShopApi.domain.Member;
import com.example.itemShopApi.domain.RefreshToken;
import com.example.itemShopApi.domain.Role;
import com.example.itemShopApi.dto.*;
import com.example.itemShopApi.security.jwt.util.IfLogin;
import com.example.itemShopApi.security.jwt.util.JwtTokenizer;
import com.example.itemShopApi.security.jwt.util.LoginUserDto;
import com.example.itemShopApi.service.MemberService;
import com.example.itemShopApi.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.reflect.MemberSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "JWT 컨트롤러", description = "JWT 로그인 API입니다.")

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    //ResponseEntity jpa 에서 restful api 응답을 보내줄떄 담아주는것
    //BindingResult bindingResult 는 valid 와 한세트
    //인증시 문제가 생기면 BindingResult bindingResult가 에러를 반환
    //@RequestBody 컨트롤러 메소드에 @RequestBody라고 적혀있으면
    //httpBody로 값을 받는다는 의미다
    @PostMapping("/signup")
    @Operation(summary = "회원가입" , description = "JWT 사용자 등록을 합니다.")
    public ResponseEntity signup (
            @Parameter(name="memberSignupDto" ,description = "회원가입 정보")
            @RequestBody @Valid MemberSignupDto memberSignupDto, BindingResult bindingResult){
        System.out.println("memberSignupDto ++ " + memberSignupDto);
        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        //member 객체에 받아온 값을 넣어준다.
        Member member = new Member();
        member.setName(memberSignupDto.getName());
        member.setEmail(memberSignupDto.getEmail());
        member.setPassword(passwordEncoder.encode(memberSignupDto.getPassword()));
        //암호화 사용!
        //member.setBirthYear(Integer.parseInt(memberSignupDto.getBirthYear()));
        //member.setBirthMonth(Integer.parseInt(memberSignupDto.getBirthMonth()));
        //member.setBirthDay(Integer.parseInt(memberSignupDto.getBirthDay()));
        //member.setGender(memberSignupDto.getGender());

        Member saveMember = memberService.addMember(member);

        //API 반환값으로 사용할 DTO
        MemberSignupResponseDto memberSignupResponse = new MemberSignupResponseDto();
        memberSignupResponse.setMemberId(saveMember.getMemberId());
        System.out.println("saveMember.getMemberId()==" + saveMember.getMemberId());
        memberSignupResponse.setName(saveMember.getName());
        memberSignupResponse.setRegdate(saveMember.getRegdate());
        memberSignupResponse.setEmail(saveMember.getEmail());

        //회원가입
        return new ResponseEntity(memberSignupResponse , HttpStatus.CREATED);

        //return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/login")
    @Operation(summary = "로그인" , description = "JWT 토큰을 발급합니다.")
    public ResponseEntity login(
            @Parameter(name="loginDto" ,description = "로그인 정보")
            @RequestBody @Valid MemberLoginDto loginDto , BindingResult bindingResult){
        //TODO email에 해당하는 사용자 정보를 읽어와서 암호가 맞는지 검사하는 코드가 있어야한다.
        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        //email이 없을 경우 Exception이 발생한다, Global Exception에 대한 처리가 필요하다
        Member member = memberService.findByEmail(loginDto.getEmail());
        //member엔티티를 받아온다.

        //암호화된 DB의 PW와 입력받은 PW가 같은값인지 매칭시켜야 한다.
        if(!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<String> roles = member.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        //member 가가진 Set<Role>을 스트림으로 바꿔서 그 리스트를 리스트로 바꾸겟다는것
        //Role::getName Role 객체 안에있는 getName 메서드를 사용하겟단것

        //JWT토큰을 생성하였다 jwt라이브러리를 이용하여 생성
        String accessToken = jwtTokenizer.createAccessToken(member.getMemberId() , member.getEmail() ,member.getName() , roles);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getMemberId() , member.getEmail() ,member.getName() , roles);

        //사용자가 로그인을 요청해서 아이디 암호가 맞으면이 리프레쉬토큰을 DB에 저장하는데
        //성능 떄문에 메모리 DB에 저장하는게 좋을수도 있다.
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setMemberId(member.getMemberId());
        log.info("refreshTokenEntity  = {}" , refreshTokenEntity);
        refreshTokenService.addRefreshToken(refreshTokenEntity);

        MemberLoginResponseDto loginResponse = MemberLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(member.getMemberId())
                .nickname(member.getName())
                .build();
        return new ResponseEntity(loginResponse , HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    @Operation(summary = "로그아웃" , description = "JWT 토큰을 삭제합니다..")
    public ResponseEntity logout(
            @Parameter(name="refreshTokenDto" ,description = "Refresh 토큰정보")
            @RequestBody RefreshTokenDto refreshTokenDto){
        refreshTokenService.deleteRefreshToken(refreshTokenDto.getRefreshToken());
        return new ResponseEntity(HttpStatus.OK);
    }
    /*public ResponseEntity logout(@RequestHeader("Authorization") String token){
        //token repository 에서 refresh token 에 해당하는 값을 삭제한다.
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }*/


    /**
     *
     * 1.전달받은 유저의 아이디로 유저가 존재하는지 확인한다
     * 2.RefreshToken이 유효한지 체크한다
     * 3.AccessToken을 발급하여 기존 RefreshToken과 함께 응답한다.
     */
    @PostMapping("/refreshToken")
    @Operation(summary = "JWT 재발급" , description = "JWT 토큰을 재발급합니다.")
    public ResponseEntity requestRefresh(@RequestBody RefreshTokenDto refreshTokenDto){
        System.out.println("refreshTokenDto ==" + refreshTokenDto);
        //post 의 body로 refreshTokenDto를 받는다.
        //이를 DB에서 조회를 하자.
        RefreshToken refreshToken = refreshTokenService.findRefreshToken(refreshTokenDto.getRefreshToken()).orElseThrow(()-> new IllegalArgumentException("Refresh token not found"));
        //orElseThrow 메서드 객체가 비었을떄 예외를 반환
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getValue());
        //jwt builder을 사용해서 리프래시토큰에서 claims 값을 추출하자
        System.out.println("claims ==" + claims);
        Long memberId = Long.valueOf((Integer)claims.get("userId"));
        //추출한 clmais객체에서 memberId를 찾아오자

        Member member = memberService.getMember(memberId).orElseThrow(()-> new IllegalArgumentException("Member Not Found"));
        //찾은 memberId값을 DB member에 조회해 있는지 확인하자

        List roles = (List) claims.get("roles");
        String email = claims.getSubject();
        //memberEntitiy가 유효하다면 값을 꺼내와서 새로운 accessToken을 만들어주자
        String accessToken = jwtTokenizer.createAccessToken(memberId , email , member.getName(), roles);

        MemberLoginResponseDto loginResponse = MemberLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenDto.getRefreshToken())
                .memberId(member.getMemberId())
                .nickname(member.getName())
                .build();
        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/info")
    @Operation(summary = "사용자 정보" , description = "사용자 정보를 조회합니다.")
    public ResponseEntity userinfo(@IfLogin LoginUserDto loginUserDto){
        Member member = memberService.findByEmail(loginUserDto.getEmail());
        List<String> roles = member.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        log.info("roles = {}" + roles);
        return new ResponseEntity(member, HttpStatus.OK);
    }


    @PostMapping("/addRole")
    @Operation(summary = "관리자 권한부여(재 로그인 필요) " , description = "기존회원에 관리자 권한을 합니다.")
    public ResponseEntity addRoleAdmin (@RequestBody RefreshTokenDto refreshTokenDto){

        //member 객체에 받아온 값을 넣어준다.
        RefreshToken refreshToken = refreshTokenService.findRefreshToken(refreshTokenDto.getRefreshToken()).orElseThrow(()-> new IllegalArgumentException("Refresh token not found"));
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getValue());
        Long memberId = Long.valueOf((Integer)claims.get("userId"));

        Member member = memberService.getMember(memberId).orElseThrow(()-> new IllegalArgumentException("Member Not Found"));
     
        Member saveMember = memberService.addRoleMember(member);

        //API 반환값으로 사용할 DTO
        MemberSignupResponseDto memberSignupResponse = new MemberSignupResponseDto();
        memberSignupResponse.setMemberId(saveMember.getMemberId());
        memberSignupResponse.setName(saveMember.getName());
        memberSignupResponse.setRegdate(saveMember.getRegdate());
        memberSignupResponse.setEmail(saveMember.getEmail());

        //회원가입
        return new ResponseEntity(memberSignupResponse , HttpStatus.OK);

    }
}
