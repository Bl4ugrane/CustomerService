package com.ugachev.service;


import com.ugachev.dao.CustomerDAO;
import com.ugachev.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;




@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO customerDAO;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    @Transactional
    public List<Customer> getCustomersByName(String name) {
        return customerDAO.getCustomersByName(name);
    }

    @Override
    @Transactional
    public List<Customer> getCustomersBySurname(String surname) {

        return customerDAO.getCustomersBySurname(surname);
    }

    @Override
    @Transactional
    public Customer getCustomerById(Long id) {
        return customerDAO.getCustomerById(id);
    }

    @Override
    @Transactional
    public void updateActualAddress(Long user_id, Long address_id) {
        customerDAO.updateActualAddress(user_id, address_id);
    }

    @Override
    @Transactional
    public void saveCustomer(Customer customer, Long id1, Long id2) {
        customerDAO.saveCustomer(customer, id1,id2);
    }
}
