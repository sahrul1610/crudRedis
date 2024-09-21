package com.app79.crudRedis.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app79.crudRedis.model.ESCustomer;
import com.app79.crudRedis.service.CustomerService;

@RestController
@RequestMapping("/esApi")
public class ElasticsearchController {

   @Autowired
   CustomerService customerServices;

   @GetMapping("/getAllESCustomer")
   @ResponseBody
   public ResponseEntity<?> getAllESCustomerData() {
       Iterable<ESCustomer> customerList = customerServices.getAllESCustomer();
       Map<String, Object> response = new HashMap<>();
       response.put("status", "success");
       response.put("message", "Customer loaded successfully");
       response.put("data", customerList);
       return ResponseEntity.ok(response);
   }

   @GetMapping("/getESCustomer/{id}")
   @ResponseBody
   public ResponseEntity<?> getESCustomerData(@PathVariable Long id) {
       ESCustomer customer = customerServices.getESCustomer(id);
       Map<String, Object> response = new HashMap<>();
       response.put("status", "success");
       response.put("message", "Customer with ID " + id + " loaded successfully");
       response.put("data", customer);
       return ResponseEntity.ok(response);
   }
}