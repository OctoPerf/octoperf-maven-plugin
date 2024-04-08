package com.octoperf.commons.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;

import static nl.jqno.equalsverifier.EqualsVerifier.forClass;

/**
 * Tests {@link ValueWrapper}.
 * 
 * @author gpe
 *
 */
public class ValueWrapperTest extends AbstractJsonEntityTest<ValueWrapper<String>> {

  public static ValueWrapper<String> newInstance() {
    return new ValueWrapper<>("test");
  }

  @Test
  public void shouldPassEqualsVerifier() {
    forClass(entity().getClass()).verify();
  }

  @Override
  protected ValueWrapper<String> entity() {
    return newInstance();
  }

  @Override
  protected TypeReference<ValueWrapper<String>> typeReference() {
    return new TypeReference<>() {
    };
  }

}
