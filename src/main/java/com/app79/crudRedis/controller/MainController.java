package com.app79.crudRedis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app79.crudRedis.model.Customer;
import com.app79.crudRedis.service.CustomerService;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    CustomerService customerServices;

    @GetMapping("/getAllCustomer")
    @ResponseBody
    public ResponseEntity<?> getAllCustomerData() {
        List<Customer> customerList = customerServices.getAllCustomer();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Customer loaded successfully");
        response.put("data", customerList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getCustomer/{id}")
    @ResponseBody
    public ResponseEntity<?> getCustomerData(@PathVariable Long id) {
        Customer customer = customerServices.getCustomer(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Customer with ID " + id + " loaded successfully");
        response.put("data", customer);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/storeCustomer")
    @ResponseBody
    public ResponseEntity<?> storeCustomerData(@RequestBody Customer customer) {
        try {
            Customer createdCustomer = customerServices.storeCustomer(customer);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Customer created successfully");
            response.put("data", createdCustomer);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PutMapping("/updateCustomer/{id}")
    @ResponseBody
    public ResponseEntity<?> updateCustomerData(@RequestBody Customer customer, @PathVariable Long id) {
        try {
            Customer existingCustomer = customerServices.getCustomer(id);
            if(null == existingCustomer){
                throw new IllegalArgumentException("Customer with ID "+id+" is not exists");
            }
            if(id != customer.getId()){
                throw new IllegalArgumentException("Id in request body does not match Customer's Id");
            }
            customerServices.updateCustomer(customer, id);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Customer with Id " + id + " updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/deleteCustomer/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteCustomerData(@PathVariable Long id) {
        Boolean deleted = customerServices.deleteCustomer(id);
        if (!deleted) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Customer with Id " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Customer with id " + id + " deleted successfully");
        return ResponseEntity.ok(response);
    }
} 
