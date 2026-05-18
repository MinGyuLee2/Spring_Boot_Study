insert into region (region_id, name, created_at, updated_at)
values (1, '안암동', current_timestamp, current_timestamp);

insert into food_category (food_category_id, name, created_at, updated_at)
values
    (1, '한식', current_timestamp, current_timestamp),
    (2, '중식', current_timestamp, current_timestamp),
    (3, '카페', current_timestamp, current_timestamp);

insert into member (
    member_id,
    nickname,
    email,
    password,
    gender,
    birth_date,
    address,
    total_point,
    status,
    deleted_at,
    created_at,
    updated_at
)
values (
    1,
    'nickname012',
    'dlatplf@naver.com',
    '$2y$10$GfhQazQqEEww21FQKcOwrOzI9R5NGQo2b9lRIWOrCwUfgMLxGJGzG',
    'NONE',
    '2000-01-01',
    '서울 성북구 안암동',
    2500,
    'ACTIVE',
    null,
    current_timestamp,
    current_timestamp
);

insert into member_food_preference (
    member_food_preference_id,
    member_id,
    food_category_id,
    created_at,
    updated_at
)
values
    (1, 1, 1, current_timestamp, current_timestamp),
    (2, 1, 2, current_timestamp, current_timestamp);

insert into store (
    store_id,
    region_id,
    name,
    address,
    category,
    status,
    created_at,
    updated_at
)
values
    (1, 1, '반이학생마라탕', '서울 성북구 안암로 1', '중식당', 'ACTIVE', current_timestamp, current_timestamp),
    (2, 1, '안암분식', '서울 성북구 안암로 2', '한식', 'ACTIVE', current_timestamp, current_timestamp);

insert into mission (
    mission_id,
    store_id,
    title,
    description,
    reward_point,
    status,
    created_at,
    updated_at
)
values
    (1, 1, '10,000원 이상의 식사시 사진 리뷰', '10,000원 이상의 식사를 하세요!', 500, 'ACTIVE', current_timestamp, current_timestamp),
    (2, 1, '12,000원 이상의 식사시 리뷰', '12,000원 이상의 식사를 하세요!', 500, 'ACTIVE', current_timestamp, current_timestamp),
    (3, 2, '8,000원 이상의 식사시 리뷰', '8,000원 이상의 식사를 하세요!', 300, 'ACTIVE', current_timestamp, current_timestamp);

insert into member_mission (
    member_mission_id,
    member_id,
    mission_id,
    status,
    started_at,
    completed_at,
    created_at,
    updated_at
)
values
    (101, 1, 1, 'CHALLENGING', '2026-04-30 09:00:00', null, current_timestamp, current_timestamp),
    (102, 1, 2, 'COMPLETED', '2026-04-29 12:00:00', '2026-04-29 13:00:00', current_timestamp, current_timestamp);

insert into review (
    review_id,
    member_id,
    store_id,
    rating,
    content,
    created_at,
    updated_at
)
values (
    1,
    1,
    1,
    5,
    '음식이 맛있고 포인트도 얻고 맛있는 맛집도 알게 된 것 같아 너무 행복한 식사였습니다.',
    current_timestamp,
    current_timestamp
);

insert into review_photo (review_photo_id, review_id, photo_url)
values
    (1, 1, 'https://example.com/reviews/1-1.jpg'),
    (2, 1, 'https://example.com/reviews/1-2.jpg');
