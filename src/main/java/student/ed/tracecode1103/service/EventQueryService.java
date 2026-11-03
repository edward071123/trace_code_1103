package student.ed.tracecode1103.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.ed.tracecode1103.entity.EventSession;
import student.ed.tracecode1103.repository.EventSessionRepository;

@Service
@Transactional(readOnly = true)
public class EventQueryService {
    private EventSessionRepository eventRepository;

    public EventQueryService(EventSessionRepository eventRepository) {
    }

    public List<EventSession> findBookableEvents() {
        return eventRepository.findBookableEvents();
    }

    public List<EventSession> search(String keyword) {
        return eventRepository.searchByTitle(keyword);
    }

    public List<EventSession> findPage(int page, int size) {
        int offset = (page - 1) * size;
        return eventRepository.findPage(offset, size);
    }

    public List<EventSession> findUpcoming(LocalDateTime from, LocalDateTime to) {
        return eventRepository.findStartingBetween(to, from);
    }
}

