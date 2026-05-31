# 🍃 Spring_Boot_A
SSUMC 10기 SpringBoot 스터디 A조

## 🚀 배포 준비

### 로컬 실행
```bash
./gradlew bootRun
```

기본 프로필은 H2 인메모리 DB를 사용합니다.

### 프로덕션 환경변수
배포 환경에서는 `SPRING_PROFILES_ACTIVE=prod`를 설정하고 아래 값을 등록합니다.

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

### Docker 빌드 및 실행
```bash
docker build -t spring-boot-a .
docker run --env-file .env -p 8080:8080 spring-boot-a
```

### 개인 GitHub 레포로 옮기기
개인 GitHub에서 빈 저장소를 만든 뒤, 아래처럼 `personal` 원격을 추가해서 현재 `Dominic/main` 브랜치를 `main`으로 올릴 수 있습니다.

```bash
git remote add personal git@github.com:YOUR_GITHUB_ID/YOUR_REPOSITORY.git
git push personal Dominic/main:main
```

## 💻 Member
|하나|엘시|동구|도미닉|민스|
| :---------:|:----------:|:----------:|:----------:|:----------:|
|[송채원](https://github.com/chaerishme)|[이찬형](https://github.com/lclee0520)|[원도경](https://github.com/gangdogang)|[이민규](https://github.com/MinGyuLee2)|[김민성](https://github.com/minseongid)|

## 📁 디렉토리 구조
- src 디렉토리를 실제 작업을 진행하는 Spring Boot의 src와 일치시켜 주세요.
- 미션 및 실습, 주차를 정확히 구분하기 위해 아래의 commit 규칙을 ‼️반드시‼️ 준수하여 commit 후 push 해 주세요.
```bash
├─.github
│  └─PULL_REQUEST_TEMPLATE
│  └─ISSUE_TEMPLATE
├─README.md
└─src
    ├─main
    │
    └─test
    │
    └─...
            
``` 
## 🎨 commit 규칙
- 해당 commit이 미션에 관한 것일 경우: "mission/#해당 주차"

```
Ex) 1주차 미션 수행 후
mission/#01
mission/#01 - 서버 환경설정
```

<br>

- 해당 commit이 실습에 관한 것일 경우: "practice/#해당 주차"
```
Ex) 2주차 실습 수행 후
practice/#02
practice/#02 - ㅇㅇ 어노테이션 구현
```
- 해당 주차에 미션이나 실습이 없는 경우, 있는 주차에만 수행하시면 됩니다.

## 🌳 branch 규칙
```bash
├─main
    ├─angela/main
    │  └─angela/#1
``` 

1. `닉네임/main 브랜치`가 기본 브랜치로 pr 보낼 때 root 브랜치(main 브랜치)가 아닌 닉네임/main 브랜치로 올립니다.
2. 매주 워크북, 실습, 그리고 미션은 각자의 닉네임/main 브랜치를 base 브랜치로 삼아 `닉네임/이슈번호 브랜치`를 생성하여 관련 파일을 업로드합니다.
3. 모든 팀원들의 approve를 받으면, pr을 머지하고 해당 pr을 생성한 브랜치(닉네임/이슈번호 브랜치)는 삭제합니다. approve와 merge는 스터디 진행 중에 이루어집니다.

## 🔖 커밋 컨벤션
| Message  | 설명                                              |
| :------: | :------------------------------------------------ |
|   mission   | 미션 수행                                  |
|   practice   | 실습 수행                             |
|   keyword    | 키워드 정리                                         |
|   workbook   | 워크북 정리                                         |
|  fix   | 버그 수정 |
| docs | 문서 수정                                     |
| comment | 주석 추가 및 변경                                     |
|   test   | 테스트 코드 추가                                       |
|  rename   | 파일 혹은 폴더명 수정                |
|  remove   | 파일 혹은 폴더 삭제                |
|  chore   | 기타 변경사항                |
