package com.example.controller;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import com.example.repository.ReadEmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

}
