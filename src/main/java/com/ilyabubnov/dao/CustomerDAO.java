package com.ilyabubnov.dao;

import com.ilyabubnov.enteties.Customer;
import org.hibernate.SessionFactory;

public class CustomerDAO extends GenericDAO <Customer> {
    public CustomerDAO(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }
}
