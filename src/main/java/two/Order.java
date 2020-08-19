package two;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class Order {
    @NotEmpty
    private String salesOrderId;
    private String customerId;
    @NotEmpty
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

    public boolean isFutureOrder() {
        return isFutureOrder(this);
    }

    private boolean isFutureOrder(Order order) {
        LocalDateTime orderTime = LocalDateTime.parse(order.getTimeStamp());
        return orderTime.isAfter(LocalDateTime.now());
    }
}
