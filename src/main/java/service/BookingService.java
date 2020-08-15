package service;

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
        if(bookingRepository.add(orders)){
            bookingPublisher.publish(orders);
        }
    }
}
