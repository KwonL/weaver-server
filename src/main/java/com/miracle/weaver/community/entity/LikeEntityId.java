package com.miracle.weaver.community.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeEntityId implements Serializable {

    private Integer boardId;

    private Integer userId;
}
