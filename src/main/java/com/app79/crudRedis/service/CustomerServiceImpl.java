package com.app79.crudRedis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.app79.crudRedis.model.Customer;
import com.app79.crudRedis.model.ESCustomer;
import com.app79.crudRedis.repository.CustomerESRepository;
import com.app79.crudRedis.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerESRepository customerESRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private static final String KEY = "USER";

    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        if (customerList.isEmpty()) {
            return new ArrayList<Customer>();
        }
        return customerList;
    }

    @Override
    public Customer getCustomer(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            return new Customer();
        }
        Customer customer = optionalCustomer.get();
        return customer;
    }

    @Override

    public Customer storeCustomer(Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findByName(customer.getName());

        if (existingCustomer.isPresent()) {
            throw new IllegalArgumentException("Customer with the same name already exists");

        }

        Customer newCustomer = customerRepository.saveAndFlush(customer);

        // redis
        redisTemplate.opsForHash().put(KEY, newCustomer.getId(), newCustomer);

        // elasticsearch
        ESCustomer esCustomer = new ESCustomer(newCustomer.getId(), newCustomer.getName(), newCustomer.getAge());
        customerESRepository.save(esCustomer);

        return newCustomer;
    }

    @Override
    public void updateCustomer(Customer customer, Long id) {
        try {

            Customer newCustomer = customerRepository.save(customer);

            // redis
            redisTemplate.opsForHash().put(KEY, id, newCustomer);

            // elasticsearch
            ESCustomer esCustomer = new ESCustomer(newCustomer.getId(), newCustomer.getName(), newCustomer.getAge());
            customerESRepository.save(esCustomer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean deleteCustomer(Long id) {
        try {

            customerRepository.deleteById(id);

            // redis
            redisTemplate.opsForHash().delete(KEY, id);

            // elasticsearch
            customerESRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Redis
    @Override
    public List<Customer> getAllRedisCustomer() {
        List<Customer> customerList = redisTemplate.opsForHash().values(KEY);
        if (customerList.isEmpty()) {
            return new ArrayList<Customer>();
        }
        return customerList;
    }

    @Override
    public Customer getRedisCustomer(Long id) {
        Customer customer = (Customer) redisTemplate.opsForHash().get(KEY, id);
        return customer;
    }

    // ElasticSearch
    @Override
    public List<ESCustomer> getAllESCustomer() {
        List<ESCustomer> esCustomerList = new ArrayList<>();
        customerESRepository.findAll().forEach(esCustomerList::add); // Convert Iterable to List
        return esCustomerList;
    }

    @Override
    public ESCustomer getESCustomer(Long id) {
        Optional<ESCustomer> optionalCustomer = customerESRepository.findById(id);
        return optionalCustomer.orElse(new ESCustomer()); // Return an empty ESCustomer if not found
    }

}