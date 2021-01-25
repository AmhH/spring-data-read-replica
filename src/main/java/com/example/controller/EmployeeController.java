package com.example.controller;

import com.example.model.Employee;
import com.example.model.Person;
import com.example.repository.EmployeeRepository;
import com.example.repository.ReadEmployeeRepository;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeRepository employeeRepository;
  private final ReadEmployeeRepository readEmployeeRepository;


  @GetMapping("/employee")
  public List<Employee> getEmployees(){
    return readEmployeeRepository.findAll();
  }

  @PostMapping("/employee")
  @ResponseStatus(HttpStatus.CREATED)
  public void addEmployee(@RequestBody Employee employee){
    employeeRepository.save(employee);
  }

  public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
    fields.addAll(
        Arrays.stream(type.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Transient.class))
            .collect(Collectors.toList())
    );
    if (type.getSuperclass() != null) {
      getAllFields(fields, type.getSuperclass());
    }
    return fields;
  }

  public static void main(String[] args) {
    List<Field> fieldList = new ArrayList<>();
    List<Field> allFields = getAllFields(fieldList, Person.class);
    System.out.println(fieldList);
    System.out.println(allFields);
  }
  /**
  public static void getAllFields(){
    Reflections reflections = new Reflections(
        new ConfigurationBuilder()
            .setUrls(ClasspathHelper.forPackage("your.packageName"))
            .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner(), new FieldAnnotationsScanner()));

    Set<Field> fields =
        reflections.getFieldsAnnotatedWith(YourAnnotation.class);

    fields.stream().forEach(field -> {
      System.out.println(field.getDeclaringClass());
      System.out.println(field.getName());
    });
  }*/

}
