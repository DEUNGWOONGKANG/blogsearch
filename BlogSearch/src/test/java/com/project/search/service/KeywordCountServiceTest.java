package com.project.search.service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.project.search.entity.KeywordCount;

@SpringBootTest
class KeywordCountServiceTest {
	@Autowired
    private KeywordCountService keywordCountService;
	
	@Test
	@DisplayName("키워드 검색횟수 증가 동시성 테스트")
	void keywordCountAddTest() throws InterruptedException  {
		int numberOfThreads = 2;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        
        String keyword = "버즈";
        
        System.out.println("########## 키워드 검색횟수 증가 동시성 테스트 시작 ##########");
        
        service.execute(() -> {
        	keywordCountService.keywordCountAdd(keyword);
            latch.countDown();
        });
        service.execute(() -> {
        	keywordCountService.keywordCountAdd(keyword);
            latch.countDown();
        });
        
        latch.await();
        
        System.out.println("########## 키워드 검색횟수 증가 동시성 테스트 검증 ##########");
        
        List<KeywordCount> rank = keywordCountService.getKeywordRank();
        int i = 1;
        for(KeywordCount k : rank) {
        	System.out.println("########## 랭킹 " + i );
        	System.out.println(k.getKeyword() + " ===== " + k.getCount() + "번" );
        	i++;
        }
	}
}
