package com.ecf.gamestore.utils;

import java.util.Collection;
import java.util.Objects;

public class CollectionUtils {

    public static boolean isNullOrEmpty(Collection collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }
}
