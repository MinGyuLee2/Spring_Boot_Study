package com.example.demo.domain.review.repository;

import com.example.demo.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Review 엔티티의 DB 접근을 담당하는 Repository입니다.
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 마이페이지 화면에서 회원이 작성한 리뷰 개수를 표시할 때 사용합니다.
    long countByMember_Id(Long memberId);

    /**
     * 특정 가게에 작성된 리뷰 목록을 페이징 조회합니다.
     */
    @EntityGraph(attributePaths = {"member"})
    @Query(
            value = """
                    select review
                    from Review review
                    join review.store store
                    join review.member member
                    where store.id = :storeId
                    """,
            countQuery = """
                    select count(review)
                    from Review review
                    join review.store store
                    where store.id = :storeId
                    """
    )
    Page<Review> findReviewsByStoreId(@Param("storeId") Long storeId, Pageable pageable);
}
