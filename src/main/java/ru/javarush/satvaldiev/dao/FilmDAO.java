package ru.javarush.satvaldiev.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.javarush.satvaldiev.entity.Film;

public class FilmDAO extends GenericDAO<Film>{
    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public Film getFirstAvailableFilmForRent() {
        Query<Film> query = getCurrentSession()
                .createQuery("select f from Film f " +
                        "where f.filmId not in (select distinct film.filmId from Inventory )", Film.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
