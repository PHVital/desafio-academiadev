package main.java.br.com.academiadev.infrastructure.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class GenericCsvExporter {
    public static <T> String exportToCsv(List<T> data, List<String> fieldNames) {
        if (data == null || data.isEmpty()) {
            return "";
        }

        StringBuilder csv = new StringBuilder();

        csv.append(String.join(",", fieldNames)).append("\n");

        for (T item : data) {
            String line = fieldNames.stream().map(fieldName -> {
                try {
                    Field field = getFieldRecursively(item.getClass(), fieldName);
                    field.setAccessible(true);
                    Object value = field.get(item);
                    return value != null ? value.toString() : "";
                } catch (Exception e) {
                    return "ERROR";
                }
            }).collect(Collectors.joining(","));

            csv.append(line).append("\n");
        }

        return csv.toString();
    }

    private static Field getFieldRecursively(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                return getFieldRecursively(clazz.getSuperclass(), fieldName);
            }
            throw e;
        }
    }
}
