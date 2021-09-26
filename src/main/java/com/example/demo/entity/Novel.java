package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "novel")
public class Novel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serialNo;
	
	private String novelName;
	
	private String novelUrl;

	private int endFlag;

	private String lastTime;
}
