package com.project.search.service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.search.dto.KeywordCountInterface;

@SpringBootTest
class KeywordCountServiceTest {
	@Autowired
    private KeywordHistoryService keywordHistoryService;
	
	@Test
	@DisplayName("키워드 검색시 해당 키워드 검색횟수 count 증가 테스트")
	void keywordCountAddTest() throws InterruptedException  {
		int numberOfThreads = 5;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        
        String keyword = "신용카드";
        
        for(int i=0; i<numberOfThreads; i++) {
        	service.execute(() -> {
        		keywordHistoryService.useKeyword(keyword);
                latch.countDown();
            });
        }
        latch.await();
        
        //검색어 랭킹으로 테스트
        List<KeywordCountInterface> rank = keywordHistoryService.getKeywordRank();
        int i = 1;
        for(KeywordCountInterface k : rank) {
        	System.out.println("########## 검색어 랭킹 " + i );
        	System.out.println("########## 키워드 : " + k.getKeyword() + " ===== " + k.getCount() + " 회");
        	i++;
        }
	}
}
