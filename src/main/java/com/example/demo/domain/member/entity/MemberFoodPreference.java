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

    @Id
    @Column(name = "member_food_preference_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(optional = false)
    @JoinColumn(name = "food_category_id", nullable = false)
    private FoodCategory foodCategory;

    public static MemberFoodPreference create(Member member, FoodCategory foodCategory) {
        MemberFoodPreference preference = new MemberFoodPreference();
        preference.member = member;
        preference.foodCategory = foodCategory;
        return preference;
    }
}
