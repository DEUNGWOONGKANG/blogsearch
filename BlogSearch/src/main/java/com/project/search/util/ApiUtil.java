package com.project.search.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.search.dto.ResponseDto;
import com.project.search.util.exception.CustomException;
import com.project.search.util.exception.ErrorCode;

@Component
public class ApiUtil {
	private final Environment env;
	
	@Autowired
    public ApiUtil(Environment env) {
        this.env = env;
    }
	
	//RestTemplate를 이용한 api 호출
	public ResponseDto callGet(String keyword, String apiUrl, Pageable pageable) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			//api 호출 할곳에 따른 헤더 세팅
			HttpEntity<String> header = getHeader(apiUrl);
			//api 호출 할곳에 따른 URI 세팅
	        UriComponentsBuilder builder = getUri(keyword, apiUrl, pageable);
        	
        	ResponseEntity<Map> result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, header, Map.class);
        	//api를 통해 받은 데이터를 responseDto로 변환
    		return new ResponseDto(result.getBody(), apiUrl, pageable);
		} catch (HttpClientErrorException e) {
			//카카오 이며 카카오 서버 문제로 인해 exception 처리가 된 경우 naver api 호출
			if(apiUrl.equals("kakao") && e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				apiUrl = "naver";
				return callGet(keyword, apiUrl, pageable);
			}
			//http status value를 이용하여 exception 처리
			throw new CustomException(ErrorCode.valueOfStatus(e.getStatusCode().value()));
		}
	}
	
	//헤더 세팅
	public HttpEntity<String> getHeader(String apiUrl) {
		HttpHeaders httpHeaders = new HttpHeaders();
		// api를 요청할 url 마다 헤더정보가 다르므로 if문으로 헤더를 세팅해준다.
		if(apiUrl.equals("kakao")) {
        	httpHeaders.set("Authorization", env.getProperty("api.key.kakao"));
        }else if(apiUrl.equals("naver")) {
        	httpHeaders.set("X-Naver-Client-Id", env.getProperty("api.key.naver.id"));
        	httpHeaders.set("X-Naver-Client-Secret", env.getProperty("api.key.naver.secret"));
        }
		return new HttpEntity<>(httpHeaders);
	}
	
	//URI 세팅
	public UriComponentsBuilder getUri(String keyword, String apiUrl, Pageable pageable) {
		String url = env.getProperty("api.url." + apiUrl);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		// api를 요청할 url 마다 param정보가 다르므로 if문으로 param을 세팅해준다.
		if(apiUrl.equals("kakao")) {
			builder = builder.queryParam("query", keyword)
					.queryParam("page", pageable.getPageNumber()+1)
					.queryParam("size", pageable.getPageSize());
		}else if(apiUrl.equals("naver")) {
			try {
				//네이버 API 사용에는 utf8 인코딩 필요
				String encodeKeyword = URLEncoder.encode(keyword, "utf-8");
			
				builder = builder.queryParam("query", encodeKeyword)
						.queryParam("start", pageable.getPageNumber()+1)
						.queryParam("display", pageable.getPageSize());
			} catch (UnsupportedEncodingException e) {
				throw new CustomException(ErrorCode.BAD_REQUEST);
			}
		}
		
		//정렬 값이 있는경우 세팅
		if(pageable.getSort().toList().size() > 0) {
			String sort = pageable.getSort().toList().get(0).getProperty();
			//kakao로 조회하였지만 서버오류로 인해 조회하지 못한 경우 naver로 재조회를 하기때문에 정렬 값을 바꾸어 준다.
			if(apiUrl.equals("naver") && sort.equals("accuracy")) sort = "sim";
			if(apiUrl.equals("naver") && sort.equals("recency")) sort = "date";
			builder.queryParam("sort", sort);
		}
		
		return builder;
	}
	

}
