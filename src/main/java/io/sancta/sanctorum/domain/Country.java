package io.sancta.sanctorum.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(schema = "world", name = "country")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Country {
    @Id
    Integer id;

    String code;

    @Column(name = "code_2")
    String alternativeCode;

    String name;

    Continent continent;

    String region;

    @Column(name = "surface_area")
    BigDecimal surfaceArea;

    @Column(name = "indep_year")
    Short independenceYear;

    Integer population;

    @Column(name = "life_expectancy")
    BigDecimal lifeExpectancy;

    BigDecimal GNP;

    @Column(name = "gnpo_id")
    BigDecimal GNPOId;

    @Column(name = "local_name")
    String localName;

    @Column(name = "government_from")
    String governmentFrom;

    @Column(name = "head_of_state")
    String headOfState;

    @OneToOne
    @Column(name = "capital")
    City city;
}
