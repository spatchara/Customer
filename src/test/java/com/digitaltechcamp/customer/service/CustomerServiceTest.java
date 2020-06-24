package com.digitaltechcamp.customer.service;

import com.digitaltechcamp.customer.model.Customer;
import com.digitaltechcamp.customer.repositaries.CustomerRepository;
import com.digitaltechcamp.customer.support.CustomerSupportTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepository);
    }
    @DisplayName("Test Get all customer should return list")
    @Test
    void testGetAllCustomer() {
        when(customerRepository.findAll()).thenReturn(CustomerSupportTest.getCustomerList());
        List<Customer> resp = customerService.getCustomerList();
        assertEquals(1, resp.get(0).getId().intValue());
        assertEquals("Ryan", resp.get(0).getFirstname());
        assertEquals("giggs", resp.get(0).getLastname());
        assertEquals("233444555", resp.get(0).getPhoneNo());
        assertEquals("gig@mail.com", resp.get(0).getEmail());
        assertEquals(23, resp.get(0).getAge().intValue());


        assertEquals(2, resp.get(1).getId().intValue());
        assertEquals("David", resp.get(1).getFirstname());
        assertEquals("Backham", resp.get(1).getLastname());
        assertEquals("asdsad", resp.get(1).getPhoneNo());
        assertEquals("david@mail.com", resp.get(1).getEmail());
        assertEquals(40, resp.get(1).getAge().intValue());

    }
    @DisplayName("Test Get all customer should return list")
    @Test
    void testGetCustomerById() {
        Long reqParam = 1L;

        when(customerRepository.findAllById(reqParam)).thenReturn(CustomerSupportTest.getCustomerList().get(0));

        Customer resp = customerService.getCustomerById((reqParam));
        assertEquals(1, resp.getId().intValue());
        assertEquals("Ryan", resp.getFirstname());
        assertEquals("giggs", resp.getLastname());
        assertEquals("233444555", resp.getPhoneNo());
        assertEquals("gig@mail.com", resp.getEmail());
        assertEquals(23, resp.getAge().intValue());

    }

    @DisplayName("Test get  customer by name")
    @Test
    void testGetCustomeByName(){
        String  reqParam = "tiger";

        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstname("tiger");
        customer.setLastname("Ldew");
        customer.setEmail("Dew.com");
        customer.setPhoneNo("0985153944");
        customer.setAge(22);
        customerList.add(customer);

        customer  = new Customer();
        customer.setId(2L);
        customer.setFirstname("tiger");
        customer.setLastname("Ldew2");
        customer.setEmail("Dew2.com");
        customer.setPhoneNo("0985153945");
        customer.setAge(22);
        customerList.add(customer);

        customer  = new Customer();
        customer.setId(3L);
        customer.setFirstname("dew");
        customer.setLastname("Ldew2");
        customer.setEmail("Dew2.com");
        customer.setPhoneNo("0985153945");
        customer.setAge(22);
        customerList.add(customer);

        when(customerRepository.findByFirstname(reqParam)).thenReturn(customerList);
        List<Customer> respResult = customerService.getByCustomerFirstname(reqParam);

        assertEquals("tiger",respResult.get(0).getFirstname());
        assertEquals("tiger",respResult.get(1).getFirstname());
        assertEquals("dew",respResult.get(2).getFirstname());

        assertEquals(3,respResult.size());
    }

    @DisplayName("Test create customer should return New Customer")
    @Test
    void testCreateCustome(){
        when(customerRepository.save(CustomerSupportTest.getReqNewCustomer())).thenReturn(CustomerSupportTest.getReturnNewCustomer());

        Customer resp = customerService.createCustomer(CustomerSupportTest.getReqNewCustomer());
        assertEquals(1, resp.getId().intValue());
        assertEquals("test", resp.getFirstname());
        assertEquals("Mama", resp.getLastname());
        assertEquals("3242342", resp.getPhoneNo());
        assertEquals("aa@gmail.com", resp.getEmail());
        assertEquals(23, resp.getAge().intValue());
    }

    @DisplayName("Test update customer should return Success")
    @Test
    void testUpdateCustome(){
        Long reqId = 2L;
        when(customerRepository.findAllById(reqId)).thenReturn(CustomerSupportTest.getOldCustomer());
        when(customerRepository.save(CustomerSupportTest.getRepUpdateCustomer())).thenReturn(CustomerSupportTest.getRepUpdateCustomer());

        Customer resp = customerService.updateCustomer(reqId, CustomerSupportTest.getRepUpdateCustomer());
        assertEquals(2, resp.getId().intValue());
        assertEquals("Noon", resp.getFirstname());
        assertEquals("Bow", resp.getLastname());
        assertEquals("098888", resp.getPhoneNo());
        assertEquals("noon@gmail.com", resp.getEmail());
        assertEquals(5, resp.getAge().intValue());

    }
    @DisplayName("Test update customer should return fail")
    @Test
    void testUpdateCustomeFail(){
        Long reqId = 4L;
        when(customerRepository.findAllById(reqId)).thenReturn(null);
        Customer resp = customerService.updateCustomer(reqId, CustomerSupportTest.getUpdateCustomerFail());
        assertEquals(null, resp);
    }
    @DisplayName("Test Delete customer should return true")
    @Test
    void testDeleteCustomer(){
        Long reqId = 1L;
        doNothing().when(customerRepository).deleteById((reqId));
//        boolean resp = customerService.deleteById(reqId);
//        assertEquals(true, resp);
//        assertTrue(resp);
        assertTrue(customerService.deleteById(reqId));

    }
    @DisplayName("Test Delete customer should return fail")
    @Test
    void testDeleteCustomerfail() {
        Long reqId = 1L;
        doThrow(EmptyResultDataAccessException.class).when(customerRepository).deleteById(reqId);
//        boolean resp = customerService.deleteById(reqId);
//        assertEquals(false, resp);
//        assertFalse(resp);
        assertFalse(customerService.deleteById(reqId));
    }


}
