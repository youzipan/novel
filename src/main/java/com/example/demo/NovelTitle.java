package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "novel_title")
public class NovelTitle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serialNo;
	
	private Long novelNo;
	
	private String title;

	private String url;

	private String regDate;

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public NovelTitle() {

	}

	public NovelTitle(Long serialNo, String title) {
		super();
		this.serialNo = serialNo;
		this.title = title;
	}

	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	public Long getNovelNo() {
		return novelNo;
	}

	public void setNovelNo(Long novelNo) {
		this.novelNo = novelNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}
