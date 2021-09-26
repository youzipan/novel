package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "novel")
public class NovelHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serialNo;
	
	private String novelName;
	
	private String novelUrl;

	private int endFlag;

	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	public String getNovelName() {
		return novelName;
	}

	public void setNovelName(String novelName) {
		this.novelName = novelName;
	}

	public String getNovelUrl() {
		return novelUrl;
	}

	public void setNovelUrl(String novelUrl) {
		this.novelUrl = novelUrl;
	}

	public int getEndFlag() {
		return endFlag;
	}

	public void setEndFlag(int endFlag) {
		this.endFlag = endFlag;
	}
	
	

}
