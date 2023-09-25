package com.ilyabubnov.dao;

import com.ilyabubnov.enteties.Category;
import org.hibernate.SessionFactory;

public class CategoryDAO extends GenericDAO <Category> {
    public CategoryDAO(SessionFactory sessionFactory) {
        super(Category.class, sessionFactory);
    }
}
