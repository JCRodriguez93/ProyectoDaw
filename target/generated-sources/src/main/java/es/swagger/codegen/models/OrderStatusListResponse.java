package es.swagger.codegen.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import es.swagger.codegen.models.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OrderStatusListResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-06-11T20:45:41.200175600+02:00[Europe/Madrid]")


public class OrderStatusListResponse   {
  @JsonProperty("orderStatuses")
  @Valid
  private List<OrderStatus> orderStatuses = new ArrayList<OrderStatus>();

  public OrderStatusListResponse orderStatuses(List<OrderStatus> orderStatuses) {
    this.orderStatuses = orderStatuses;
    return this;
  }

  public OrderStatusListResponse addOrderStatusesItem(OrderStatus orderStatusesItem) {
    this.orderStatuses.add(orderStatusesItem);
    return this;
  }

  /**
   * Get orderStatuses
   * @return orderStatuses
   **/
  @Schema(required = true, description = "")
      @NotNull
    @Valid
    public List<OrderStatus> getOrderStatuses() {
    return orderStatuses;
  }

  public void setOrderStatuses(List<OrderStatus> orderStatuses) {
    this.orderStatuses = orderStatuses;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderStatusListResponse orderStatusListResponse = (OrderStatusListResponse) o;
    return Objects.equals(this.orderStatuses, orderStatusListResponse.orderStatuses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderStatuses);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderStatusListResponse {\n");
    
    sb.append("    orderStatuses: ").append(toIndentedString(orderStatuses)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
