package ru.javarush.satvaldiev.dao;

import org.hibernate.SessionFactory;
import ru.javarush.satvaldiev.entity.Category;

public class CategoryDAO extends GenericDAO<Category> {
    public CategoryDAO(SessionFactory sessionFactory) {
        super(Category.class, sessionFactory);
    }
}
