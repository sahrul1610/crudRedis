package com.app79.crudRedis.service;
import java.util.List;

import com.app79.crudRedis.model.Customer;
import com.app79.crudRedis.model.ESCustomer;


public interface CustomerService {
    List<Customer> getAllCustomer();
    Customer getCustomer(Long id);
    Customer storeCustomer(Customer customer);
    void updateCustomer(Customer customer, Long id);
    Boolean deleteCustomer(Long id);
     // Fungsi baru untuk Redis
    List<Customer> getAllRedisCustomer();
    Customer getRedisCustomer(Long id);

    // Fungsi baru untuk Elasticsearch
    List<ESCustomer> getAllESCustomer(); 
    ESCustomer getESCustomer(Long id); 
}
