package student.ed.tracecode1103.runner;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import student.ed.tracecode1103.entity.Booking;
import student.ed.tracecode1103.entity.EventSession;
import student.ed.tracecode1103.repository.BookingRepository;
import student.ed.tracecode1103.repository.EventSessionRepository;
import student.ed.tracecode1103.service.BookingService;
import student.ed.tracecode1103.service.EventQueryService;

@Component
public class TicketDemoRunner implements CommandLineRunner {
    private final EventQueryService eventQueryService;
    private final BookingService bookingService;
    private final EventSessionRepository eventRepository;
    private final BookingRepository bookingRepository;

    public TicketDemoRunner(
            EventQueryService eventQueryService,
            BookingService bookingService,
            EventSessionRepository eventRepository,
            BookingRepository bookingRepository) {
        this.eventQueryService = eventQueryService;
        this.bookingService = bookingService;
        this.eventRepository = eventRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(String... args) {
        check("01 service can query repositories", () ->
                printEvents(eventQueryService.findBookableEvents()));

        check("02 event 101 ticket price is 1200", () ->
                System.out.println("price = " + eventRepository.findById(101L).orElseThrow().getTicketPrice()));

        check("03 bookable event ids are 101, 103, 104", () ->
                printEvents(eventRepository.findBookableEvents()));

        check("04 title contains Spring returns 102, 103", () ->
                printEvents(eventQueryService.search("Spring")));

        check("05 zero-based page 1 size 2 returns 103, 104", () ->
                printEvents(eventQueryService.findPage(1, 2)));

        check("06 member 2 history contains T-DEMO-001 only", () ->
                printBookings(bookingService.findMemberHistory(2L)));

        check("07 event 101 has a paid sale", () ->
                System.out.println("sold = " + bookingService.isSold(101L)));

        check("08 paid revenue is 1200", () ->
                System.out.println("paid revenue = " + bookingService.paidRevenue()));

        check("09 events starting from 11/03 through 11/10 are 102, 103, 101", () ->
                printEvents(eventQueryService.findUpcoming(
                        LocalDateTime.of(2026, 11, 3, 0, 0),
                        LocalDateTime.of(2026, 11, 10, 23, 59, 59)
                )));

        check("10 pending or paid booking is active", () ->
                System.out.println("member 1 / event 103 active = "
                        + bookingService.hasActiveBooking(1L, 103L)));

        check("11 close two events before 11/09", () -> {
            int updated = bookingService.closeEventsBefore(LocalDateTime.of(2026, 11, 9, 0, 0));
            System.out.println("updated = " + updated);
            System.out.println("event 102 status = "
                    + eventRepository.findById(102L).orElseThrow().getStatus());
            System.out.println("event 103 status = "
                    + eventRepository.findById(103L).orElseThrow().getStatus());
        });

        check("12 two tickets total 1200", () -> {
            Booking booking = bookingService.createBooking(1L, 103L, 2, false);
            System.out.println("unit/quantity/total = "
                    + booking.getUnitPrice() + "/"
                    + booking.getQuantity() + "/"
                    + booking.getTotalAmount());
        });

        check("13 reject zero tickets without a new booking", () -> {
            long before = bookingRepository.count();
            try {
                bookingService.createBooking(1L, 101L, 0, false);
            } catch (RuntimeException exception) {
                System.out.println(exception.getClass().getSimpleName() + ": " + exception.getMessage());
            }
            System.out.println("booking count before/after = " + before + "/" + bookingRepository.count());
        });

        check("14 event 103 capacity changes from 10 to 8", () ->
                System.out.println("remaining capacity = "
                        + eventRepository.findById(103L).orElseThrow().getRemainingCapacity()));

        check("15 payment failure rolls back booking and capacity", () -> {
            long countBefore = bookingRepository.count();
            int capacityBefore = eventRepository.findById(104L).orElseThrow().getRemainingCapacity();
            try {
                bookingService.createBooking(1L, 104L, 1, true);
            } catch (RuntimeException exception) {
                System.out.println(exception.getClass().getSimpleName() + ": " + exception.getMessage());
            }
            long countAfter = bookingRepository.count();
            int capacityAfter = eventRepository.findById(104L).orElseThrow().getRemainingCapacity();
            System.out.println("booking count before/after = " + countBefore + "/" + countAfter);
            System.out.println("capacity before/after = " + capacityBefore + "/" + capacityAfter);
        });
    }

    private void check(String title, Runnable action) {
        System.out.println("\n===== " + title + " =====");
        try {
            action.run();
        } catch (Exception exception) {
            System.out.println(exception.getClass().getSimpleName() + ": " + exception.getMessage());
        }
    }

    private void printEvents(List<EventSession> events) {
        events.forEach(event -> System.out.printf(
                "%d | %-20s | price=%d | capacity=%d | %s | %s%n",
                event.getId(),
                event.getTitle(),
                event.getTicketPrice(),
                event.getRemainingCapacity(),
                event.getStatus(),
                event.getStartAt()
        ));
        System.out.println("count = " + events.size());
    }

    private void printBookings(List<Booking> bookings) {
        bookings.forEach(booking -> System.out.printf(
                "%s | member=%s | event=%s | quantity=%d | total=%d | %s%n",
                booking.getBookingNo(),
                booking.getMember().getAccount(),
                booking.getEvent().getTitle(),
                booking.getQuantity(),
                booking.getTotalAmount(),
                booking.getStatus()
        ));
        System.out.println("count = " + bookings.size());
    }
}

