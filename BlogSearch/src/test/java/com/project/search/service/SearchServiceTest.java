package com.project.search.service;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.project.search.dto.ResponseDto;

@SpringBootTest
class SearchServiceTest {
	@Autowired
    private SearchService searchService;
	
	@Test
	@DisplayName("블로그 검색 테스트")
	void searchTest() {
		System.out.println("########## 카카오 블로그 검색 테스트 ##########");
		Pageable paging = PageRequest.of(0, 10, Sort.Direction.ASC, "accuracy");
		ResponseDto result = searchService.search("카카오", "kakao", paging);
		List<Map<String, Object>> blogList = result.getContent();
		System.out.println("PAGE : " + result.getPage());
		System.out.println("PAGE SIZE : " + result.getSize());
        System.out.println("TOTAL COUNT : " + result.getTotalCount());
        for(int i=0; i<blogList.size(); i++) {
        	System.out.println("### 순서  ### " + (i+1));
        	System.out.println("제목 : " + blogList.get(i).get("title"));
        }
        
        
        System.out.println("########## 네이버 블로그 검색 테스트 ##########");
        paging = PageRequest.of(0, 10, Sort.Direction.ASC, "sim");
        result = searchService.search("네이버", "naver", paging);
        blogList = result.getContent();
        System.out.println("PAGE : " + result.getPage());
        System.out.println("PAGE SIZE : " + result.getSize());
        System.out.println("TOTAL COUNT : " + result.getTotalCount());
        for(int i=0; i<blogList.size(); i++) {
        	System.out.println("### 순서  ### " + (i+1));
        	System.out.println("제목 : " + blogList.get(i).get("title"));
        }
	}
}
