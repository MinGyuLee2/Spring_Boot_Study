package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원에게 연결된 소셜 계정을 저장하고 조회하는 Repository입니다.
 */
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
}
