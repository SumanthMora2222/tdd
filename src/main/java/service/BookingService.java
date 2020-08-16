package service;

import java.util.stream.Collectors;
import model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingPublisher bookingPublisher;

    public BookingService(BookingRepository bookingRepository, BookingPublisher bookingPublisher) {
        this.bookingRepository = bookingRepository;
        this.bookingPublisher = bookingPublisher;
    }

    public void book(List<Order> orders) {
        final List<Order> successfulOrders = orders.stream()
          .filter(order -> order.getStatus().equals("SUCCESS")).collect(Collectors.toList());
        if(!successfulOrders.isEmpty()) {
            bookingPublisher.publish(successfulOrders);
        }
    }
}
