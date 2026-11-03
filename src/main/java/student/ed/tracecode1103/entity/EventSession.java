package student.ed.tracecode1103.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import student.ed.tracecode1103.constant.EventStatus;

@Entity
@Table(name = "event_sessions")
public class EventSession {
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "ticket_price", nullable = false)
    private Integer ticketPrice;

    @Column(name = "remaining_capacity", nullable = false)
    private Integer remainingCapacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    protected EventSession() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getTicketPrice() {
        return ticketPrice;
    }

    public Integer getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setRemainingCapacity(Integer remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }
}

