package com.ugachev.dao;

import com.ugachev.model.Address;
import com.ugachev.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;




@Repository
public class CustomerDAOImpl implements CustomerDAO {

    //Внедрение интерфейса соединения с БД
    private EntityManager entityManager;

    @Autowired
    public CustomerDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Customer> getCustomersByName(String name) {

        //Поиск клиентов по имени
        Query query = entityManager.createQuery("from Customer WHERE first_name=:first_name");
        query.setParameter("first_name",name);

        return query.getResultList();
    }

    @Override
    public List<Customer> getCustomersBySurname(String surname) {

        //Поиск клиентов по фамилии
        Query query = entityManager.createQuery("from Customer WHERE last_name=:last_name");
        query.setParameter("last_name",surname);

        return query.getResultList();
    }

    @Override
    public Customer getCustomerById(Long id) {

        //Поиск клиента по id
        Customer customer = entityManager.find(Customer.class,id);

        return customer;
    }

    @Override
    public void saveCustomer(Customer customer, Long id1, Long id2) {
        //Поиск по id фактического адреса и адреса регистрации
        Address registredAddress = entityManager.find(Address.class,id1);
        Address actualAddress = entityManager.find(Address.class,id2);


         //Добавление найденных адресов в объект клиента и его сохранение в БД
        customer.setRegistredAddress(registredAddress);
        customer.setActualAddress(actualAddress);
        Customer newCustomer = entityManager.merge(customer);

        //Получение id клиента из БД
        customer.setId(newCustomer.getId());
    }

    @Override
    public void updateActualAddress(Long user_id, Long address_id) {

        //Поиск по id фактического адреса и клиента
        Customer customer = entityManager.find(Customer.class,user_id);
        Address actualAddress = entityManager.find(Address.class,address_id);
        customer.setActualAddress(actualAddress);
        entityManager.merge(customer);
    }
}
