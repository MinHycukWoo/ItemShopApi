package com.example.itemShopApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder //이게 있으면 매개변수 체이닝 을 사용해 빌드할수 있게된다.
@NoArgsConstructor //매개변수가 없는 기본생성자를 자동으로 생성합니다
@AllArgsConstructor //모든 매개변수를 가지는 생성자를 자동으로 생성합니다.
public class MemberSignupDto {

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "이메일 형식을 맞춰야합니다")
    @Schema(description = "사용자 이메일" , nullable = false , example = "test@gmail.com")
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{7,16}$",
            message = "비밀번호는 영문+숫자+특수문자를 포함한 8~20자여야 합니다")
    @Schema(description = "사용자 비밀번호" , nullable = false , example = "qwer1234!@#")
    private String password;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z가-힣\\\\s]{2,15}",
            message = "이름은 영문자, 한글, 공백포함 2글자부터 15글자까지 가능합니다.")
    @Schema(description = "사용자 이름" , nullable = false , example = "홍길동")
    private String name;

    /*
    @NotNull
    @Pattern(regexp = "^\\d{4}$", message = "생년은 4자리 숫자로 입력해야 합니다")
    private String birthYear;

    @NotNull
    @Pattern(regexp = "^(0?[1-9]|1[012])$", message = "생월은 1부터 12까지의 숫자로 입력해야 합니다")
    private String birthMonth;

    @NotNull
    @Pattern(regexp = "^(0?[1-9]|[12][0-9]|3[01])$", message = "생일은 1부터 31까지의 숫자로 입력해야 합니다")
    private String birthDay;

    @NotEmpty
    @Pattern(regexp = "^[MF]{1}$", message = "성별은 'M' 또는 'F'로 입력해야 합니다")
    private String gender;*/
}