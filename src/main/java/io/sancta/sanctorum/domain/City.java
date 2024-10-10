package io.sancta.sanctorum.domain;

import jakarta.persistence.*;

@Entity
@Table(schema = "world", name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    @JoinColumn(name = "country_id")
    Country country;

    String district;

    Integer population;
}
