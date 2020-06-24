package com.digitaltechcamp.customer.controller;

import com.digitaltechcamp.customer.model.Customer;
import com.digitaltechcamp.customer.service.CustomerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public List<Customer> customerList(){
        return customerService.getCustomerList();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        Customer customer = customerService.getCustomerById(id);
        if(customer != null){
            return  ResponseEntity.ok(customer);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list/name/{firstname}")
    public  ResponseEntity<?> getCustomerByFirstName(@PathVariable String firstname){
        List<Customer> customer = customerService.getByCustomerFirstname(firstname);
        if (customer != null){
            return ResponseEntity.ok(customer);
        }else {
            return  ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer body){
        Customer customer = customerService.createCustomer(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCustomer(@PathVariable Long id, @Valid @RequestBody Customer body){
        body.setId(id);
        Customer customer = customerService.updateCustomer(id, body);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        return customerService.deleteById(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }


}
