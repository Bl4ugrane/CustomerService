package com.ugachev;


import com.ugachev.controller.CustomerController;
import com.ugachev.model.Customer;
import com.ugachev.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerSearchTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService service;

    //Проверка поиска клиента по имени
    @Test
    public void getCustomersByNameTest() throws Exception {

        Customer customer = new Customer(1L,"Dmitriy",
                "Ugachev", "Evgenievich", "male");

        String name = "Dmitriy";

        List<Customer> allCustomers = Arrays.asList(customer);

        given(service.getCustomersByName(name)).willReturn(allCustomers
                .stream().filter( c -> c.getFirst_name().equals(name)).collect(Collectors.toList()));

        mvc.perform(get("/api/v1/customers").param("name",name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) status().isOk()).andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].first_name", is(customer.getFirst_name())))
                .andExpect(jsonPath("$[0].last_name", is(customer.getLast_name())))
                .andExpect(jsonPath("$[0].middle_name", is(customer.getMiddle_name())));

    }

    //Проверка поиска клиента по фамилии
    @Test
    public void getCustomersBySurnameTest() throws Exception {

        Customer customer1 = new Customer(1L,"Anton",
                "Smirnov", "Anatolievich", "male");

        Customer customer2 = new Customer(2L,"Sergey",
                "Smirnov", "Alexeyevich", "male");

        Customer custome3 = new Customer(3L,"Karina",
                "Pavlova", "Evgenievna", "female");

        String surname = "Smirnov";

        List<Customer> allCustomers = Arrays.asList(customer1,customer2,custome3);

       given(service.getCustomersBySurname(surname)).willReturn(allCustomers
                .stream().filter( c -> c.getLast_name().equals(surname)).collect(Collectors.toList()));;

        mvc.perform(get("/api/v1/customers").param("surname", surname)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) status().isOk()).andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].first_name", is(customer1.getFirst_name())))
                .andExpect(jsonPath("$[1].first_name", is(customer2.getFirst_name())))
                .andExpect(jsonPath("$[0].last_name", is(surname)))
                .andExpect(jsonPath("$[1].last_name", is(surname)));

    }

    //Проверка поиска несуществующего клиента по имени
    @Test
    public void getCustomersWithIncorrectInputDataTest() throws Exception {

        Customer customer1 = new Customer(1L,"Anton",
                "Smirnov", "Anatolievich", "male");

        Customer customer2 = new Customer(2L,"Sergey",
                "Smirnov", "Alexeyevich", "male");

        Customer custome3 = new Customer(3L,"Karina",
                "Pavlova", "Evgenievna", "female");

        String name = "Dmitriy";

        List<Customer> allCustomers = Arrays.asList(customer1, customer2, custome3);

        given(service.getCustomersByName(name)).willReturn(allCustomers
                .stream().filter( c -> c.getFirst_name().equals(name)).collect(Collectors.toList()));

        mvc.perform(get("/api/v1/customers").param("name",name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) status().isNotFound()).andDo(print())
                .andExpect(jsonPath("$.message", is("Customer not found")));

    }

    //Проверка поиска клиента, задав в строке поиска некорректный параметр
    @Test
    public void getCustomersWithIncorrectParamTest() throws Exception {

        Customer customer1 = new Customer(1L,"Anton",
                "Smirnov", "Anatolievich", "male");

        Customer customer2 = new Customer(2L,"Sergey",
                "Smirnov", "Alexeyevich", "male");

        Customer custome3 = new Customer(3L,"Karina",
                "Pavlova", "Evgenievna", "female");

        String name = "Dmitriy";

        List<Customer> allCustomers = Arrays.asList(customer1, customer2, custome3);

        given(service.getCustomersByName(name)).willReturn(allCustomers
                .stream().filter( c -> c.getFirst_name().equals(name)).collect(Collectors.toList()));

        mvc.perform(get("/api/v1/customers").param("test",name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) status().isNotFound()).andDo(print())
                .andExpect(jsonPath("$.message", is("Customer not found")));

    }

}
