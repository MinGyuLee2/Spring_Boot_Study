# Spring Boot Study

Spring Boot 학습을 위해 만든 음식점 리뷰 서비스 API 프로젝트입니다. 회원 인증, 음식 카테고리, 지역, 가게, 미션, 리뷰 도메인을 나누어 구현하며 Spring Boot 기반 백엔드 구조와 REST API 설계를 연습합니다.

## 주요 기능

- 회원가입, 로그인, JWT 기반 인증
- 카카오 OAuth 로그인 연동
- 회원 마이페이지, 포인트, 완료 미션 수 조회
- 음식 카테고리 및 지역 조회
- 가게 리뷰 목록 조회 및 리뷰 작성
- 내가 작성한 리뷰 커서 기반 조회
- 진행 중인 미션 조회, 미션 도전, 미션 완료 처리
- Swagger UI를 통한 API 문서 확인

## 기술 스택

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Bean Validation
- JWT
- H2 Database
- MySQL
- Gradle
- Docker

## 프로젝트 구조

```text
src
├── main
│   ├── java/com/example/demo
│   │   ├── domain
│   │   │   ├── food
│   │   │   ├── member
│   │   │   ├── mission
│   │   │   ├── region
│   │   │   ├── review
│   │   │   └── store
│   │   └── global
│   │       ├── apiPayload
│   │       ├── auth
│   │       ├── config
│   │       ├── entity
│   │       └── exception
│   └── resources
└── test
```

## API 요약

| Domain | Method | Endpoint | Description |
| --- | --- | --- | --- |
| Auth | POST | `/api/v1/auth/login` | 로그인 |
| Auth | POST | `/api/v1/auth/oauth/kakao` | 카카오 로그인 |
| Member | POST | `/api/v1/members/signup` | 회원가입 |
| Member | GET | `/api/v1/members/me` | 내 정보 조회 |
| Member | GET | `/api/v1/members/me/points` | 내 포인트 조회 |
| Member | GET | `/api/v1/members/me/missions/completed/count` | 완료 미션 수 조회 |
| Food | GET | `/api/v1/food-categories/{foodCategoryId}` | 음식 카테고리 조회 |
| Region | GET | `/api/v1/regions/{regionId}` | 지역 조회 |
| Store | GET | `/api/v1/stores/{storeId}/reviews` | 가게 리뷰 목록 조회 |
| Store | POST | `/api/v1/stores/{storeId}/reviews` | 가게 리뷰 작성 |
| Review | POST | `/api/v1/reviews/me` | 내가 작성한 리뷰 목록 조회 |
| Review | GET | `/api/v1/reviews/{reviewId}` | 리뷰 상세 조회 |
| Mission | GET | `/api/v1/missions/available` | 도전 가능한 미션 조회 |
| Member Mission | GET | `/api/v1/member-missions` | 진행 중인 미션 조회 |
| Member Mission | POST | `/api/v1/member-missions/ongoing` | 미션 도전 |
| Member Mission | PATCH | `/api/v1/member-missions/{memberMissionId}/complete` | 미션 완료 |

## 로컬 실행

```bash
./gradlew bootRun
```

기본 설정은 H2 인메모리 DB를 사용합니다.

- API 서버: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- H2 Console: `http://localhost:8080/h2-console`

## 테스트 및 빌드

```bash
./gradlew test
./gradlew clean bootJar
```

## 환경변수

로컬 개발에서는 기본값으로 실행할 수 있습니다. 카카오 로그인을 테스트하거나 운영 환경에 배포할 때는 `.env.example`을 참고해 환경변수를 설정합니다.

```bash
KAKAO_CLIENT_ID=your_kakao_rest_api_key
KAKAO_CLIENT_SECRET=your_kakao_client_secret
KAKAO_REDIRECT_URI=http://localhost:8082/oauth/kakao/callback
JWT_SECRET=replace_with_a_long_random_secret_at_least_32_bytes
```

## 프로덕션 설정

운영 환경에서는 `SPRING_PROFILES_ACTIVE=prod`를 사용합니다.

```bash
PORT=8080
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:mysql://your-host:3306/your-database?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
SPRING_DATASOURCE_USERNAME=your_database_user
SPRING_DATASOURCE_PASSWORD=your_database_password
SPRING_JPA_HIBERNATE_DDL_AUTO=update
JWT_SECRET=replace_with_a_long_random_secret_at_least_32_bytes
JWT_ACCESS_TOKEN_VALIDITY_IN_SECONDS=3600
KAKAO_CLIENT_ID=your_kakao_rest_api_key
KAKAO_CLIENT_SECRET=your_kakao_client_secret
KAKAO_REDIRECT_URI=https://your-domain.com/oauth/kakao/callback
```

## Docker 실행

```bash
docker build -t spring-boot-study .
docker run --env-file .env -p 8080:8080 spring-boot-study
```

## EC2 배포

EC2에 Docker와 Docker Compose가 설치되어 있다면 아래 순서로 배포할 수 있습니다.

```bash
git clone https://github.com/MinGyuLee2/Spring_Boot_Study.git
cd Spring_Boot_Study
cp .env.production.example .env
vi .env
docker compose up -d --build
```

배포 후 컨테이너 상태와 로그를 확인합니다.

```bash
docker compose ps
docker compose logs -f app
```

애플리케이션은 기본적으로 `http://EC2_PUBLIC_IP:8080`에서 실행됩니다. EC2 보안 그룹에서 인바운드 TCP `8080` 포트를 열어야 외부 접속이 가능합니다.

## 학습 포인트

- 도메인별 Controller, Service, Repository 계층 분리
- 공통 API 응답 포맷과 전역 예외 처리
- Spring Security 필터 기반 JWT 인증
- JPA 엔티티 연관관계와 커서 기반 페이지네이션
- 개발 환경과 운영 환경 설정 분리
