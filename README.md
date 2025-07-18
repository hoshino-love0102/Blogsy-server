# Blogsy Server

Spring Boot 기반의 블로그 백엔드 서버입니다.  
회원가입, 인증, 게시글 CRUD, AI 추천 기능 등을 제공합니다.

---

## 1. 공통

- **JWT 발급 및 인증**
    - 로그인 시 JWT 발급
    - 이후 API 요청 시 토큰 인증 수행
- **CORS 설정**
    - 허용 origin 관리
- **글 임베딩 벡터 관리**
    - OpenAI Embedding API 사용
- **전역 예외 처리**
    - GlobalExceptionHandler 구현

---

## 2. 회원 관리

### 회원가입

- 엔드포인트: `POST /api/auth/signup`
- 이메일 중복 검사
- 닉네임 중복 검사
- 비밀번호 암호화 (BCrypt)
- 회원정보 저장
- 요청 필드 (JSON):
    - username (String)
    - email (String)
    - password (String)
    - nickname (String)
    - birth (yyyy-MM-dd)
    - phone (String)
    - profileImageUrl (String)

### 로그인

- 엔드포인트: `POST /api/auth/login`
- 이메일 또는 username 중 하나로 로그인 가능
- 비밀번호 검증 (BCrypt)
- JWT 발급
- 요청 필드 (JSON):
    - emailOrUsername (String)
    - password (String)
- 응답 필드 (JSON):
    - token (JWT String)
    - username
    - email
    - nickname
    - birth
    - phone
    - profileImageUrl

---

### 마이페이지

- (구현 필요) 회원 정보 조회/수정 기능

---

### 비밀번호 재설정

- (구현 필요) 이메일 인증 및 비밀번호 변경 로직

---

## 3. 게시글 관리 (미구현)

- **글 작성**
    - 제목, 내용, 임베딩 벡터 저장
- **글 수정**
    - 작성자 인증 후 수정 가능
- **글 삭제**
    - 작성자 인증 후 삭제 가능
- **글 목록 조회**
    - 전체 글 목록
- **글 상세 조회**
    - 단일 글 상세 보기

---

## 4. AI 기능

현재 AI 관련 기능은 **임베딩 기반 추천 서비스**가 동작 중입니다.

- **AI 피드 추천**
    - 사용자의 임베딩 벡터와 각 글의 벡터 간 코사인 유사도를 계산
    - 유사도 순으로 글을 정렬 후 상위 10개의 글 추천
    - 임시로 `"게임 추천"` 텍스트를 사용자 임베딩 생성에 사용 중
    - 추후 사용자별 프로필 데이터로 변경 예정

---

## 기술 스택

- Spring Boot
- Spring Security
- JWT
- MariaDB
- JPA (Hibernate)
- OpenAI API (Embedding)
- Lombok

---

## TODO

- 마이페이지 API 구현
- 비밀번호 재설정 로직
- 관리자 기능 (회원/글/통계)
- AI 생성 기능 (제목 추천, 요약, 이미지 생성 등)
- 댓글 기능
- 좋아요 기능
- 해시태그 관리
- 검색 기능