package ru.javarush.satvaldiev.dao;

import org.hibernate.SessionFactory;
import ru.javarush.satvaldiev.entity.Payment;

public class PaymentDAO extends GenericDAO<Payment> {
    public PaymentDAO(SessionFactory sessionFactory) {
        super(Payment.class, sessionFactory);
    }
}
