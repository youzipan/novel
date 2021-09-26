package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.NovelTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NovelTitleDao extends JpaRepository<NovelTitle, Long> {

	List<NovelTitle> findByNovelNo(Long serialNo);

	@Query(value = "select * from novel_title u where u.novel_no=? order by u.serial_no desc limit 1", nativeQuery = true)
	NovelTitle findByNovelNoLimitOne(Long serialNo);
}
