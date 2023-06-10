# geipV1
<p align="center"><img src="https://github.com/lay423/geipV1/assets/41409287/eef3a1fd-2639-4ca1-b8b8-c98c417407da"></p>
![7](https://github.com/lay423/geipV1/assets/41409287/54aecf06-119d-4a2b-80dc-086dab190e60)
![제목 없음](https://github.com/lay423/geipV1/assets/41409287/a453864b-6b0b-4018-a541-94501d96d6b7)

## 프로젝트 소개
리그오브레전드 전적검색, 내전 팀 추천 웹 애플리케이션

### 팀원 소개
- 팀장 : 황준하
- 개발 : 김준영
- 개발 : 김대근

## 프로젝트 기술스택
- Backend tech stack
    - Java 11, Spring boot 2.7.3, MySQL, Spring JPA, Gradle 7.6
- Front tech stack
    - HTML, CSS, Java Script, Thymeleaf
- Devops stack
    - AWS EC2, Docker

## ERD
![075e8ffd8cf61a0f](https://github.com/lay423/geipV1/assets/41409287/a7a1d188-9433-4f04-a990-53d299968f81)



### 11.12
- 내전 전적 기능 구현 완료
  1. TeamBuilding.html에서 저장된 정보 DB에 저장
  2. table/html에서 승패 결정 안된 목록 보이기
  3. 승패 결정 후 정보 업데이트

### 11.08
- [Update] 매치검색시 리그정보받아오기 기능 추가
  1. GeipControllerV1
  2. SearchService
  3. search.html
  4. TeamBuildingRiotApiDTO 필드추가

### 11.01
- 내전 전적 기능 구현전 연결확인
  1. 완벽한 기능 구현은 진행중
  2. 그전에 다른 환경에서 실행되는지 확인용도

### 10.30
- DB에 없는 유저 검색시 안되는거 오류 해결, Search, Key 위치변경
  1. SearchService
  2. RiotApiService
  3. MatchDao
  4. GeipControllerV1


### 10.29
- 새로고침 누르면 바로 가능하게 수정
  1. search.html
- 검색했는데 DB에 없으면 바로 Riot에서 받아오게 수정
  1. GeipControllerV1
- 새로고침 했을때 DB에 Update 안되는 문제 해결 
  1. 기존의 ConnectionMaker 삭제
  2. MatchDao jdbcTemplate 적용
- 메인(searchBar)에 테스트 이미지 적용

### 10.26
- 내전 팀 빌딩 + 추천 팀 빌딩 수정
  1. 내전 이름 추가하여 내전 이름 + 플레이어 API로 post 전송

### 10.19
- MatchDB 연동 -> 검색, Api -> 전적 새로고침
  1. Match 다시 domain으로
  2. MatchDao 에서 DB 관련 로직처리
  3. 기존의 API에서 데이터 받아오는건 새로고침 할때만 실행

### 10.16
- HTML 수정
  1. sidebar 필요없는 기능 삭제 + 이름 변경
  2. 각 sidebar 누르면 연결되도록
  3. index는 구현아직 안됨(어떤 느낌으로 만들지 고민됨)
  4. 팀추천, 팀빌딩 페이지는 아직 커밋 안함(충돌날까봐 html수정해주면 그때 올릴계획)
- domain[Summoner, Match] -> dto로 변경, Summoner는 삭제

### 10.10
- 내전 팀 기능 및 추천 기능 수정

### 10.09
- html 파일 삭제, 수정
1. index, main 삭제
2. search 수정


- 내전 팀 기능 구현 및 추천 기능 구현
- URL로 연결 필요
- 매치 불러오기 최적화 필요
- 웹 디자인 수정 필요

### 10.08
- RequestParam으로 검색형식 변경, domain 위치 롤백
  - domain 이름 변경하면 실행 자체가 안됨
  - PathVariable로 하면 로직이 두번도는 문제발생 => RequestParam으로 변경
