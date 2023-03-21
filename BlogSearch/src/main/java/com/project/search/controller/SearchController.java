package com.project.search.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.search.dto.ResponseDto;
import com.project.search.entity.KeywordCount;
import com.project.search.service.KeywordCountService;
import com.project.search.service.SearchService;

@RestController
public class SearchController {
	
	private final SearchService searchService;
	private final KeywordCountService keywordCountService;
	
	@Autowired
    public SearchController(SearchService searchService, KeywordCountService keywordCountService) {
        this.searchService = searchService;
        this.keywordCountService = keywordCountService;
    }
	
	/*
	 * īī�� ��α� �˻� 
	 * @ param = keyword / �˻�Ű����,  
	 * @ param = apiUrl / api ȣ���� ����Ʈ ��)kakao, naver (���� īī�� API �̿ܿ� ���ο� �˻� �ҽ��� �߰��� �� ������ ����ؾ� �մϴ�.)
	 */
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ResponseEntity<ResponseDto> search(@RequestParam String keyword, @RequestParam String apiUrl, @PageableDefault(page = 1, size = 10) Pageable pageable){
		//�Էµ� Ű���� �˻� ��� ��ȸ
		ResponseDto result = searchService.search(keyword, apiUrl, pageable);
		//�Էµ� Ű���� �˻�Ƚ�� ����
		keywordCountService.keywordCountAdd(keyword);
		return new ResponseEntity<ResponseDto>(result, HttpStatus.OK);
	}
	
	/*
	 * ��α� �˻� ���� RANK ��ȸ
	 */
	@RequestMapping(value="/rank", method=RequestMethod.GET)
	public ResponseEntity<List<KeywordCount>> rank(){
		// ���� Ű���� �˻� ���� 1~10�� ��ȸ
		List<KeywordCount> result = keywordCountService.getKeywordRank();
		return new ResponseEntity<List<KeywordCount>>(result, HttpStatus.OK);
	}
	
}
