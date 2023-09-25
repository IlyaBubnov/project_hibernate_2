package com.ilyabubnov.dao;

import com.ilyabubnov.enteties.FilmText;
import org.hibernate.SessionFactory;

public class FilmTextDAO extends GenericDAO <FilmText> {
    public FilmTextDAO(SessionFactory sessionFactory) {
        super(FilmText.class, sessionFactory);
    }
}
