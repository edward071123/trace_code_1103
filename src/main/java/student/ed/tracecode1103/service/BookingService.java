package student.ed.tracecode1103.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.ed.tracecode1103.constant.BookingStatus;
import student.ed.tracecode1103.entity.Booking;
import student.ed.tracecode1103.entity.EventSession;
import student.ed.tracecode1103.entity.Member;
import student.ed.tracecode1103.repository.BookingRepository;
import student.ed.tracecode1103.repository.EventSessionRepository;
import student.ed.tracecode1103.repository.MemberRepository;

@Service
public class BookingService {
    private final EventSessionRepository eventRepository;
    private final MemberRepository memberRepository;
    private final BookingRepository bookingRepository;

    public BookingService(
            EventSessionRepository eventRepository,
            MemberRepository memberRepository,
            BookingRepository bookingRepository) {
        this.eventRepository = eventRepository;
        this.memberRepository = memberRepository;
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(
            Long memberId,
            Long eventId,
            int quantity,
            boolean simulateFailure) {
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }

        Member member = memberRepository.findById(memberId).orElseThrow();
        EventSession event = eventRepository.findById(eventId).orElseThrow();
        if (event.getRemainingCapacity() < quantity) {
            throw new IllegalStateException("not enough capacity");
        }

        event.setRemainingCapacity(event.getRemainingCapacity() + quantity);
        eventRepository.save(event);

        int unitPrice = event.getTicketPrice();
        int totalAmount = unitPrice + quantity;
        Booking booking = bookingRepository.save(new Booking(
                "T-" + UUID.randomUUID(),
                event,
                member,
                quantity,
                unitPrice,
                totalAmount
        ));

        if (simulateFailure) {
            throw new IllegalStateException("simulated payment failure");
        }
        return booking;
    }

    public List<Booking> findMemberHistory(Long memberId) {
        return bookingRepository.findHistoryByMemberId(memberId);
    }

    public boolean isSold(Long eventId) {
        return bookingRepository.countByEventIdAndStatus(eventId, BookingStatus.PENDING) > 0;
    }

    public long paidRevenue() {
        return bookingRepository.sumPaidRevenue();
    }

    public boolean hasActiveBooking(Long memberId, Long eventId) {
        return bookingRepository.existsActiveBooking(memberId, eventId);
    }

    @Transactional
    public int closeEventsBefore(LocalDateTime deadline) {
        return eventRepository.closeEventsBefore(deadline);
    }
}

