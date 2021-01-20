package com.example.controller;

import com.example.model.Employee;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Transient;

public class FieldsWithAnnotation {
  public static void main(String[] args) {
    Field[] allFields = Employee.class.getDeclaredFields();

    Set<String> collect = Arrays.stream(allFields)
        .flatMap(field -> {
          List<Field> fields = new LinkedList<>();
          getFieldsForNested(field, fields);
          return fields.stream();
        })
        .filter(field ->  field.isAnnotationPresent(Transient.class))
        .map(field -> field.getName())
        .collect(Collectors.toSet());

    System.out.println(collect);
    System.out.println();
  }

  private static void getFieldsForNested(Field field, List<Field> all) {
    String packageName = field.getType().getPackageName();
    if(packageName.startsWith("com.example")){
      recurseOnFields(field.getType().getDeclaredFields(), all);
    } else if(packageName.startsWith("java.util")){
      String typeName = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName();
      try {
        Field[] fields = Class.forName(typeName).newInstance().getClass().getDeclaredFields();
        recurseOnFields(fields, all);
      } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
        e.printStackTrace();
      }
    }else {
      all.add(field);
    }
  }

  private static void recurseOnFields(Field[] extracted, List<Field> allFields){
    for (Field field : extracted) {
      getFieldsForNested(field, allFields);
    }
  }

  private static Stream<Field> getFieldsForNested1(Field field) {
    String packageName = field.getType().getPackageName();
    if(packageName.startsWith("com.example")){
      return Arrays.stream(field.getType().getDeclaredFields());
    }
    if(packageName.startsWith("java.util")){
      String typeName = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName();
      try {
        return Arrays.stream(Class.forName(typeName).newInstance().getClass().getDeclaredFields());
      } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
        e.printStackTrace();
      }
    }
    return Stream.of(field);
  }

}
