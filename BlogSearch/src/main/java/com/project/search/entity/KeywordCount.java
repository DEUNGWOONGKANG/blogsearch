package com.project.search.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="keyword_count")
public class KeywordCount {
	@Id
	@Column(name = "keyword")
	String keyword;
	
	@Column(name = "count")
	long count;
}
