package com.example.demo.domain.food.entity;

import com.example.demo.domain.member.entity.MemberFoodPreference;
import com.example.demo.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "food_category")
public class FoodCategory extends BaseTimeEntity {

    // 음식 카테고리 테이블의 기본 키입니다.
    @Id
    @Column(name = "food_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 음식 카테고리 이름입니다. 중복을 허용하지 않습니다.
    @Column(nullable = false, unique = true, length = 20)
    private String name;

    // 이 카테고리를 선호하는 회원 연결 목록입니다.
    @OneToMany(mappedBy = "foodCategory")
    private List<MemberFoodPreference> memberFoodPreferences = new ArrayList<>();
}
