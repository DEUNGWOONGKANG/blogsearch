# blogsearch rest api service

## 1. 블로그 검색하기
GET /search
* 키워드를 통해 블로그를 검색할 수 있어야 한다.<br>
* 검색 결과에서 Sorting(정확도순, 최신순) 기능을 지원<br>
* 검색 결과는 Pagination 형태로 제공<br>
* 검색 소스는 카카오 API 사용 https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog <br>
* 카카오 블로그 검색 API에 장애가 발생한 경우, 네이버 블로그 검색 API를 통해 데이터 제공 https://developers.naver.com/docs/serviceapi/search/blog/blog.md <br>
* 추후 카카오 API 이외에 새로운 검색 소스가 추가될 수 있음을 고려<br>
### parameter
* required String keyword (키워드 검색)
* required String apiUrl (api 호출할 사이트 예)kakao, naver 추후 새로운 검색 소스 추가 고려)
* String sort 정렬 방식은 각각의 API 호출하는 사이트의 방식을 따름 <br>
* 예) kakao => accuracy(정확도순), recency(최신순) / naver => sim(정확도순), date(최신순) (미입력시 자동 정확도순 정렬)
### 호출예시
* http://localhost:8080/search?keyword=카카오&apiUrl=kakao&page=1&size=10&sort=accuracy
* http://localhost:8080/search?keyword=네이버&apiUrl=naver&page=1&size=10&sort=sim
* 카카오 API 호출시 500 INTERNAL_SERVER_ERROR 가 떨어지는 경우 NAVER API를 재호출 하여 결과값을 사용자에게 전달
* 검색시 결과값이 정상적으로 조회가 되면 해당 키워드 검색 횟수 count 1 증가 (해당 메서드 synchronized 적용하여 동시성 이슈 처리)
***
## 2. 인기 검색어 목록
GET /rank
* 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드 및 검색 횟수 제공
### parameter
### 호출예시
* http://localhost:8080/rank

jar dowun load url <br>
https://drive.google.com/file/d/1YgoeZQP49W36g2BiVg51t3H0fePdA33V/view?usp=share_link
