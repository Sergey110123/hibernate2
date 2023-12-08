package ru.javarush.satvaldiev.dao;

import org.hibernate.SessionFactory;
import ru.javarush.satvaldiev.entity.Country;

public class CountryDAO extends GenericDAO<Country>{
    public CountryDAO(SessionFactory sessionFactory) {
        super(Country.class, sessionFactory);
    }
}
