package com.ilyabubnov;

import com.ilyabubnov.dao.*;
import com.ilyabubnov.enteties.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Main {

    private final SessionFactory sessionFactory;

    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    public Main() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/movie");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "Qq377417");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");

        sessionFactory = new Configuration()
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
                .addProperties(properties)
                .buildSessionFactory();

        actorDAO = new ActorDAO(sessionFactory);
        addressDAO = new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        filmDAO = new FilmDAO(sessionFactory);
        filmTextDAO = new FilmTextDAO(sessionFactory);
        inventoryDAO = new InventoryDAO(sessionFactory);
        languageDAO = new LanguageDAO(sessionFactory);
        paymentDAO = new PaymentDAO(sessionFactory);
        rentalDAO = new RentalDAO(sessionFactory);
        staffDAO = new StaffDAO(sessionFactory);
        storeDAO = new StoreDAO(sessionFactory);
    }

    public static void main(String[] args) {

        Main main = new Main();

        Customer customer = main.createCustomer();
        main.customerReturnInventoryToTheStore();
        main.customerRentInventory(customer);
        main.newFilmCreated();

    }

    private Customer createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            Store store = storeDAO.getItems(0,1).get(0);
            City city = cityDAO.getByName("Manchester");

            Address address = new Address();
            address.setAddress("Sir Matt Busby Way, Old Trafford");
            address.setCity(city);
            address.setDistrict("Stretford");
            address.setPhone("441616767770");
            addressDAO.save(address);

            Customer customer = new Customer();
            customer.setAddress(address);
            customer.setActive(true);
            customer.setEmail("ticketingandmembershipservices@manutd.co.uk");
            customer.setFirstName("Manchester");
            customer.setLastName("United");
            customer.setStore(store);
            customerDAO.save(customer);

            session.getTransaction().commit();

            return customer;
        }
    }

    private void customerReturnInventoryToTheStore() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Rental rental = rentalDAO.getUnreturnedRental();
            rental.setReturnDate(LocalDateTime.now());

            rentalDAO.save(rental);

            session.getTransaction().commit();
        }
    }

    private void customerRentInventory(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            Film film = filmDAO.getFirstFilmForRent();
            Store store = storeDAO.getItems(0,1).get(0);

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
            payment.setCustomer(customer);
            payment.setStaff(staff);
            payment.setAmount(BigDecimal.valueOf(13.13));
            payment.setPaymentDate(LocalDateTime.now());
            payment.setRental(rental);
            paymentDAO.save(payment);

            session.getTransaction().commit();
        }
    }

    private void newFilmCreated() {
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            Language language = languageDAO.getItems(0,20)
                                           .stream()
                                           .unordered()
                                           .findAny()
                                           .get();

            List<Category> categories = categoryDAO.getItems(0,5);

            List<Actor> actors = actorDAO.getItems(0,20);

            Film film = new Film();
            film.setActors(new HashSet<>(actors));
            film.setRating(Rating.NC17);
            film.setSpecialFeatures(Set.of(Feature.TRAILERS, Feature.COMMENTARIES));
            film.setLength((short) 123);
            film.setReplacementCost(BigDecimal.TEN);
            film.setRentalRate(BigDecimal.ZERO);
            film.setLanguage(language);
            film.setDescription("new action movie");
            film.setTitle("Action movie");
            film.setRentalDuration((byte) 55);
            film.setOriginalLanguage(language);
            film.setCategories(new HashSet<>(categories));
            film.setYear(Year.now());
            filmDAO.save(film);

            FilmText filmText = new FilmText();
            filmText.setId(film.getId());
            filmText.setFilm(film);
            filmText.setDescription("new action movie");
            filmText.setTitle("Action movie");
            filmTextDAO.save(filmText);

            session.getTransaction().commit();
        }
    }
}
