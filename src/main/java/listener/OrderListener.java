package listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.InvalidOrderException;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import service.BookingService;

public class OrderListener {

    @Autowired
    private BookingService bookingService;

    public OrderListener(BookingService bookingService) {

        this.bookingService = bookingService;
    }

    public void process(List<Order> orders) {
        if (orders.isEmpty()) {
            throw new InvalidOrderException();
        }

        List<Order> validOrders = orders.stream()
                .filter(order -> !isFutureTime(order.getTimeStamp()))
                .collect(Collectors.toList());

        if (!validOrders.isEmpty()) {
            bookingService.book(validOrders);
        }

        //exception test

    }

    private boolean isFutureTime(String timeStamp) {
        return LocalDateTime.parse(timeStamp).isAfter(LocalDateTime.now());
    }
}
