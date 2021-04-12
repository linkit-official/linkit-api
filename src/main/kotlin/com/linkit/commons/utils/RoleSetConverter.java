package com.linkit.commons.utils;

import com.linkit.domain.user.model.Role;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleSetConverter implements AttributeConverter<Set<Role>, String> {
    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(Set<Role> attribute) {
        return attribute.stream().map(Enum::name).collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public Set<Role> convertToEntityAttribute(String string) {
        if (null == string) {
            return new HashSet<>();
        }
        return Arrays.stream(string.split(SPLIT_CHAR)).map(Role::valueOf).collect(Collectors.toSet());
    }
}
