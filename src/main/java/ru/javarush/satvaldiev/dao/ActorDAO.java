package ru.javarush.satvaldiev.dao;

import org.hibernate.SessionFactory;
import ru.javarush.satvaldiev.entity.Actor;

public class ActorDAO extends GenericDAO<Actor>{
    public ActorDAO(SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }
}
