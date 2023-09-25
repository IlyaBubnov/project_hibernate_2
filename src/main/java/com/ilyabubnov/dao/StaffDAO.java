package com.ilyabubnov.dao;

import com.ilyabubnov.enteties.Staff;
import org.hibernate.SessionFactory;

public class StaffDAO extends GenericDAO <Staff> {
    public StaffDAO(SessionFactory sessionFactory) {
        super(Staff.class, sessionFactory);
    }
}
