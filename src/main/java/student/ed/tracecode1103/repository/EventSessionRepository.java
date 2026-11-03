package student.ed.tracecode1103.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student.ed.tracecode1103.entity.EventSession;

public interface EventSessionRepository extends JpaRepository<EventSession, Long> {
    @Query("""
            select event
            from EventSession event
            where event.status = student.ed.tracecode1103.constant.EventStatus.OPEN
               or event.remainingCapacity > 0
            order by event.id
            """)
    List<EventSession> findBookableEvents();

    @Query("""
            select event
            from EventSession event
            where lower(event.title) like lower(:keyword)
            order by event.id
            """)
    List<EventSession> searchByTitle(@Param("keyword") String keyword);

    @Query(value = "select * from event_sessions order by id limit :size offset :offset", nativeQuery = true)
    List<EventSession> findPage(@Param("offset") int offset, @Param("size") int size);

    @Query("""
            select event
            from EventSession event
            where event.startAt between :from and :to
            order by event.startAt
            """)
    List<EventSession> findStartingBetween(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("""
            update EventSession event
            set event.status = student.ed.tracecode1103.constant.EventStatus.CLOSED
            where event.startAt < :deadline
            """)
    int closeEventsBefore(@Param("deadline") LocalDateTime deadline);
}

