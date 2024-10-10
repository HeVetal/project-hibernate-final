package io.sancta.sanctorum.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Entity
@Table(schema = "world", name = "country")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
}
