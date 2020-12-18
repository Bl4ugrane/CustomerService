package com.ugachev.dao;

import com.ugachev.model.Customer;
import java.util.List;


public interface CustomerDAO {

    List<Customer> getCustomersByName(String name);

    List<Customer> getCustomersBySurname(String surname);

    Customer getCustomerById(Long id);

    void saveCustomer(Customer customer, Long id1, Long id2);

    void updateActualAddress(Long user_id, Long address_id);
}
