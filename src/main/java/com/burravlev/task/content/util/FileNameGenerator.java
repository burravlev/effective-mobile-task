package com.burravlev.task.content.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileNameGenerator {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
