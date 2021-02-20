import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class OrderTest {

    @Test
    public void shouldGenerateOrderFromMessage() throws JsonProcessingException {
        String message = "[\n" +
                "  {\n" +
                "  \"salesOrderId\": \"123456\",\n" +
                "  \"customerId\": \"QWERASDF\",\n" +
                "  \"status\": \"SUCCESS\",\n" +
                "  \"timeStamp\": \"2019-08-21T11:05:03\"\n" +
                "  }]";

        ObjectMapper objectMapper = new ObjectMapper();
        Order[] orders = objectMapper.readValue(message, Order[].class);

        Order expectedOrders = new Order("123456", "QWERASDF", "SUCCESS", "2019-08-21T11:05:03");
        assertEquals(Arrays.asList(expectedOrders), Arrays.asList(orders));
    }

    @Test
    public void validationFailsWhenSalesOrderIdIsNull() throws JsonProcessingException {
        String message = "[\n" +
                "  {\n" +
                "  \"salesOrderId\": null,\n" +
                "  \"customerId\": \"QWERASDF\",\n" +
                "  \"status\": \"SUCCESS\",\n" +
                "  \"timeStamp\": \"2019-08-21T11:05:03\"\n" +
                "  }]";

        ObjectMapper objectMapper = new ObjectMapper();
        Order[] orders = objectMapper.readValue(message, Order[].class);
        List<Order> resultOrders = Arrays.asList(orders);
        boolean isValid = areValidOrders(resultOrders);

        assertFalse(isValid);
    }

    @Test
    public void validationFailsWhenSalesOrderIdIsEmpty() throws JsonProcessingException {
        String message = "[\n" +
                "  {\n" +
                "  \"salesOrderId\": \"\",\n" +
                "  \"customerId\": \"QWERASDF\",\n" +
                "  \"status\": \"SUCCESS\",\n" +
                "  \"timeStamp\": \"2019-08-21T11:05:03\"\n" +
                "  }]";

        ObjectMapper objectMapper = new ObjectMapper();
        Order[] orders = objectMapper.readValue(message, Order[].class);
        List<Order> resultOrders = Arrays.asList(orders);
        boolean isValid = areValidOrders(resultOrders);

        assertFalse(isValid);
    }

    @Test
    public void validationFailsWhenStatusIsNullOrStatusIsEmpty() throws JsonProcessingException {
        String message1 = "[\n" +
                "  {\n" +
                "  \"salesOrderId\": \"123456\",\n" +
                "  \"customerId\": \"QWERASDF\",\n" +
                "  \"status\": null,\n" +
                "  \"timeStamp\": \"2019-08-21T11:05:03\"\n" +
                "  }]";

        String message2 = "[\n" +
                "  {\n" +
                "  \"salesOrderId\": \"123456\",\n" +
                "  \"customerId\": \"QWERASDF\",\n" +
                "  \"status\": \"\",\n" +
                "  \"timeStamp\": \"2019-08-21T11:05:03\"\n" +
                "  }]";

//        ObjectMapper objectMapper = new ObjectMapper();
//        Order[] orders = objectMapper.readValue(message1, Order[].class);
//        List<Order> resultOrders = Arrays.asList(orders);
//        boolean isValid = areValidOrders(resultOrders);
//
//        assertFalse(isValid);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        Order[] orders = objectMapper.readValue(message1, Order[].class);
//        List<Order> resultOrders = Arrays.asList(orders);
//        boolean isValid = areValidOrders(resultOrders);

//        assertFalse(isValid);

    }

    private boolean areValidOrders(List<Order> orders) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return orders.stream().noneMatch(order -> {
            Set<ConstraintViolation<Order>> constraintViolationsErrors = validator.validate(order);
            return !constraintViolationsErrors.isEmpty();
        });
    }

}