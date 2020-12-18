package com.ugachev.service;

import com.ugachev.model.Customer;
import java.util.List;



public interface CustomerService {

    List<Customer> getCustomersByName(String name);

    List<Customer> getCustomersBySurname(String surname);

    Customer getCustomerById(Long id);

    void updateActualAddress(Long user_id, Long address_id);

    void saveCustomer(Customer customer, Long id1, Long id2);

}
