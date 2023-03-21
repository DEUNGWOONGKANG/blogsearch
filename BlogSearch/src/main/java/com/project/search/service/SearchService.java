package com.project.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.search.dto.ResponseDto;
import com.project.search.util.ApiUtil;

@Service
public class SearchService{
	private final ApiUtil apiUtil;
	
	@Autowired
    public SearchService(ApiUtil apiUtil) {
        this.apiUtil = apiUtil;
    }

	public ResponseDto search(String keyword, String apiUrl, Pageable pageable) {
		return apiUtil.callGet(keyword, apiUrl, pageable);
	}

}
