package service;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


import java.util.Arrays;
import java.util.List;
import model.Order;
import org.junit.Test;

public class BookingServiceTest {

  @Test
  public void shouldPublishOrdersWithSuccessStatus() {
    BookingPublisher bookingPublisher = mock(BookingPublisher.class);
    BookingRepository bookingRepository = mock(BookingRepository.class);

    final BookingService bookingService = new BookingService(bookingRepository, bookingPublisher);

    final Order firstOrder = new Order("123456", "QWERASDF", "SUCCESS", "2019-08-21T11:05:03+00:00");
    final Order secondOrder = new Order("123456", "QWERASDF", "SUCCESS", "2019-08-21T11:05:03+00:00");
    final List<Order> orders = Arrays.asList(firstOrder,secondOrder);

    bookingService.book(orders);

    verify(bookingPublisher).publish(orders);
  }

  @Test
  public void shouldNotPublishOrdersIfStatusIsPending() {
    BookingPublisher bookingPublisher = mock(BookingPublisher.class);
    BookingRepository bookingRepository = mock(BookingRepository.class);

    final BookingService bookingService = new BookingService(bookingRepository, bookingPublisher);

    final Order firstOrder = new Order("123456", "QWERASDF", "PENDING", "2019-08-21T11:05:03+00:00");
    final Order secondOrder = new Order("123456", "QWERASDF", "PENDING", "2019-08-21T11:05:03+00:00");
    final List<Order> orders = Arrays.asList(firstOrder,secondOrder);

    bookingService.book(orders);

    verify(bookingPublisher,never()).publish(anyList());
  }

  @Test
  public void shouldPublishOnlyOrdersWithStatusAsSuccessAndNotPublishPendingOrders() {
    BookingPublisher bookingPublisher = mock(BookingPublisher.class);
    BookingRepository bookingRepository = mock(BookingRepository.class);

    final BookingService bookingService = new BookingService(bookingRepository, bookingPublisher);

    final Order firstOrder = new Order("123456", "QWERASDF", "SUCCESS", "2019-08-21T11:05:03+00:00");
    final Order secondOrder = new Order("123456", "QWERASDF", "PENDING", "2019-08-21T11:05:03+00:00");
    final List<Order> orders = Arrays.asList(firstOrder,secondOrder);

    bookingService.book(orders);

    verify(bookingPublisher).publish(Arrays.asList(firstOrder));
  }
}
