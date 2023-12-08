package ru.javarush.satvaldiev;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.javarush.satvaldiev.config.PropertiesSessionFactoryProvider;
import ru.javarush.satvaldiev.config.SessionFactoryProvider;
import ru.javarush.satvaldiev.dao.*;
import ru.javarush.satvaldiev.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    SessionFactoryProvider provider = new PropertiesSessionFactoryProvider();
    SessionFactory sessionFactory = provider.getSessionFactory();
    ActorDAO actorDAO = new ActorDAO(sessionFactory);
    AddressDAO addressDAO = new AddressDAO(sessionFactory);
    CategoryDAO categoryDAO = new CategoryDAO(sessionFactory);
    CityDAO cityDAO = new CityDAO(sessionFactory);
    CountryDAO countryDAO = new CountryDAO(sessionFactory);
    CustomerDAO customerDAO = new CustomerDAO(sessionFactory);
    FilmDAO filmDAO = new FilmDAO(sessionFactory);
    FilmTextDAO filmTextDAO = new FilmTextDAO(sessionFactory);
    InventoryDAO inventoryDAO = new InventoryDAO(sessionFactory);
    LanguageDAO languageDAO = new LanguageDAO(sessionFactory);
    PaymentDAO paymentDAO = new PaymentDAO(sessionFactory);
    RentalDAO rentalDAO = new RentalDAO(sessionFactory);
    StaffDAO staffDAO = new StaffDAO(sessionFactory);
    StoreDAO storeDAO = new StoreDAO(sessionFactory);
    public static void main(String[] args) {
        Main main = new Main();

        Customer customer = main.createCustomer();
        main.customerReturnInventoryToStore();
        main.customerRentInventory(customer);
        main.newFilmWasMade();
    }

    private void newFilmWasMade() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Language language = languageDAO.getItems(0, 20).stream()
                    .unordered().findAny().get();
            List<Category> categories = categoryDAO.getItems(0, 5);
            List<Actor> actors = actorDAO.getItems(0, 15);

            Film film = new Film();
            film.setActors(new HashSet<>(actors));
            film.setRating(Rating.NC17);
            film.setSpecialFeatures(Set.of(SpecialFeature.TRAILERS, SpecialFeature.COMMENTARIES));
            film.setLength((short) 115);
            film.setReplacementCost(BigDecimal.valueOf(10));
            film.setRentalRate(BigDecimal.ZERO);
            film.setDescription("Похождение музыкальной группы");
            film.setTitle("Бременские музыканты");
            film.setRentalDuration((byte) 7);
            film.setOriginalLanguage(language);
            film.setLanguage(language);
            film.setCategories(new HashSet<>(categories));
            film.setYear(Year.of(2023));
            filmDAO.save(film);

            FilmText filmText = new FilmText();
            filmText.setFilm(film);
            filmText.setFilmId(film.getFilmId());
            filmText.setDescription("Похождение музыкальной группы");
            filmText.setTitle("Бременские музыканты");
            filmTextDAO.save(filmText);

            session.getTransaction().commit();
        }
    }

    private void customerRentInventory(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Film film = filmDAO.getFirstAvailableFilmForRent();
            Store store = storeDAO.getItems(0, 1).get(0);

            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            inventory.setStore(store);
            inventoryDAO.save(inventory);

            Staff staff = store.getStaff();

            Rental rental = new Rental();
            rental.setRentalDate(LocalDateTime.now());
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setStaff(staff);
            rentalDAO.save(rental);

            Payment payment = new Payment();
            payment.setRental(rental);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setCustomer(customer);
            payment.setAmount(BigDecimal.valueOf(55.47));
            payment.setStaff(staff);
            paymentDAO.save(payment);

            session.getTransaction().commit();
        }
    }

    private void customerReturnInventoryToStore() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Rental rental = rentalDAO.getAnyUnreturnedRental();
            rental.setReturnDate(LocalDateTime.now());
            rentalDAO.save(rental);
            session.getTransaction().commit();
        }
    }

    private Customer createCustomer() {
         try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Store store = storeDAO.getItems(0, 1).get(0);

            City city = cityDAO.getByName("Moscow");

            Address address = new Address();
            address.setAddress("Prospekt Mira, 104");
            address.setDistrict("Alekseevskiy");
            address.setCity(city);
            address.setPhone("495-111-11-11");
            addressDAO.save(address);

            Customer customer = new Customer();
            customer.setStore(store);
            customer.setFirstName("Ivan");
            customer.setLastName("Ivanov");
            customer.setEmail("ivan@mail.ru");
            customer.setAddress(address);
            customer.setActive(true);
            customerDAO.save(customer);

            session.getTransaction().commit();
            return customer;
        }
    }
}