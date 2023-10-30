package ru.ddc.schedulestoring.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.*;
import java.util.concurrent.ThreadLocalRandom;

public class ObjectFieldsFiller {

    public static <T> T fillFieldsRandomValues(Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {//T tObject) {

        T tObject = tClass.getDeclaredConstructor().newInstance();
//        Class<?> objectClass = tObject.getClass();
        Field[] fields = tClass.getDeclaredFields();

        for (Field field : fields) {
            setRandomValueInField(field, tObject);
        }

        return tObject;
    }

    private static void setRandomValueInField(Field field, Object object) {

        Long minLongValue = 1L;
        Long maxLongValue = 200000L;

        Integer stringLength = 10;

        field.setAccessible(true);
        String fieldType = field.getType().getSimpleName();

        try {
            switch (fieldType) {
                case "Long" -> setLongValue(object, field, minLongValue, maxLongValue);
                case "String" -> setStringValue(object, field, stringLength);
                case "ZonedDateTime" -> setZonedDateTimeValue(object, field);
                case "LocalDate" -> setLocalDateValue(object, field);
//                case "Student" -> setStudentValue(object, field);
//                case "Mentor" -> setMentorValue(object, field);
                case "Boolean" -> setBooleanValue(object, field);
                default -> setNullValue(object, field);
            }
        } catch (IllegalAccessException ignore) {// TODO
        }
    }

    private static void setLocalDateValue(Object object, Field field) throws IllegalAccessException {
        LocalDate randomDate = LocalDate.now()
                .minusDays(ThreadLocalRandom.current().nextInt(0, 15000));
        field.set(object, randomDate);
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

    private static void setZonedDateTimeValue(Object object, Field field) throws IllegalAccessException {
        ZonedDateTime randomDateTime = ZonedDateTime.now()
                .minusSeconds(ThreadLocalRandom.current().nextInt(0, 1659624728));
        field.set(object, randomDateTime);
    }

//    private static void setStudentValue(Object object, Field field) throws IllegalAccessException {
//        Student randomStudent = fillFieldsRandomValues(new Student());
//        field.set(object, randomStudent);
//    }
//
//    private static void setMentorValue(Object object, Field field) throws IllegalAccessException {
//        Mentor randomMentor = fillFieldsRandomValues(new Mentor());
//        field.set(object, randomMentor);
//    }

    private static void setBooleanValue(Object object, Field field) throws IllegalAccessException {
        Boolean randomBoolean = ThreadLocalRandom.current().nextBoolean();
        field.set(object, randomBoolean);
    }

    private static void setNullValue(Object object, Field field) throws IllegalAccessException {
        field.set(object, null);
    }
}
