package one.model;

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
    public void shouldBeAbleToGenerateOrderModalFromMessage() throws JsonProcessingException {
        String message = "[{\n" +
                "  \"salesOrderId\": \"123456\",\n" +
                "  \"customerId\": \"QWERASDF\",\n" +
                "  \"status\": \"PENDING\",\n" +
                "  \"timeStamp\": \"2019-08-21T11:05:03+00:00\"\n" +
                "}]";

        ObjectMapper objectMapper = new ObjectMapper();
        Order[] orders = objectMapper.readValue(message, Order[].class);

        Order order = new Order("123456", "QWERASDF", "PENDING", "2019-08-21T11:05:03+00:00");

        assertEquals(Arrays.asList(order), Arrays.asList(orders));
    }

    @Test
    public void validationsShouldFailWhenSalesOrderIdIsEmpty() throws JsonProcessingException {
        String message = "[{\n" +
                "  \"salesOrderId\": \"\",\n" +
                "  \"customerId\": \"QWERASDF\",\n" +
                "  \"status\": \"PENDING\",\n" +
                "  \"timeStamp\": \"2019-08-21T11:05:03+00:00\"\n" +
                "}]";

        ObjectMapper objectMapper = new ObjectMapper();
        Order[] orders = objectMapper.readValue(message, Order[].class);

        assertEquals(false, areValidOrders(Arrays.asList(orders)));

    }

    @Test
    public void validationsShouldFailWhenCustomerIdIsEmpty() throws JsonProcessingException {
        String message = "[{\n" +
                "  \"salesOrderId\": \"123456\",\n" +
                "  \"customerId\": \"\",\n" +
                "  \"status\": \"PENDING\",\n" +
                "  \"timeStamp\": \"2019-08-21T11:05:03+00:00\"\n" +
                "}]";

        ObjectMapper objectMapper = new ObjectMapper();
        Order[] orders = objectMapper.readValue(message, Order[].class);

        assertEquals(false, areValidOrders(Arrays.asList(orders)));

    }

    private boolean areValidOrders(List<Order> orders) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Order>> constraintViolationsErrors = validator.validate(orders.get(0));
        return constraintViolationsErrors.isEmpty();
    }

}