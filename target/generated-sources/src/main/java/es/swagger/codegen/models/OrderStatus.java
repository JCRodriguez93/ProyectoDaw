package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets OrderStatus
 */
public enum OrderStatus {
  PENDIENTE("PENDIENTE"),
    PAGADO("PAGADO"),
    CANCELADO("CANCELADO"),
    CONFIRMADO("CONFIRMADO");

  private String value;

  OrderStatus(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static OrderStatus fromValue(String text) {
    for (OrderStatus b : OrderStatus.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
