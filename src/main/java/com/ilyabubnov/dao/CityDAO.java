package com.ilyabubnov.dao;

import com.ilyabubnov.enteties.City;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;

public class CityDAO extends GenericDAO <City> {
    public CityDAO(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getByName (String name) {
        Query <City> query = getCurrentSession()
                .createQuery("select c from City c where c.city = :NAME", City.class);
        query.setParameter("NAME", name);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
