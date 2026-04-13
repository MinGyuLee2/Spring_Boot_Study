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

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberGender gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(name = "total_point", nullable = false)
    private Integer totalPoint = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberStatus status;

    @OneToMany(mappedBy = "member")
    private List<SocialAccount> socialAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberFoodPreference> foodPreferences = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberMission> memberMissions = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

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
