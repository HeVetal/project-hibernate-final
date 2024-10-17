package io.sancta.sanctorum;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import io.sancta.sanctorum.dao.CityDAO;
import io.sancta.sanctorum.dao.CountryDAO;
import org.hibernate.SessionFactory;

public class Main {

    private SessionFactory sessionFactory;
    private RedisClient redisClient;
    private ObjectMapper objectMapper;
    private CityDAO cityDAO;
    private CountryDAO countryDAO;


    public Main() {

    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}