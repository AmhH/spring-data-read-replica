package com.example.model;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Department {

  @Id
  private Long id;

  @Transient
  private String name;

  public static void main(String[] args) throws NoSuchFieldException {
    Arrays.stream(Employee.class.getDeclaredFields())
        .forEach(f -> {
          if(f.getType().getPackageName().startsWith("java.util")){
            String typeName = ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0]
                .getTypeName();
            try {
              System.out.println(Arrays.toString(Class.forName(typeName).newInstance().getClass().getDeclaredFields()));
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
              e.printStackTrace();
            }
          }else {
            System.out.println(f.getGenericType());
          }
        });


    /*Field integerListField = Employee.class.getDeclaredField("department");
    ParameterizedType integerListType = (ParameterizedType) integerListField.getGenericType();
    Class<?> integerListClass = (Class<?>) integerListType.getActualTypeArguments()[0];
    System.out.println(integerListClass); // class java.lang.Integer.*/
  }
}
