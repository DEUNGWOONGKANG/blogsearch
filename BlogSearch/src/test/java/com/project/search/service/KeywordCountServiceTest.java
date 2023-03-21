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
	@DisplayName("Ű���� �˻�Ƚ�� ���� ���ü� �׽�Ʈ")
	void keywordCountAddTest() throws InterruptedException  {
		int numberOfThreads = 2;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        
        String keyword = "����";
        
        System.out.println("########## Ű���� �˻�Ƚ�� ���� ���ü� �׽�Ʈ ���� ##########");
        
        service.execute(() -> {
        	keywordCountService.keywordCountAdd(keyword);
            latch.countDown();
        });
        service.execute(() -> {
        	keywordCountService.keywordCountAdd(keyword);
            latch.countDown();
        });
        
        latch.await();
        
        System.out.println("########## Ű���� �˻�Ƚ�� ���� ���ü� �׽�Ʈ ���� ##########");
        
        List<KeywordCount> rank = keywordCountService.getKeywordRank();
        int i = 1;
        for(KeywordCount k : rank) {
        	System.out.println("########## ��ŷ " + i );
        	System.out.println(k.getKeyword() + " ===== " + k.getCount() + "��" );
        	i++;
        }
	}
}
