package com.miracle.weaver.community.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.miracle.weaver.user.User;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
