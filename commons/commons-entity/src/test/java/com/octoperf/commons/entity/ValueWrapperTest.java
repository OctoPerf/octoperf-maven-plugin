package com.octoperf.commons.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

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
    EqualsVerifier.forClass(entity().getClass()).verify();
  }

  @Override
  protected ValueWrapper<String> entity() {
    return newInstance();
  }

  @Override
  protected TypeReference<ValueWrapper<String>> typeReference() {
    return new TypeReference<ValueWrapper<String>>(){};
  }

}
