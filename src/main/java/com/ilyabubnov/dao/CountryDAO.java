package com.ilyabubnov.dao;

import com.ilyabubnov.enteties.Country;
import org.hibernate.SessionFactory;

public class CountryDAO extends GenericDAO <Country> {
    public CountryDAO(SessionFactory sessionFactory) {
        super(Country.class, sessionFactory);
    }
}
