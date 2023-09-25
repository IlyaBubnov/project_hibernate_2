package com.ilyabubnov.dao;

import com.ilyabubnov.enteties.Store;
import org.hibernate.SessionFactory;

public class StoreDAO extends GenericDAO <Store> {
    public StoreDAO(SessionFactory sessionFactory) {
        super(Store.class, sessionFactory);
    }
}
