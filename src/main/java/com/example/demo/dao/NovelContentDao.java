package com.example.demo.dao;

import com.example.demo.entity.NovelContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovelContentDao extends JpaRepository<NovelContent, Long> {

	List<NovelContent> findByNovelNo(Long novelNo);

	NovelContent findByTitleNo(Long serialNo);
}
