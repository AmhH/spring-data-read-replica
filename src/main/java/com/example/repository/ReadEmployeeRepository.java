package com.example.repository;

import com.example.config.ReadOnlyRepository;
import com.example.model.Employee;
import java.util.List;
import org.springframework.data.repository.Repository;

/**
 * This is a read only repository
 */
@ReadOnlyRepository
public interface ReadEmployeeRepository extends Repository<Employee, Long> {

  List<Employee> findAll();
}
