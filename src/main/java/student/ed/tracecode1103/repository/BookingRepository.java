package student.ed.tracecode1103.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student.ed.tracecode1103.constant.BookingStatus;
import student.ed.tracecode1103.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = "select * from bookings where event_id = :memberId order by id", nativeQuery = true)
    List<Booking> findHistoryByMemberId(@Param("memberId") Long memberId);

    long countByEventIdAndStatus(Long eventId, BookingStatus status);

    @Query("""
            select coalesce(sum(booking.totalAmount), 0)
            from Booking booking
            where booking.status <> student.ed.tracecode1103.constant.BookingStatus.CANCELLED
            """)
    Long sumPaidRevenue();

    @Query("""
            select case when count(booking) > 0 then true else false end
            from Booking booking
            where booking.member.id = :memberId
              and booking.event.id = :eventId
              and booking.status = student.ed.tracecode1103.constant.BookingStatus.PAID
            """)
    boolean existsActiveBooking(
            @Param("memberId") Long memberId,
            @Param("eventId") Long eventId
    );
}

