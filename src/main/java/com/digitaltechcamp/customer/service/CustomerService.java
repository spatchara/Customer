package com.digitaltechcamp.customer.service;

import com.digitaltechcamp.customer.model.Customer;
import com.digitaltechcamp.customer.repositaries.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    public List<Customer> getCustomerList(){
        return customerRepository.findAll();

    }
    public  Customer getCustomerById(Long id){
        return customerRepository.findAllById(id);
    }

    public List<Customer> getByCustomerFirstname(String firstname){
        return customerRepository.findByFirstname(firstname);
    }

    public Customer createCustomer(Customer customer){
    return customerRepository.save(customer);
    }
    public Customer updateCustomer(Long id, Customer customerReq){
        return customerRepository.findAllById(id) != null ? customerRepository.save(customerReq) : null;
    }

    public boolean deleteById(Long id){
        try{
            customerRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e){
            return false;
        }
    }
}
