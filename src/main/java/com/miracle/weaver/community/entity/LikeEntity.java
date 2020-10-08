package com.miracle.weaver.community.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "like_log", schema = "public")
@IdClass(LikeEntityId.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeEntity implements Serializable {

    @Id
    private Integer boardId;

    @Id
    private Integer userId;
}
