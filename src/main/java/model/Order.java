package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@Data
public class Order {
    @NotEmpty
    private String salesOrderId;
    @NotEmpty
    private String customerId;
    private String status;
    private String timeStamp;

    public Order() {

    }

    public Order(String salesOrderId, String customerId, String status, String timeStamp) {
        this.salesOrderId = salesOrderId;
        this.customerId = customerId;
        this.status = status;
        this.timeStamp = timeStamp;
    }
}
