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
	@DisplayName("��α� �˻� �׽�Ʈ")
	void searchTest() {
		System.out.println("########## īī�� ��α� �˻� �׽�Ʈ ##########");
		Pageable paging = PageRequest.of(0, 10, Sort.Direction.ASC, "accuracy");
		ResponseDto result = searchService.search("īī��", "kakao", paging);
		List<Map<String, Object>> blogList = result.getContent();
		System.out.println("PAGE : " + result.getPage());
		System.out.println("PAGE SIZE : " + result.getSize());
        System.out.println("TOTAL COUNT : " + result.getTotalCount());
        for(int i=0; i<blogList.size(); i++) {
        	System.out.println("### ����  ### " + (i+1));
        	System.out.println("���� : " + blogList.get(i).get("title"));
        }
        
        
        System.out.println("########## ���̹� ��α� �˻� �׽�Ʈ ##########");
        paging = PageRequest.of(0, 10, Sort.Direction.ASC, "sim");
        result = searchService.search("���̹�", "naver", paging);
        blogList = result.getContent();
        System.out.println("PAGE : " + result.getPage());
        System.out.println("PAGE SIZE : " + result.getSize());
        System.out.println("TOTAL COUNT : " + result.getTotalCount());
        for(int i=0; i<blogList.size(); i++) {
        	System.out.println("### ����  ### " + (i+1));
        	System.out.println("���� : " + blogList.get(i).get("title"));
        }
	}
}
