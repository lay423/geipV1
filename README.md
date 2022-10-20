# geipV1

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
