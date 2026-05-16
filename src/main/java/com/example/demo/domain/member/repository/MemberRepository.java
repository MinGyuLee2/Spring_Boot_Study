package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Member 엔티티의 DB 접근을 담당하는 Repository입니다.
 *
 * <p>JpaRepository를 상속하면 기본 CRUD 메서드가 자동으로 제공됩니다.</p>
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}
