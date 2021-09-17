package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovelDao extends JpaRepository<Novel, Long> {

	List<Novel> findByEndFlagNot(int endFlag);
}
