package com.ugachev.controller;


import com.ugachev.exception_handling.CustomerNotFoundException;
import com.ugachev.exception_handling.NotFoundFieldException;
import com.ugachev.model.Customer;
import com.ugachev.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomersByName(@RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "surname", required = false) String surname) {

        List<Customer> customerList = null;

        // Поиск по имени и фамилии
        if (name != null && !name.isEmpty() && surname != null && !surname.isEmpty()) {
            customerList = customerService.getCustomersByName(name).
                    stream().filter(c -> c.getLast_name().equals(surname)).collect(Collectors.toList());
        // Поиск по имени
        } else if (name != null && !name.isEmpty()) {
            customerList = customerService.getCustomersByName(name);
        // Поиск по фамилии
        } else if (surname != null && !surname.isEmpty()) {
            customerList = customerService.getCustomersBySurname(surname);
        }

        //Если совпадений не найдено, выбросить исключение
        if (customerList == null || customerList.isEmpty())
            throw new CustomerNotFoundException("Customer not found");

        return customerList;
    }

    @PostMapping
    public Customer createNewCustomer(@RequestBody Map<String,String> map) throws Exception {

        //Создание нового обьекта и добавление в его поля значения из map через сеттеры
        Customer customer = new Customer();
        customer.setFirst_name(map.get("first_name"));
        customer.setLast_name(map.get("last_name"));
        customer.setMiddle_name(map.get("middle_name"));
        customer.setSex(map.get("sex"));

        //Валидация обязательных полей в теле запроса
        if (customer.getSex() == null || customer.getSex().isEmpty())
            throw new Exception("Field 'sex' cannot be blank");

        //Если одно из полей не указан в теле запроса, выбросить исключение
        if (!map.containsKey("actual_address_id")) {
            throw new NotFoundFieldException("Field 'actual_address_id' cannot be blank");
        } else if (!map.containsKey("registred_address_id")) {
            throw new NotFoundFieldException("Field 'registred_address_id' cannot be blank");
        }

        //Парсинг строки в число и передача id-ов в метод для создания нового клиента
        long registred_address_id = Long.parseLong(map.get("registred_address_id"));
        long actual_address_id = Long.parseLong(map.get("actual_address_id"));

        customerService.saveCustomer(customer, registred_address_id,actual_address_id);

        return customer;

    }

    @PutMapping("/{id}")
    public Customer updateActualAddress(@PathVariable("id") Long user_id,
                                        @RequestBody Map<String,Long> map) {

        //Поиск клиента для которого произведен запрос
        Customer customer = customerService.getCustomerById(user_id);

        //Если совпадений не найдено, выбросить исключение
        if (customer == null)
            throw new CustomerNotFoundException("Customer not found with id " + user_id);

        if (!map.containsKey("actual_address_id"))
            throw new NotFoundFieldException("Field 'actual_address_id' are required");

        customerService.updateActualAddress(user_id,map.get("actual_address_id"));

        return customer;

    }
}
