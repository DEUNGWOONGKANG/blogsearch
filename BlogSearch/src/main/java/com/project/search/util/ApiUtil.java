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
	
	//RestTemplate�� �̿��� api ȣ��
	public ResponseDto callGet(String keyword, String apiUrl, Pageable pageable) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			//api ȣ�� �Ұ��� ���� ��� ����
			HttpEntity<String> header = getHeader(apiUrl);
			//api ȣ�� �Ұ��� ���� URI ����
	        UriComponentsBuilder builder = getUri(keyword, apiUrl, pageable);
        	
        	ResponseEntity<Map> result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, header, Map.class);
        	//api�� ���� ���� �����͸� responseDto�� ��ȯ
    		return new ResponseDto(result.getBody(), apiUrl, pageable);
		} catch (HttpClientErrorException e) {
			//īī�� �̸� īī�� ���� ������ ���� exception ó���� �� ��� naver api ȣ��
			if(apiUrl.equals("kakao") && e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				apiUrl = "naver";
				return callGet(keyword, apiUrl, pageable);
			}
			//http status value�� �̿��Ͽ� exception ó��
			throw new CustomException(ErrorCode.valueOfStatus(e.getStatusCode().value()));
		}
	}
	
	//��� ����
	public HttpEntity<String> getHeader(String apiUrl) {
		HttpHeaders httpHeaders = new HttpHeaders();
		// api�� ��û�� url ���� ��������� �ٸ��Ƿ� if������ ����� �������ش�.
		if(apiUrl.equals("kakao")) {
        	httpHeaders.set("Authorization", env.getProperty("api.key.kakao"));
        }else if(apiUrl.equals("naver")) {
        	httpHeaders.set("X-Naver-Client-Id", env.getProperty("api.key.naver.id"));
        	httpHeaders.set("X-Naver-Client-Secret", env.getProperty("api.key.naver.secret"));
        }
		return new HttpEntity<>(httpHeaders);
	}
	
	//URI ����
	public UriComponentsBuilder getUri(String keyword, String apiUrl, Pageable pageable) {
		String url = env.getProperty("api.url." + apiUrl);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		// api�� ��û�� url ���� param������ �ٸ��Ƿ� if������ param�� �������ش�.
		if(apiUrl.equals("kakao")) {
			builder = builder.queryParam("query", keyword)
					.queryParam("page", pageable.getPageNumber()+1)
					.queryParam("size", pageable.getPageSize());
		}else if(apiUrl.equals("naver")) {
			try {
				//���̹� API ��뿡�� utf8 ���ڵ� �ʿ�
				String encodeKeyword = URLEncoder.encode(keyword, "utf-8");
			
				builder = builder.queryParam("query", encodeKeyword)
						.queryParam("start", pageable.getPageNumber()+1)
						.queryParam("display", pageable.getPageSize());
			} catch (UnsupportedEncodingException e) {
				throw new CustomException(ErrorCode.BAD_REQUEST);
			}
		}
		
		//���� ���� �ִ°�� ����
		if(pageable.getSort().toList().size() > 0) {
			String sort = pageable.getSort().toList().get(0).getProperty();
			//kakao�� ��ȸ�Ͽ����� ���������� ���� ��ȸ���� ���� ��� naver�� ����ȸ�� �ϱ⶧���� ���� ���� �ٲپ� �ش�.
			if(apiUrl.equals("naver") && sort.equals("accuracy")) sort = "sim";
			if(apiUrl.equals("naver") && sort.equals("recency")) sort = "date";
			builder.queryParam("sort", sort);
		}
		
		return builder;
	}
	

}
