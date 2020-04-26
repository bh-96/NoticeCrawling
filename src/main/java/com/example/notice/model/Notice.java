package com.example.notice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

}
