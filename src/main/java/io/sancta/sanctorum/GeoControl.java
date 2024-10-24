package io.sancta.sanctorum;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import io.sancta.sanctorum.dao.CityDAO;
import io.sancta.sanctorum.dao.CountryDAO;
import io.sancta.sanctorum.domain.City;
import io.sancta.sanctorum.domain.Country;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GeoControl {

    SessionFactory sessionFactory;
    CityDAO cityDAO;
    CountryDAO countryDAO;
    RedisClient redisClient;
    ObjectMapper mapper;

    public GeoControl() {
        sessionFactory = prepareRelationalDatabase();
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);

        redisClient = setupRedisClient();
        mapper = new ObjectMapper();
    }

    public void run() {
        List<City> allCities = fetchDate();
        System.out.println(allCities.size());
        shutdown();
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

    private List<City> fetchDate() {
        try (Session session = sessionFactory.getCurrentSession()) {
            List<City> allCities = new ArrayList<>();
            session.beginTransaction();
            List<Country> countries = countryDAO.getAll();

            int totalCount = cityDAO.getTotalCount();
            int step = 500;
            for (int i = 0; i < totalCount; i += step) {
                allCities.addAll(cityDAO.getItems(i, step));
            }

            session.getTransaction().commit();
            return allCities;
        }
    }
}
