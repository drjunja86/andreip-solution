package com.andreip;

import com.andreip.model.ArrayOfOrders;
import com.andreip.model.Order;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderApiDelegateImpl service;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void whenValidRequest_thenOrdersShouldBeFound() throws Exception {
        ArrayOfOrders arrayOfOrders = getArrayOfOrders();

        given(service.getOrders(anyInt(), anyInt())).willReturn(ResponseEntity.ok(arrayOfOrders));

        ResultActions resultActions = mvc.perform(get("/v1/order").contentType(MediaType.APPLICATION_JSON)
                .content(toJson(arrayOfOrders)));

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalItems", is(2)))
                .andExpect(jsonPath("$.page", is(1)))
                .andExpect(jsonPath("$.itemsPerPage", is(10)));

        // verify that method called exactly 1 time
        verify(service, VerificationModeFactory.times(1))
                .getOrders(Mockito.any(), Mockito.any());
        reset(service);
    }

    private ArrayOfOrders getArrayOfOrders() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setTotal(BigDecimal.valueOf(10.00));

        Order order2 = new Order();
        order2.setId(2L);
        order2.setTotal(BigDecimal.valueOf(50.00));

        ArrayOfOrders arrayOfOrders = new ArrayOfOrders();
        arrayOfOrders.setItems(Arrays.asList(order1, order2));
        arrayOfOrders.setPage(1);
        arrayOfOrders.setItemsPerPage(10);
        arrayOfOrders.setTotalItems(2);
        arrayOfOrders.setTotalPages(1);
        return arrayOfOrders;
    }

    static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
