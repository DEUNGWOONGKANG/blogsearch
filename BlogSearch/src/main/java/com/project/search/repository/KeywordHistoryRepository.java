package com.project.search.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.search.dto.KeywordCountInterface;
import com.project.search.entity.KeywordHistory;

@Repository
public interface KeywordHistoryRepository extends JpaRepository<KeywordHistory, String> {
	@Query(value = "select keyword, count(1) as count "
			+ "from keyword_count  "
			+ "group by keyword "
			+ "order by count desc limit 10", nativeQuery = true)
	List<KeywordCountInterface> findTop10Keyword();

}
