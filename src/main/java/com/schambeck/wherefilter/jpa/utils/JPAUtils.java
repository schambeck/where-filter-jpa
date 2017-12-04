package com.schambeck.wherefilter.jpa.utils;

import org.parboiled.common.StringUtils;

import javax.persistence.criteria.Path;

public final class JPAUtils {

    private JPAUtils() {
    }

    public static Path<?> getPath(Path<?> root, String fieldName) {
        final String[] fieldNames = fieldName.split("\\.", -1);
        if (fieldNames.length > 1) {
            String firstProperty = fieldNames[0];
            String otherProperties = StringUtils.join(fieldNames, ".", 1, fieldNames.length);
            Path<?> firstPropertyType = getPath(root, firstProperty);
            return getPath(firstPropertyType, otherProperties);
        }

        return root.get(fieldName);
    }

}
