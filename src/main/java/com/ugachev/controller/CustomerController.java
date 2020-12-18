package com.ugachev.controller;


import com.ugachev.model.Customer;
import com.ugachev.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;




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
        if (name != null && !name.isEmpty()) {
            customerList = customerService.getCustomersByName(name);
        } else if (surname != null && !surname.isEmpty()) {
            customerList = customerService.getCustomersBySurname(surname);
        }

        return customerList;
    }

    @PostMapping
    public Customer createNewCustomer(@RequestBody Map<String,String> map) {

        //Создание нового обьекта клиента и добавление в его поля значения из map через сеттеры
        Customer customer = new Customer();
        customer.setFirst_name(map.get("first_name"));
        customer.setLast_name(map.get("last_name"));
        customer.setMiddle_name(map.get("middle_name"));
        customer.setSex(map.get("sex"));

        //Парсинг строки в число и передача id-ов в метод для создания нового клиента
        Long registred_address_id = Long.parseLong(map.get("registred_address_id"));
        Long actual_address_id = Long.parseLong(map.get("actual_address_id"));

        customerService.saveCustomer(customer, registred_address_id,actual_address_id);

        return customer;
    }

    @PutMapping("/{id}")
    public Customer updateActualAddress(@PathVariable("id") Long user_id,
                                        @RequestBody Map<String,Long> map) {

        customerService.updateActualAddress(user_id,map.get("actual_address_id"));

        //Поиск клиента для которого произведен запрос
        Customer customer = customerService.getCustomerById(user_id);

        return customer;

    }
}
