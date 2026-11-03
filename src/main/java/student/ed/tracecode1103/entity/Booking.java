package student.ed.tracecode1103.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import student.ed.tracecode1103.constant.BookingStatus;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_no", nullable = false, unique = true)
    private String bookingNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", nullable = false)
    private EventSession event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private Integer unitPrice;

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.PENDING;

    @Column(name = "booked_at", nullable = false)
    private LocalDateTime bookedAt;

    protected Booking() {
    }

    public Booking(
            String bookingNo,
            EventSession event,
            Member member,
            Integer quantity,
            Integer unitPrice,
            Integer totalAmount) {
        this.bookingNo = bookingNo;
        this.event = event;
        this.member = member;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = totalAmount;
    }

    @PrePersist
    void prePersist() {
        if (bookedAt == null) {
            bookedAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public EventSession getEvent() {
        return event;
    }

    public Member getMember() {
        return member;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }
}

