# 2018 우아한 신입사원 프로젝트
## trello 서비스
---
- 팀은 2-2-1
- 점심시간에는 각자 자유시간을 가지되 필요한 경우 야근을 한다.
- 스크럼 시간에는 하나의 노트북만 열고 이슈를 정리한다.
- 프론드엔드도 js 기능 단위에 대해서는 TDD를 지향한다.
- 프로젝트는 즐겁게 한다. (이거 잘만들었다고 월급이 오르지 않는다)

## 요구사항
### 백엔드
#### 필수 요구사항
- Spring Boot + JPA 기반으로 로컬 개발 환경을 구축한다.
- DB는 MySQL 또는 Maria DB를 사용한다.
- 자동화된 Acceptance Test, Unit Test를 가져야 한다.
- Restful API 설계 원칙에 따라 API를 제공해야 한다.
- Spring Security를 적용해 보안을 강화한다.
- 비밀번호를 암호화해야한다. Spring Security에 암호화 기능 제공한다.
- Github 인증은 OAuth2로 인증해야 한다. Spring Security에 OAuth2 기능 제공한다.
- 사용자가 입력한 데이터에 대한 유효성 체크를 반드시 해야 한다. - java validation 활용한다.
- 쉘 스크립트를 활용해 개발 서버에 배포를 자동화해야 한다.
- Travis와 같은 CI 도구를 적용해 배포를 자동화한다.
#### 선택 요구사항
- XSS(Cross Site Scripting), CSRF(Cross-Site Request Forgeries) 공격으로부터 안전한 코드를 구현한다.
- 무중단 배포가 가능해야 한다.

### 프론드엔드
#### 필수 요구사항
- HTML,CSS 보다는 JavaScript 학습에 집중한다. 기본 HTML,CSS코드는 제공된다.
- 주어진 HTML,CSS 이외 추가적인 기능은 개발해야 한다.
- CSS는 SASS 문법을 사용한다.
- ES6 문법을 기본으로 구현한다.
- ES6 Classes 문법과, Module방식으로 개발한다(export, import)
- Webpack을 빌드 도구로 사용한다.
- transpiling을 한다.
- Ajax통신은 Promise기반으로 한다.
- forEach,Map,Filter,Reduce와 같은 함수형 메서드를 적극 사용한다.
- Vue, React, Angular 를 쓰지 않는다.
- jQuery를 쓰지 않는다.
- 그외 라이브러리 사용은 기본적으로 가능하지만 마스터에게 사전통보한다.
