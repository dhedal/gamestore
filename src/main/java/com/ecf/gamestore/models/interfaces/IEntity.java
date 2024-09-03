package com.ecf.gamestore.models.interfaces;

import java.time.LocalDateTime;

public interface IEntity {

    public Long getId();
    public void setUuid(String uuid);
    public void setCreatedAt( LocalDateTime creatAt);
    public void setUpdatedAt( LocalDateTime updateAt);
}
