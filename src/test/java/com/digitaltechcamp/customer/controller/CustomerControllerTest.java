package com.digitaltechcamp.customer.controller;

import com.digitaltechcamp.customer.model.Customer;
import com.digitaltechcamp.customer.repositaries.CustomerRepository;
import com.digitaltechcamp.customer.service.CustomerService;
import com.digitaltechcamp.customer.support.CustomerSupportTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mvc;
    public static final String urlCustomerList = "/customer/list/";
    public static  final  String urlCustomer = "/customer/";
    public static final String urlCutomerName = "/customer/list/name/";
//    public static final String urlCutomerNameNotFound = "/customer/list/name/";

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService);
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @DisplayName("Test get customer should return customer list")
    @Test
    void testGetCustomerList() throws Exception {
        when(customerService.getCustomerList()).thenReturn(CustomerSupportTest.getCustomerList());

        MvcResult mvcResult = mvc.perform(get(urlCustomerList)).andExpect(status().isOk()).andExpect(content().contentType((MediaType.APPLICATION_JSON_UTF8)))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals("1", jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("Ryan", jsonArray.getJSONObject(0).get("firstname").toString());
        assertEquals("giggs", jsonArray.getJSONObject(0).get("lastname").toString());
        assertEquals("233444555", jsonArray.getJSONObject(0).get("phoneNo").toString());
        assertEquals("gig@mail.com", jsonArray.getJSONObject(0).get("email").toString());
        assertEquals(23, jsonArray.getJSONObject(0).get("age"));

        assertEquals("2", jsonArray.getJSONObject(1).get("id").toString());
        assertEquals("David", jsonArray.getJSONObject(1).get("firstname").toString());
        assertEquals("Backham", jsonArray.getJSONObject(1).get("lastname").toString());
        assertEquals("asdsad", jsonArray.getJSONObject(1).get("phoneNo").toString());
        assertEquals("david@mail.com", jsonArray.getJSONObject(1).get("email").toString());
        assertEquals(40, jsonArray.getJSONObject(1).get("age"));

    }

    @DisplayName("Test get Customer By ID should return Customer")
    @Test
    void testGetCustomerById() throws Exception {
        Long reqId = 1L;
        when(customerService.getCustomerById(reqId)).thenReturn(CustomerSupportTest.getOldCustomer());

        MvcResult mvcResult = mvc.perform(get(urlCustomer  + reqId)).andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON_UTF8)))
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
//        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals("2", jsonObject.get("id").toString());
        assertEquals("oldNoon", jsonObject.get("firstname").toString());
        assertEquals("oldBow", jsonObject.get("lastname").toString());
        assertEquals("old@gmail.com", jsonObject.get("email").toString());
        assertEquals("32434242", jsonObject.get("phoneNo").toString());
        assertEquals(20, jsonObject.get("age"));
    }
    @DisplayName("Test get Customer By Name should return Customer")
    @Test
    void testGetCustomerByName() throws Exception {
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


        when(customerService.getByCustomerFirstname(reqParam)).thenReturn(customerList);

        MvcResult mvcResult = mvc.perform(get(urlCutomerName  + reqParam)).andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON_UTF8)))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals("1", jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("tiger", jsonArray.getJSONObject(0).get("firstname").toString());
        assertEquals("Ldew", jsonArray.getJSONObject(0).get("lastname").toString());
        assertEquals("Dew.com", jsonArray.getJSONObject(0).get("email").toString());
        assertEquals("0985153944", jsonArray.getJSONObject(0).get("phoneNo").toString());
        assertEquals(22, jsonArray.getJSONObject(0).get("age"));
    }

    @DisplayName("Test get Customer By Name should return Customer")
    @Test
    void testGetCustomerByNameNotFound() throws Exception {
        String  reqParam = "tiger5";
        when(customerService.getByCustomerFirstname(reqParam)).thenReturn(null);
        MvcResult mvcResult = mvc.perform(get(urlCutomerName  + reqParam)).andExpect(status().isNotFound())
                .andReturn();
    }

    @DisplayName("Test get Customer By ID should return not found")
    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        Long reqId = 5L;
        MvcResult mvcResult = mvc.perform(get(urlCustomer + reqId)).andExpect(status().isNotFound())
                .andReturn();
    }


    @DisplayName("Test Create Customer should return Success")
    @Test
    void testCreateCustomer() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getReqNewCustomer();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //Convert Json to model view at Model
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.createCustomer(reqCustomer)).thenReturn(CustomerSupportTest.getReturnNewCustomer());

        MvcResult mvcResult = mvc.perform(post(urlCustomer).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isCreated()).andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("1", jsonObject.get("id").toString());
        assertEquals("test", jsonObject.get("firstname").toString());
        assertEquals("Mama", jsonObject.get("lastname").toString());
        assertEquals("3242342", jsonObject.get("phoneNo").toString());
        assertEquals("aa@gmail.com", jsonObject.get("email").toString());
        assertEquals(23, jsonObject.get("age"));
    }
    @DisplayName("Test Create Customer with first name is empty")
    @Test
    void testCreateCustomerWithNameIdEmpty() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getReqNewCustomer();
        reqCustomer.setFirstname("");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //Convert Json to model view at Model
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.createCustomer(reqCustomer)).thenReturn(CustomerSupportTest.getReturnNewCustomer());
        MvcResult mvcResult = mvc.perform(post(urlCustomer)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isBadRequest()).andReturn();
        assertEquals("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<?> com.digitaltechcamp.customer.controller.CustomerController.createCustomer(com.digitaltechcamp.customer.model.Customer): [Field error in object 'customer' on field 'firstname': rejected value []; codes [Size.customer.firstname,Size.firstname,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [customer.firstname,firstname]; arguments []; default message [firstname],100,1]; default message [Please type firstName]] ", mvcResult.getResolvedException().getMessage());

    }
    @DisplayName("Test Update Customer should return success")
    @Test
    void testUpdateCustomer() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getOldCustomer();
        Long reqId = 2L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //Convert Json to model view at Model
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId, reqCustomer)).thenReturn(CustomerSupportTest.getReturnNewCustomer());

        MvcResult mvcResult = mvc.perform(put(urlCustomer + "" + reqId )
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk()).andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("1", jsonObject.get("id").toString());
        assertEquals("test", jsonObject.get("firstname").toString());
        assertEquals("Mama", jsonObject.get("lastname").toString());
        assertEquals("3242342", jsonObject.get("phoneNo").toString());
        assertEquals("aa@gmail.com", jsonObject.get("email").toString());
        assertEquals(23, jsonObject.get("age"));
    }

    @DisplayName("Test Update Customer should return Not found")
    @Test
    void testUpdateCustomerNotFound() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getOldCustomer();
        Long reqId = 2L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //Convert Json to model view at Model
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId, reqCustomer)).thenReturn(null);

        MvcResult mvcResult = mvc.perform(put(urlCustomer + "" + reqId )
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isNotFound()).andReturn();

        verify(customerService, times(1)).updateCustomer(reqId, reqCustomer);
    }

    @DisplayName("Test Delete Customer should returnSuccess")
    @Test
    void testDeleteCustomer() throws Exception{
        Long reqId = 4L;
        when(customerService.deleteById(reqId)).thenReturn(true);
        MvcResult mvcResult = mvc.perform(delete(urlCustomer + "" + reqId )
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        verify(customerService, times(1)).deleteById(reqId);

    }

    @DisplayName("Test Delete Customer should returnSuccess")
    @Test
    void testDeleteCustomerShouldReturnNotFound() throws Exception{
        Long reqId = 4L;
        when(customerService.deleteById(reqId)).thenReturn(false);
        MvcResult mvcResult = mvc.perform(delete(urlCustomer + "" + reqId )
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();
        verify(customerService, times(1)).deleteById(reqId);

    }

}
