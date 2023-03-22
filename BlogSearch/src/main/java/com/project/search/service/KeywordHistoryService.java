package com.project.search.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.search.dto.KeywordCountInterface;
import com.project.search.entity.KeywordHistory;
import com.project.search.repository.KeywordHistoryRepository;
import com.project.search.util.exception.CustomException;
import com.project.search.util.exception.ErrorCode;

@Service
public class KeywordHistoryService{
	private final KeywordHistoryRepository keywordHistoryRepository;
	
	@Autowired
    public KeywordHistoryService(KeywordHistoryRepository keywordHistoryRepository) {
        this.keywordHistoryRepository = keywordHistoryRepository;
    }

	public void useKeyword(String keyword) {
		if(keyword.equals("") || keyword == null) throw new CustomException(ErrorCode.BAD_REQUEST);
		KeywordHistory history = new KeywordHistory();
		history.setKeyword(keyword);
		history.setSearchdt(LocalDateTime.now());
		keywordHistoryRepository.save(history);
	}

	public List<KeywordCountInterface> getKeywordRank() {
		List<KeywordCountInterface> result = keywordHistoryRepository.findTop10Keyword();
		return result;
	}

	

}
