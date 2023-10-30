package ru.ddc.schedulestoring.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

@Component
public class ObjectFieldsFillerA {
    private static Long minLongValue = 1L;
    private static Long maxLongValue = 200000L;
    private static Integer stringLength = 10;


    public static <T> void fillFieldsWithRandomValues(T object, String... fieldsNames) {
        Class<?> aClass = object.getClass();
        Arrays.stream(fieldsNames).forEach(new Consumer<String>() {
            @Override
            public void accept(String fieldName) {
                try {
                    Field field = aClass.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    setRandomValueIntoField(object, field);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private static <T> void setRandomValueIntoField(T object, Field field) throws IllegalAccessException {
        String fieldType = field.getType().getSimpleName();
            switch (fieldType) {
                case "Long" -> setLongValue(object, field, minLongValue, maxLongValue);
                case "String" -> setStringValue(object, field, stringLength);
//                case "ZonedDateTime" -> setZonedDateTimeValue(object, field);
                case "LocalDate" -> setLocalDateValue(object, field);
//                case "Student" -> setStudentValue(object, field);
//                case "Mentor" -> setMentorValue(object, field);
//                case "Boolean" -> setBooleanValue(object, field);
//                default -> setNullValue(object, field);
            }
    }

    private static void setLongValue(Object object, Field field, Long minValue, Long maxValue) throws IllegalAccessException {
        Long randomLong = ThreadLocalRandom.current().nextLong(minValue, maxValue);
        field.set(object, randomLong);
    }

    private static void setStringValue(Object object, Field field, Integer stringLength) throws IllegalAccessException {
        String randomStr;
        if ("email".equalsIgnoreCase(field.getName())) {
            randomStr = RandomStringUtils.randomAlphanumeric(stringLength/2) + "@"
                    + RandomStringUtils.randomAlphanumeric(stringLength - 1 - stringLength/2);
        } else {
            randomStr = RandomStringUtils.randomAlphanumeric(stringLength);
        }
        field.set(object, randomStr);
    }

    private static void setLocalDateValue(Object object, Field field) throws IllegalAccessException {
        LocalDate randomDate = LocalDate.now()
                .minusDays(ThreadLocalRandom.current().nextInt(0, 15000));
        field.set(object, randomDate);
    }

    public static void setMinLongValue(Long minLongValue) {
        ObjectFieldsFillerA.minLongValue = minLongValue;
    }

    public static void setMaxLongValue(Long maxLongValue) {
        ObjectFieldsFillerA.maxLongValue = maxLongValue;
    }

    public static void setStringLength(Integer stringLength) {
        ObjectFieldsFillerA.stringLength = stringLength;
    }
}
