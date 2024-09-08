package com.ecf.gamestore.utils;

import java.util.Objects;

public class ObjectUtils {

    public static boolean isNulls(Object...objects) {
        for(Object obj : objects) {
            if(Objects.isNull(obj)) return true;
        }
        return false;
    }
}
