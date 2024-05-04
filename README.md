# SSAPING MALL Backend

SSAPING MALL은 "당근마켓"과 같은 중고 거래 플랫폼으로, 사용자들이 자유롭게 상품을 거래할 수 있는 오픈 마켓 플랫폼입니다. 사용자 인증(`JWT`, `OAuth`), 커뮤니티 포럼 시스템, 상품 거래 시스템 등 다양한 기능을 지원합니다. 백엔드는 `Java`, `Spring Boot`, `JPA`를 사용하여 구축되었으며, `Docker`를 사용하여 컨테이너화되고, `MySQL`을 데이터베이스로 사용하며, `GitHub`을 통해 버전 관리됩니다.

## 주요 기능

- 사용자 인증(`JWT`, `OAuth`)
- 커뮤니티 포럼 시스템
- 상품 거래 시스템
- 관리자를 위한 사용자 관리

## 시작하기

프로젝트를 로컬 환경에서 실행하기 위한 사전 요구 사항은 Java 17 이상, Spring Boot 3 이상, Docker, MySQL입니다. 

### 설치 및 실행 절차

1. `GitHub`에서 프로젝트 복제:
   ```
   git clone https://github.com/SSapingMall/backend.git
   ```
2. `Docker`를 사용하여 MySQL 데이터베이스 설정:
   ```
   docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag
   ```
3. `application.yml` 파일에서 데이터베이스 연결 설정 업데이트
4. `Spring Boot` 애플리케이션 실행:
   ```
   ./mvnw spring-boot:run
   ```

## 프로젝트 구조

- `src/main/java` - 자바 소스 코드
- `src/main/resources` - 애플리케이션 설정 파일 및 리소스
- `src/test` - 테스트 코드

## 기여하기

SSAPING MALL 프로젝트에 기여하고 싶으신 경우, 이슈 생성 또는 기존 이슈에 댓글을 달아 참여할 수 있습니다. 프로젝트를 포크한 다음 변경 사항을 만들고, 풀 리퀘스트를 생성하면 코드 리뷰 과정을 거쳐 프로젝트에 병합됩니다.

## 라이선스

이 프로젝트는 MIT 라이선스에 따라 배포됩니다.

자세한 정보는 해당 섹션을 참조하십시오.
