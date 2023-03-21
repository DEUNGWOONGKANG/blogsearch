package com.project.search.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.search.entity.KeywordCount;

@Repository
public interface KeywordCountRepository extends JpaRepository<KeywordCount, String> {

	List<KeywordCount> findTop10ByOrderByCountDesc();
	
	// 동시성 제어를 위해 비관적 락 구현
//	@Lock(LockModeType.PESSIMISTIC_WRITE)
//	@Query("select a from KeywordCount a where a.keyword = :keyword")
//	KeywordCount findByIdForUpdate(@Param("keyword") String keyword);

}
