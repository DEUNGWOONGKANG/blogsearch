package com.project.search.dto;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import lombok.Getter;

@Getter
public class ResponseDto {
		//API를 통해 조회된 목록
		private List<Map<String, Object>> content;
		
		//페이징 처리를 위한 객체들
	    private int page;
	    private int size;
	    private long totalCount;
	    
		public ResponseDto(Map<String, Object> searchResult, String apiUrl, Pageable pageable) {
			if(apiUrl.equals("kakao")) {
				//카카오 검색API response 
				Map meta = (Map) searchResult.get("meta");
				this.page = pageable.getPageNumber();
				this.size = pageable.getPageSize();
				this.totalCount = Long.parseLong(meta.get("total_count").toString());
				this.content = (List) searchResult.get("documents");
			}else if(apiUrl.equals("naver")) {
				//네이버 검색API response
				this.page = pageable.getPageNumber();
				this.size = pageable.getPageSize(); 
				this.totalCount = Long.parseLong(searchResult.get("total").toString());
				this.content = (List) searchResult.get("items");
			}
		}
}
