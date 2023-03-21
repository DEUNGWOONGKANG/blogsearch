package com.project.search.dto;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import lombok.Getter;

@Getter
public class ResponseDto {
		//API�� ���� ��ȸ�� ���
		private List<Map<String, Object>> content;
		
		//����¡ ó���� ���� ��ü��
	    private int page;
	    private int size;
	    private long totalCount;
	    
		public ResponseDto(Map<String, Object> searchResult, String apiUrl, Pageable pageable) {
			if(apiUrl.equals("kakao")) {
				//īī�� �˻�API response 
				Map meta = (Map) searchResult.get("meta");
				this.page = pageable.getPageNumber();
				this.size = pageable.getPageSize();
				this.totalCount = Long.parseLong(meta.get("total_count").toString());
				this.content = (List) searchResult.get("documents");
			}else if(apiUrl.equals("naver")) {
				//���̹� �˻�API response
				this.page = pageable.getPageNumber();
				this.size = pageable.getPageSize(); 
				this.totalCount = Long.parseLong(searchResult.get("total").toString());
				this.content = (List) searchResult.get("items");
			}
		}
}
