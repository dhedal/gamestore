package com.ecf.gamestore.models.enumerations;

import com.ecf.gamestore.util.RoleDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = RoleDeserializer.class)
public enum Role {
    UNDEFINED( 0, "indéfinie"),
    USER(1, "user"),
    EMPLOYEE( 2, "employee"),
    ADMIN( 3, "admin");


    private Integer key;
    private String label;

    Role(Integer key, String label){
        this.key = key;
        this.label = label;
    }

    public Integer getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public static Role getByKey(int key){
        if(key == EMPLOYEE.key.intValue()) return EMPLOYEE;
        if(key == ADMIN.key.intValue()) return ADMIN;
        if(key == USER.key.intValue()) return USER;
        return UNDEFINED;
    }
}
