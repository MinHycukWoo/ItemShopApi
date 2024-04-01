package com.example.itemShopApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity //Database Table과 맵핑하는 객체
@Table(name = "member") //Database 테이블 이름 user3와 User라는 개체가 맵핑
@NoArgsConstructor //기본생성자가 필요하다
@Setter
@Getter
public class Member {
    @Id //이 필드가 Table의 PK
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //memberId는 자동으로 생성되도록한다 1 2 3 4~
    private Long memberId;

    @Column(length = 255 ,unique = true) //유일한값
    private String email;

    @Column(length = 50)
    private String name;

    @JsonIgnore//암호화!
    @Column(length = 500)
    private String password;


    @CreationTimestamp// 이 컬럼이 생성될떄 자동으로 현재시간 넣어줌 
    private LocalDateTime regdate;
    /*
        @Column(nullable = false)
        private Integer birthYear;

        @Column(nullable = false)
        private Integer birthMonth;

        @Column(nullable = false)
        private Integer birthDay;

        @Column(length = 10 , nullable = false)
        private String gender;
    */
    //@Column 을 주지 않아서 컬럼으로는 존재하지않는다.
    @ManyToMany //다대다 설정
    @JoinTable(name = "member_role", //만들어진 관계테이블을 생성한다.
            //따로 엔티티 를 만들지 않아도 이것만으로도 관계테이블을 만들어준다.
            joinColumns = @JoinColumn(name = "member_id"), //member가 member_role을 참조하기위한 FK 역활을한다
            inverseJoinColumns = @JoinColumn(name = "role_id") //관계테이블 role 쪽을 참조하기위한 외래키를 지정
    )
    private Set<Role> roles = new HashSet<>();
    /**
     * 그래서 테이블이 자동으로 만들어지게된다.
    roles에 값이 추가되면 @JoinTable 이 member 와 role 와 관계를 정리하기위해서
    관계테이블 member_role에 값을 추가한다.
    @JoinColumn(name = "member_id") 는 해당 엔티티의 member_id를 가져오고
    inverseJoinColumns = @JoinColumn(name = "role_id") 이거는 상대테이블에 그 값이 있는지 참조하여 FK 로 사용한다.
    상태 테이블 지정은 private Set<Role> roles = new HashSet<>(); 여기서
    Set<Role> 부분이다
    */

    /*
    @Override
    public String toString() {
        return "User{" +
                "memberId=" + memberId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", regdate=" + regdate +
                ", birthYear=" + birthYear +
                ", birthMonth=" + birthMonth +
                ", birthDay=" + birthDay +
                ", gender='" + gender + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", regdate=" + regdate +
                ", roles=" + roles +
                '}';
    }

    public void addRole(Role role){
        roles.add(role);
    }
}
