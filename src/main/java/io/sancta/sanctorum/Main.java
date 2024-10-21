package io.sancta.sanctorum;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import io.sancta.sanctorum.dao.CityDAO;
import io.sancta.sanctorum.dao.CountryDAO;
import io.sancta.sanctorum.domain.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

    private SessionFactory sessionFactory;
    private CityDAO cityDAO;
    private CountryDAO countryDAO;
    private RedisClient redisClient;
    private ObjectMapper mapper;


    public Main() {
        sessionFactory = prepareRelationalDatabase();
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);

        redisClient = setupRedisClient();
        mapper = new ObjectMapper();
    }

    public static void main(String[] args) {

        Main main = new Main();
        List<City> allCities = main.fetchDate(main);
        System.out.println(allCities.size());
        main.shutdown();
    }

    private SessionFactory prepareRelationalDatabase() {
//        Configuration configuration = new Configuration();
//        Configuration configure = configuration.configure();
//        SessionFactory sessionFactory = configure.buildSessionFactory();
//        return sessionFactory;
        return new Configuration().configure().buildSessionFactory();
    }

    private RedisClient setupRedisClient() {
        return RedisClient.create();
    }

    private void shutdown() {
        if (Objects.nonNull(sessionFactory)) sessionFactory.close();

        if (Objects.nonNull(redisClient)) redisClient.shutdown();
    }

    private List<City> fetchDate(Main main) {
        try (Session session = main.sessionFactory.getCurrentSession()) {
            List<City> allCities = new ArrayList<>();
            session.beginTransaction();

            int totalCount = main.cityDAO.getTotalCount();
            int step = 500;
            for (int i = 0; i < totalCount; i += step) {
                allCities.addAll(main.cityDAO.getItems(i, step));
            }

            session.getTransaction().commit();
            return allCities;
        }
    }
}
