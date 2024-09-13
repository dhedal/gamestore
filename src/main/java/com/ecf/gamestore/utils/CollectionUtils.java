package com.ecf.gamestore.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class CollectionUtils {

    public static boolean isNullOrEmpty(Collection collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Map map) {
        return Objects.isNull(map) || map.isEmpty();
    }
}
