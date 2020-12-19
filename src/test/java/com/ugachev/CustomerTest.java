package com.ugachev;


import com.ugachev.controller.CustomerController;
import com.ugachev.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService service;


    //Проверка создания нового клиента
    @Test
    public void createTest() throws Exception {


        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"first_name\":\"Mikhail\",\"last_name\":\"Vasiliev\"," +
                        "\"middle_name\":\"Sergeevich\", \"sex\":\"male\", \"actual_address_id\":1, " +
                        "\"registred_address_id\":2}");

        MvcResult result = mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.first_name", is("Mikhail")))
                .andExpect(jsonPath("$.last_name", is("Vasiliev")))
                .andExpect(jsonPath("$.sex", is("male")))
                .andReturn();
    }

    //Проверка создания нового клиента, не добавив в теле запроса обязательное поле
    @Test
    public void validationForActualAddressFieldTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"first_name\":\"Mikhail\",\"last_name\":\"Vasiliev\"," +
                        "\"middle_name\":\"Sergeevich\", \"sex\":\"male\"}");

        MvcResult result = mvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(jsonPath("$.message", is("Field 'actual_address_id' cannot be blank")))
                .andReturn();
    }

    @Test
    public void validationForRegistredAddressFieldTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"first_name\":\"Mikhail\",\"last_name\":\"Vasiliev\"," +
                        "\"middle_name\":\"Sergeevich\", \"sex\":\"male\", \"actual_address_id\":1}");

        MvcResult result = mvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(jsonPath("$.message",
                        is("Field 'registred_address_id' cannot be blank")))
                .andReturn();
    }

    @Test
    public void validationForSexFieldTest() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"first_name\":\"Mikhail\",\"last_name\":\"Vasiliev\"," +
                        "\"middle_name\":\"Sergeevich\"}");

        MvcResult result = mvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(jsonPath("$.message", is("Field 'sex' cannot be blank")))
                .andReturn();
    }

    //Проверка изменения адреса у клиента, с несуществующем id
    @Test
    public void updateActualAddressWithIncorrectIdTest() throws Exception {

        long id = 100;

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/api/v1/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getArticleInJson(1));

        this.mvc.perform(builder)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("Customer not found with id " + id)))
                .andDo(print());
    }

    private String getArticleInJson(long id) {
        return "{\"actual_address_id\":" + id + "}";
    }

}
