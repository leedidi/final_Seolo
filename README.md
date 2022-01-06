# Project : 서로(SEOLO)
<blockquote>
<p dir="auto">서로 - 서울시 1인가구 🏠 정보공유 커뮤니티</p>
</blockquote>

급증하고 있는 1인 가구를 위한 서울시 1인 가구 특화 정보 공유 사이트<br>
1인 가구의 식사, 주거, 안전 등 정보 수집을 위한 체크리스트 작성 및 조회, 공동구매 진행 커뮤니티 개발

Project View : 

<img src="https://github.com/knotted-earlgrey/final_Seolo/blob/master/notice/main.png?raw=true"><br><br>


## Development Environment & APIs
- JAVA 1.8.0
- Oracle 11.2.0
- HTML, Javascript, JSP
- Apache Tomcat 8.5 Server
- 기타 Framework 및 API : jQuery, Ajax, Spring, Bootstrap, mybatis, KakaoMaps API
<br>

## Function
1. (서울 내) 지역 검색 후 해당 지역에 대한 주거 정보 작성 📜
2. (서울 내) 동 단위 지역 평균 점수 정보 제공
3. 주거 정보 체크리스트, 지역 정보 평균치 북마크 추가 및 스티커(해시태그) 추가
4. 회원가입 / 정보수정 / 탈퇴 / 로그인 / 아이디, 비밀번호 찾기
5. 관리자 회원 조회, 공지사항 및 FAQ 작성, 신고내역 처리

```sh
More information is in folder [detail] - Project proposal, Requirement analysis, Storyboard, PPT
```
<br>

## Database Structure 🛠
<img src="https://github.com/knotted-earlgrey/final_Seolo/blob/master/detail/DB%EB%AA%A8%EB%8D%B8%EB%A7%81(%EB%85%BC%EB%A6%AC%EC%84%A4%EA%B3%84).png?raw=true"><br>
```sh
물리설계.sql & 물리설계(PL/SQL).sql for reference
```
<br>

## Screenshot & Explanation 🖥
- 메인화면<br>
<img src="https://github.com/knotted-earlgrey/final_Seolo/blob/master/notice/main2.PNG?raw=true" style="width: 48%"><br>
```sh
<메인화면>
최신 공지사항 3개까지 조회 가능
북마크 횟수 기준으로 인기 체크리스트, 인기 지역정보 측정 후 상위 5개 노출
```
<br>

- 회원가입, 정보 수정<br>
<img src="https://github.com/knotted-earlgrey/final_Seolo/blob/master/notice/createAccount.png?raw=true" style="width: 48%"> <img src="https://github.com/knotted-earlgrey/final_Seolo/blob/master/notice/pwChange.png?raw=true" style="width: 48%"><br>
```sh
<개인정보>
회원 가입 시 약관 동의, 필수 입력항목 및 중복 확인(아이디, 닉네임, 전화번호)
가입 시 입력한 일부 항목(닉네임, 주소, 비밀번호 등)에 대해 정보 수정 가능
아이디 찾기를 통한 아이디 찾기, 비밀번호 찾기를 통한 비밀번호 재설정 가능
탈퇴 시 탈퇴 동의 및 비밀번호 재입력
```
<br>

- 지역정보 조회<br>
<img src="https://github.com/knotted-earlgrey/final_Seolo/blob/master/notice/local.png?raw=true" style="width: 48%"> <img src="https://github.com/knotted-earlgrey/final_Seolo/blob/master/notice/localView.png?raw=true" style="width: 48%"><br>
```sh
<체크리스트 - 지역정보>
서울시의 1인 가구용 주거 정보를 홈페이지에 작성된 개별 체크리스트를 토대로 조회 가능
'동' 단위의 평균치 정보 제공, 지역별로 비교 가능
특정 정보에 대해서(월세, 생활편의시설 점수 평균 등) 선택적으로만 조회 가능
```
<br>

- 체크리스트 조회<br>
<img src="https://github.com/knotted-earlgrey/final_Seolo/blob/master/notice/checkView.png?raw=true" style="width: 48%"> <img src="https://github.com/knotted-earlgrey/final_Seolo/blob/master/notice/checkView2.png?raw=true" style="width: 48%"> <img src="https://github.com/knotted-earlgrey/final_Seolo/blob/master/notice/checkView3.png?raw=true" style="width: 48%"><br>
```sh
<체크리스트 - 체크리스트>
개인이 작성한 개별 체크리스트에 대해 조회 가능
주소 등 특정 정보는 작성한 개인만 조회 가능, 기타 다른 정보는 비로그인 사용자도 조회 가능
로그인한 사용자가 특정 체크리스트에 스티커(해시태그) 부착 시 부착한 스티커 조회 가능
```
<br>


## Contact 📞
email address : cheezoo86@naver.com


