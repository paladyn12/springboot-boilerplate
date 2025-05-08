# Spring Boot OAuth2 Template Repository

Spring Boot 기반의 OAuth2 인증과 JWT 토큰 기반 인증 시스템을 포함한 백엔드 템플릿<br>
빠른 백엔드 서비스 개발을 위한 기반 코드

## ✅ 주요 기능

- **OAuth2 카카오 로그인**
    - `/oauth2/authorization/kakao` 엔드포인트로 카카오 로그인
    - 로그인 후 JWT 토큰 발급
    - `/users/info` 엔드포인트를 통해 사용자 정보 확인 가능

- **JWT 인증 및 인가**
    - 로그인 시 JWT Access Token 발급
    - 인증된 사용자만 접근 가능한 API 구현
    - 사용자 권한 기반 접근 제어 (`USER`, `ADMIN` 등)

- **전역 예외 처리**
    - 존재하지 않는 URL 요청 시 404 처리
    - 인증/인가 실패 시 커스터마이징된 에러 메시지 응답

- **Swagger API 문서**
    - `/swagger-ui/index.html`에서 API 문서 확인 가능
    - JWT 토큰 인증 테스트 가능

- **사용자 권한 관리**
    - `/admin` 경로는 `ADMIN` 권한이 있는 사용자만 접근 가능

- **Docker 지원**
    - `Dockerfile`, `docker-compose.yml` 포함
    - 로컬에서 손쉽게 컨테이너로 실행 가능

- **CI/CD with GitHub Actions**
    - push 시 자동으로 테스트, 빌드, 배포
    - `.github/workflows` 디렉토리 확인

---

## 🛠️ 기술 스택

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring OAuth2 Client
- JWT (jjwt)
- Swagger (springdoc-openapi)
- Docker & Docker Compose
- GitHub Actions

---
