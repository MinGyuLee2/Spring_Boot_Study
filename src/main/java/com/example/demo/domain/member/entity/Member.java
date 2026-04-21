package com.example.demo.domain.member.entity;

import com.example.demo.domain.member.enums.MemberGender;
import com.example.demo.domain.member.enums.MemberStatus;
import com.example.demo.domain.mission.entity.MemberMission;
import com.example.demo.domain.review.entity.Review;
import com.example.demo.global.entity.SoftDeleteBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends SoftDeleteBaseEntity {

    // 회원 테이블의 기본 키입니다. IDENTITY는 DB의 auto increment 전략을 사용합니다.
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 서비스에서 노출되는 회원 닉네임입니다.
    @Column(nullable = false, length = 20)
    private String nickname;

    // 로그인 또는 회원 식별에 사용할 이메일입니다.
    @Column(nullable = false, length = 100)
    private String email;

    // 실제 서비스에서는 반드시 암호화된 비밀번호를 저장해야 합니다.
    @Column(nullable = false, length = 255)
    private String password;

    // enum은 ORDINAL 대신 STRING으로 저장해야 순서 변경에 안전합니다.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberGender gender;

    // 생년월일입니다. 가입 요청에서 과거 날짜인지 검증합니다.
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    // 회원 주소입니다.
    @Column(nullable = false, length = 100)
    private String address;

    // 회원이 보유한 총 포인트입니다. 신규 회원은 0으로 시작합니다.
    @Column(name = "total_point", nullable = false)
    private Integer totalPoint = 0;

    // 회원 계정 상태입니다. 탈퇴나 비활성화 상태를 표현할 수 있습니다.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberStatus status;

    // 회원은 여러 소셜 계정을 연결할 수 있습니다.
    @OneToMany(mappedBy = "member")
    private List<SocialAccount> socialAccounts = new ArrayList<>();

    // 회원이 선호하는 음식 카테고리 목록입니다.
    @OneToMany(mappedBy = "member")
    private List<MemberFoodPreference> foodPreferences = new ArrayList<>();

    // 회원이 도전 중이거나 완료한 미션 목록입니다.
    @OneToMany(mappedBy = "member")
    private List<MemberMission> memberMissions = new ArrayList<>();

    // 회원이 작성한 리뷰 목록입니다.
    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    /**
     * 회원 생성에 필요한 기본값을 한곳에서 지정하는 정적 팩토리 메서드입니다.
     *
     * <p>생성자를 직접 열어두는 대신 create 메서드를 사용하면
     * totalPoint, status 같은 초기값을 빠뜨리지 않게 관리할 수 있습니다.</p>
     */
    public static Member create(
            String email,
            String password,
            String nickname,
            MemberGender gender,
            LocalDate birthDate,
            String address
    ) {
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.nickname = nickname;
        member.gender = gender;
        member.birthDate = birthDate;
        member.address = address;
        member.totalPoint = 0;
        member.status = MemberStatus.ACTIVE;
        return member;
    }
}
