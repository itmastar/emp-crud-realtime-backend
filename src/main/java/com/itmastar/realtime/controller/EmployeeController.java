package com.itmastar.realtime.controller;

import com.itmastar.realtime.exception.ResourceNotFoundException;
import com.itmastar.realtime.model.Employee;
import com.itmastar.realtime.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/")
public class EmployeeController {

    private ResourceNotFoundException exception;
@Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @PostMapping("/employee")
    public Employee createEmployeeRecord(@RequestBody Employee employee){
       return  employeeRepository.save(employee);

    }
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws ResourceNotFoundException {
        Employee response= employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Not Exists with id :"+id));
   return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id,@RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee existingEmployee= employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Not Exists with id :"+id));

           existingEmployee.setFirstname(employeeDetails.getFirstname());
           existingEmployee.setLastname(employeeDetails.getLastname());
           existingEmployee.setEmail(employeeDetails.getEmail());
         Employee updatedEmployee=  employeeRepository.save(existingEmployee);
         return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmp(@PathVariable Long id) throws ResourceNotFoundException {
        Employee existingEmployee= employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee Not Exists with id :"+id));

        employeeRepository.delete(existingEmployee);
        Map<String,Boolean> response= new HashMap<>();
        response.put("Deleted",Boolean.TRUE);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
