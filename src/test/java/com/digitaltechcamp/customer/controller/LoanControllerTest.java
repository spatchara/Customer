package com.digitaltechcamp.customer.controller;

import com.digitaltechcamp.customer.api.LoanApi;
import com.digitaltechcamp.customer.model.Customer;
import com.digitaltechcamp.customer.model.response.GetLoanInfoResponse;
import com.digitaltechcamp.customer.support.CustomerSupportTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanControllerTest {
    @Mock
    LoanApi loanApi;
    @InjectMocks
    LoanController loanController;

    private MockMvc mvc;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        loanController = new LoanController(loanApi);
        mvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }
    @DisplayName("Test Get loan info should return loan information")
    @Test
    void testGetLoanInfo() throws Exception {
        Long reqId = 1L;
            GetLoanInfoResponse getLoanInfoResponse = new GetLoanInfoResponse();
            getLoanInfoResponse.setId(1L);
            getLoanInfoResponse.setStatus("OK");
            getLoanInfoResponse.setAccountPayable("102-222-2200");
            getLoanInfoResponse.setAccountReceivable("102-333-2020");
            getLoanInfoResponse.setPrincipleAmount(3400000.0);


        when(loanApi.getLoanInfo(reqId)).thenReturn(getLoanInfoResponse);

        MvcResult mvcResult = mvc.perform(get("/loan/"  + reqId)).andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON_UTF8)))
                .andReturn();
        GetLoanInfoResponse resp = loanApi.getLoanInfo(reqId);
        assertEquals("1", resp.getId().toString());
        assertEquals("OK", resp.getStatus());
        assertEquals("102-222-2200", resp.getAccountPayable());
        assertEquals("102-333-2020", resp.getAccountReceivable());
        assertEquals(3400000.0, resp.getPrincipleAmount());

    }
}
