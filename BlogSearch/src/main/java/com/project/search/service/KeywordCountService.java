package com.project.search.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.search.entity.KeywordCount;
import com.project.search.repository.KeywordCountRepository;

@Service
public class KeywordCountService{
	private final KeywordCountRepository keywordCountRepository;
	
	@Autowired
    public KeywordCountService(KeywordCountRepository keywordCountRepository) {
        this.keywordCountRepository = keywordCountRepository;
    }

	//���ü� ��� ���� �˻�Ƚ�� ���� �޼��忡 synchronized ����
	public synchronized void keywordCountAdd(String keyword) {
		KeywordCount data = keywordCountRepository.findById(keyword).orElse(null);
		if(data != null) {
			data.setCount(data.getCount()+1);
		}else{
			//�ű�
			data = new KeywordCount();
			data.setKeyword(keyword);
			data.setCount(1);
		}
		keywordCountRepository.save(data);
	}

	public List<KeywordCount> getKeywordRank() {
		return keywordCountRepository.findTop10ByOrderByCountDesc();
	}
	

}
