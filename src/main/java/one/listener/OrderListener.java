package one.listener;

import one.exceptions.InvalidOrderException;
import one.model.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import one.service.BookingService;

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
