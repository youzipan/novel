package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "novel_content")
public class NovelContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serialNo;
    private Long titleNo;

    private Long novelNo;

    private String title;

    private String url;

    private String content;
}
