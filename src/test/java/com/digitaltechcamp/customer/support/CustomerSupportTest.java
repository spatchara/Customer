package com.digitaltechcamp.customer.support;

import com.digitaltechcamp.customer.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerSupportTest {
    public static List<Customer> getCustomerList(){
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstname("Ryan");
        customer.setLastname("giggs");
        customer.setPhoneNo("233444555");
        customer.setEmail("gig@mail.com");
        customer.setAge(23);
        customerList.add(customer);

        customer = new Customer();
        customer.setId(2L);
        customer.setFirstname("David");
        customer.setLastname("Backham");
        customer.setPhoneNo("asdsad");
        customer.setEmail("david@mail.com");
        customer.setAge(40);
        customerList.add(customer);
        return customerList;
    }
    public static Customer getReqNewCustomer(){
        Customer customerReq = new Customer();
        customerReq.setFirstname("Aun");
        customerReq.setLastname("Ming");
        customerReq.setPhoneNo("0987655");
        customerReq.setEmail("oan@gmail.com");
        customerReq.setAge(25);
        return customerReq;
    }

    public static Customer getReturnNewCustomer(){
        Customer customerReturn = new Customer();
        customerReturn.setId(1L);
        customerReturn.setFirstname("test");
        customerReturn.setLastname("Mama");
        customerReturn.setPhoneNo("3242342");
        customerReturn.setEmail("aa@gmail.com");
        customerReturn.setAge(23);

        return customerReturn;
    }

    public static Customer getRepUpdateCustomer(){
        Customer reqCustomer = new Customer();
        reqCustomer.setId(2L);
        reqCustomer.setFirstname("Noon");
        reqCustomer.setLastname("Bow");
        reqCustomer.setEmail("noon@gmail.com");
        reqCustomer.setPhoneNo("098888");
        reqCustomer.setAge(5);
        return reqCustomer;
    }

    public static Customer getOldCustomer(){
        Customer oldCustomer = new Customer();
        oldCustomer.setId(2L);
        oldCustomer.setFirstname("oldNoon");
        oldCustomer.setLastname("oldBow");
        oldCustomer.setEmail("old@gmail.com");
        oldCustomer.setPhoneNo("32434242");
        oldCustomer.setAge(20);

        return oldCustomer;
    }

    public static Customer getUpdateCustomerFail(){
        Customer reqCustomer = new Customer();
        reqCustomer.setId(1L);
        reqCustomer.setFirstname("Noon");
        reqCustomer.setLastname("Bow");
        reqCustomer.setEmail("noon@gmail.com");
        reqCustomer.setPhoneNo("098888");
//        reqCustomer.setAge(5);

        return reqCustomer;
    }


}
