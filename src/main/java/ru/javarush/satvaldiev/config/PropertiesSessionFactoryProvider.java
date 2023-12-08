package ru.javarush.satvaldiev.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.javarush.satvaldiev.entity.*;

public class PropertiesSessionFactoryProvider implements SessionFactoryProvider {
    @Override
    public SessionFactory getSessionFactory(){
        return new Configuration()
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Film.class)
                .addAnnotatedClass(FilmText.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Rental.class)
                .addAnnotatedClass(Staff.class)
                .addAnnotatedClass(Store.class)
                .buildSessionFactory();
    }
}
