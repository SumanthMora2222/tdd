package one.service;

import java.util.List;
import java.util.stream.Collectors;
import one.model.Order;
import org.springframework.stereotype.Service;
import one.repo.BookingRepository;

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
    if (!successfulOrders.isEmpty()) {
      bookingRepository.add(successfulOrders);
      final List<Order> unpublishedOrders = successfulOrders.stream()
        .filter(order -> !bookingRepository.isOrderPresent(order.getSalesOrderId())).collect(Collectors.toList());
      bookingPublisher.publish(unpublishedOrders);
    }
  }
}
