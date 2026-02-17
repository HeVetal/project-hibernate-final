package io.sancta.sanctorum.redis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Language {

    String language;

    Boolean isOfficial;

    BigDecimal percentage;

}
