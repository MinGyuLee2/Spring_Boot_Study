package com.example.demo.domain.review.repository;

import com.example.demo.domain.review.entity.Review;
import java.util.List;
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

    /**
     * 내가 작성한 리뷰를 ID 내림차순 커서로 조회합니다.
     */
    @EntityGraph(attributePaths = {"store"})
    @Query("""
            select review
            from Review review
            join review.member member
            join review.store store
            where member.id = :memberId
              and (:cursorId is null or review.id < :cursorId)
            order by review.id desc
            """)
    List<Review> findMyReviewsByIdDesc(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    /**
     * 내가 작성한 리뷰를 ID 오름차순 커서로 조회합니다.
     */
    @EntityGraph(attributePaths = {"store"})
    @Query("""
            select review
            from Review review
            join review.member member
            join review.store store
            where member.id = :memberId
              and (:cursorId is null or review.id > :cursorId)
            order by review.id asc
            """)
    List<Review> findMyReviewsByIdAsc(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    /**
     * 내가 작성한 리뷰를 별점 내림차순, ID 내림차순 커서로 조회합니다.
     */
    @EntityGraph(attributePaths = {"store"})
    @Query("""
            select review
            from Review review
            join review.member member
            join review.store store
            where member.id = :memberId
              and (
                    :cursorRating is null
                    or review.rating < :cursorRating
                    or (review.rating = :cursorRating and review.id < :cursorId)
              )
            order by review.rating desc, review.id desc
            """)
    List<Review> findMyReviewsByRatingDesc(
            @Param("memberId") Long memberId,
            @Param("cursorRating") Integer cursorRating,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    /**
     * 내가 작성한 리뷰를 별점 오름차순, ID 오름차순 커서로 조회합니다.
     */
    @EntityGraph(attributePaths = {"store"})
    @Query("""
            select review
            from Review review
            join review.member member
            join review.store store
            where member.id = :memberId
              and (
                    :cursorRating is null
                    or review.rating > :cursorRating
                    or (review.rating = :cursorRating and review.id > :cursorId)
              )
            order by review.rating asc, review.id asc
            """)
    List<Review> findMyReviewsByRatingAsc(
            @Param("memberId") Long memberId,
            @Param("cursorRating") Integer cursorRating,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
