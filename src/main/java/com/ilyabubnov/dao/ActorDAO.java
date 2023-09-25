package com.ilyabubnov.dao;

import com.ilyabubnov.enteties.Actor;
import org.hibernate.SessionFactory;

public class ActorDAO extends GenericDAO <Actor> {
    public ActorDAO(SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }
}
