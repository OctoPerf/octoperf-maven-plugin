package com.octoperf.tools.jackson.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JacksonConfig.class, TestRegistrator.class})
public class JsonMapperServiceTest {

  @Autowired
  private JsonMapperService mapper;

  private Bean bean;
  private BeanInterval beanInterval;
  private BeanInstant beanInstant;
  private static final String JSON_BEAN = "{\"fields\":[\"field1\",\"field2\"],\"id\":\"id\"}";
  private static final String JSON_BEAN_INTERVAL =
      "{\"id\":\"id\",\"interval\":\"1409758020000-1409761620000\"}";
  private static final String JSON_BEAN_INSTANT = "{\"instant\":1409758020.000000000}";
  private static final String JSON_ARRAY = "[\"userId\",\"benchResultId\",1409758020.000000000]";
  private static final Instant TIMESTAMP = Instant.from(LocalDateTime.of(2014, 9, 03, 15, 27).atOffset(ZoneOffset.UTC));
  
  @BeforeEach
  public void before() {
    bean = Bean.builder().id(Optional.of("id")).fields(Arrays.asList("field1", "field2")).build();
    beanInterval =
        BeanInterval
            .builder()
            .id("id")
            .interval(
                new Interval(new DateTime(2014, 9, 03, 15, 27, DateTimeZone.UTC), new DateTime(
                    2014, 9, 03, 16, 27, DateTimeZone.UTC))).build();
    beanInstant = BeanInstant.builder().instant(TIMESTAMP).build();
  }

  @Test
  public void shouldIncludeEmptyCollections() throws IOException {
    final TestDTO dto = new TestDTO(ImmutableSet.<String>of(), TestDTOType.A);
    final String json = mapper.toJson(dto);
    final TestDTO fromJson = mapper.fromJson(json, TestDTO.class);
    assertEquals(dto, fromJson);
  }

  @Test
  public void shouldMapBeanToJson() throws IOException {
    // nullField must not be written
    // fields list must be written as a JSONArray
    assertEquals(JSON_BEAN, mapper.toJson(bean));
  }

  @Test
  public void shouldMapBeanToJson2() throws IOException {
    // nullField must not be written
    // fields list must be written as a JSONArray
    assertEquals(JSON_BEAN, mapper.toJson(bean, new TypeReference<Bean>() {}));
  }

  @Test
  public void shouldMapBeanIntervalToJson() throws IOException {
    // interval must be formated druid style
    assertEquals(JSON_BEAN_INTERVAL, mapper.toJson(beanInterval));
  }

  @Test
  public void shouldMapBeanIntervalToPrettyJson() throws IOException {
    final String prettyJson = mapper.toPrettyJson(beanInterval);
    final BeanInterval deserialized = mapper.fromJson(prettyJson, BeanInterval.class);
    assertEquals(beanInterval, deserialized);
  }

  @Test
  public void shouldMapJsonToBean() throws IOException {
    assertEquals(bean, mapper.fromJson(JSON_BEAN, Bean.class));
  }

  @Test
  public void shouldMapJsonToBean3() throws IOException {
    assertEquals(bean, mapper.fromJson(JSON_BEAN, new TypeReference<Bean>() {}));
  }

  @Test
  public void shouldThrowJsonParseException() {
    assertThrows(
      JsonParseException.class,
      () -> mapper.fromJson("cannot be parsed", Bean.class)
    );
  }
  
  @Test
  public void shouldMapJsonToBeanInstant() throws IOException {
    assertEquals(beanInstant, mapper.fromJson(JSON_BEAN_INSTANT, BeanInstant.class));
  }
  
  @Test
  public void shouldMapBeanInstantToJson() throws IOException {
    assertEquals(JSON_BEAN_INSTANT, mapper.toJson(beanInstant));
  }
  
  @Test
  public void shouldMapArrayToJson() throws IOException {
    assertEquals(JSON_ARRAY, mapper.toJson(new Object[]{"userId", "benchResultId", TIMESTAMP }));
  }

  @Data
  @Builder
  private static final class Bean {
    private final Optional<String> id;
    private final Integer nullField;
    private final List<String> fields;

    @JsonCreator
    public Bean(@JsonProperty("id") final Optional<String> id,
        @JsonProperty("nullField") final Integer nullField,
        @JsonProperty("fields") final List<String> fields) {
      super();
      this.id = checkNotNull(id);
      this.nullField = nullField;
      this.fields = checkNotNull(fields);
    }
  }

  @Data
  @Builder
  private static final class BeanInterval {
    private final String id;
    private final Interval interval;

    @JsonCreator
    public BeanInterval(@JsonProperty("id") final String id,
        @JsonProperty("interval") final Interval interval) {
      super();
      this.id = checkNotNull(id);
      this.interval = checkNotNull(interval);
    }
  }
  
  @Data
  @Builder
  private static final class BeanInstant {
    private final Instant instant;

    @JsonCreator
    public BeanInstant(@JsonProperty("instant") final Instant instant) {
      super();
      this.instant = checkNotNull(instant);
    }
  }
  
}
