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
	 * 카카오 블로그 검색 
	 * @ param = keyword / 검색키워드,  
	 * @ param = apiUrl / api 호출할 사이트 예)kakao, naver (추후 카카오 API 이외에 새로운 검색 소스가 추가될 수 있음을 고려해야 합니다.)
	 */
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ResponseEntity<ResponseDto> search(@RequestParam String keyword, @RequestParam String apiUrl, @PageableDefault(page = 1, size = 10) Pageable pageable){
		//입력된 키워드 검색 목록 조회
		ResponseDto result = searchService.search(keyword, apiUrl, pageable);
		//입력된 키워드 검색횟수 증가
		keywordCountService.keywordCountAdd(keyword);
		return new ResponseEntity<ResponseDto>(result, HttpStatus.OK);
	}
	
	/*
	 * 블로그 검색 순위 RANK 조회
	 */
	@RequestMapping(value="/rank", method=RequestMethod.GET)
	public ResponseEntity<List<KeywordCount>> rank(){
		// 현재 키워드 검색 순위 1~10등 조회
		List<KeywordCount> result = keywordCountService.getKeywordRank();
		return new ResponseEntity<List<KeywordCount>>(result, HttpStatus.OK);
	}
	
}
