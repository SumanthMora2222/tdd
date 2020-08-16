package service;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import model.Order;
import org.junit.Before;
import org.junit.Test;
import repo.BookingRepository;

public class BookingServiceTest {

  private BookingPublisher bookingPublisher;
  private BookingRepository bookingRepository;
  private BookingService bookingService;

  @Before
  public void setUp() {
    bookingPublisher = mock(BookingPublisher.class);
    bookingRepository = mock(BookingRepository.class);

    bookingService = new BookingService(bookingRepository, bookingPublisher);

    final Order firstOrder = new Order("123456", "QWERASDF", "SUCCESS", "2019-08-21T11:05:03+00:00");
    when(bookingRepository.isOrderPresent(firstOrder.getSalesOrderId())).thenReturn(false);
  }

  @Test
  public void shouldSaveAndPublishOrdersWithSuccessStatus() {
    final Order firstOrder = new Order("123456", "QWERASDF", "SUCCESS", "2019-08-21T11:05:03+00:00");
    final Order secondOrder = new Order("123456", "QWERASDF", "SUCCESS", "2019-08-21T11:05:03+00:00");
    final List<Order> orders = Arrays.asList(firstOrder,secondOrder);

    bookingService.book(orders);

    verify(bookingRepository).add(orders);
    verify(bookingPublisher).publish(orders);
  }

  @Test
  public void shouldNotSaveAndNotPublishOrdersIfStatusIsPending() {
    BookingPublisher bookingPublisher = mock(BookingPublisher.class);
    BookingRepository bookingRepository = mock(BookingRepository.class);

    final BookingService bookingService = new BookingService(bookingRepository, bookingPublisher);

    final Order firstOrder = new Order("123456", "QWERASDF", "PENDING", "2019-08-21T11:05:03+00:00");
    final Order secondOrder = new Order("123456", "QWERASDF", "PENDING", "2019-08-21T11:05:03+00:00");
    final List<Order> orders = Arrays.asList(firstOrder,secondOrder);

    bookingService.book(orders);

    verify(bookingPublisher,never()).publish(anyList());
    verify(bookingRepository,never()).add(anyList());
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

  @Test
  public void shouldNotPublishOrdersMultipleTimes() {
    BookingPublisher bookingPublisher = mock(BookingPublisher.class);
    BookingRepository bookingRepository = mock(BookingRepository.class);

    final BookingService bookingService = new BookingService(bookingRepository, bookingPublisher);

    final Order firstOrder = new Order("123456", "QWERASDF", "SUCCESS", "2019-08-21T11:05:03+00:00");
    final List<Order> orders = Arrays.asList(firstOrder);

    when(bookingRepository.isOrderPresent(firstOrder.getSalesOrderId())).thenReturn(true);

    bookingService.book(orders);

    verify(bookingPublisher,never()).publish(Arrays.asList(firstOrder));
  }
}
