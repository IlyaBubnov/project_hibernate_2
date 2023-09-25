package com.ilyabubnov.dao;

import com.ilyabubnov.enteties.Inventory;
import org.hibernate.SessionFactory;

public class InventoryDAO extends GenericDAO <Inventory> {
    public InventoryDAO(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }
}
