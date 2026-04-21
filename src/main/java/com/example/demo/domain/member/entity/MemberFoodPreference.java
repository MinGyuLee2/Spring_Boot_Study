package com.example.demo.domain.member.entity;

import com.example.demo.domain.food.entity.FoodCategory;
import com.example.demo.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_food_preference")
public class MemberFoodPreference extends BaseTimeEntity {

    // 회원 음식 선호 연결 테이블의 기본 키입니다.
    @Id
    @Column(name = "member_food_preference_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 선호 정보를 가진 회원입니다. 여러 선호 정보가 한 회원에 속합니다.
    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 회원이 선호하는 음식 카테고리입니다.
    @ManyToOne(optional = false)
    @JoinColumn(name = "food_category_id", nullable = false)
    private FoodCategory foodCategory;

    /**
     * 회원과 음식 카테고리 사이의 선호 관계를 생성합니다.
     */
    public static MemberFoodPreference create(Member member, FoodCategory foodCategory) {
        MemberFoodPreference preference = new MemberFoodPreference();
        preference.member = member;
        preference.foodCategory = foodCategory;
        return preference;
    }
}
