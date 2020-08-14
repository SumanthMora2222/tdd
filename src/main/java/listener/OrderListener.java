package listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class OrderListener {

    @Autowired
    private BookingService bookingService;

    public OrderListener(BookingService bookingService) {

        this.bookingService = bookingService;
    }

    public void process(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        Order[] orders = new Order[0];
        try {
            orders = objectMapper.readValue(message, Order[].class);
        } catch (JsonProcessingException e) {
            throw new InvalidOrderException();
        }
        if (validateOrders(Arrays.asList(orders))) {
            bookingService.book(Arrays.asList(orders));
        } else {
            throw new InvalidOrderException();
        }

    }

    private boolean validateOrders(List<Order> orders) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Order>> constraintViolationsErrors = validator.validate(orders.get(0));
        return constraintViolationsErrors.isEmpty();
    }
}
