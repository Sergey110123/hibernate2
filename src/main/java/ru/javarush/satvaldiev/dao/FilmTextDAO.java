package ru.javarush.satvaldiev.dao;

import org.hibernate.SessionFactory;
import ru.javarush.satvaldiev.entity.FilmText;

public class FilmTextDAO extends GenericDAO<FilmText> {
    public FilmTextDAO(SessionFactory sessionFactory) {
        super(FilmText.class, sessionFactory);
    }
}
