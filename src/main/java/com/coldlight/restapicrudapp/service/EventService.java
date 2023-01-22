package com.coldlight.restapicrudapp.service;


import com.coldlight.restapicrudapp.entity.EventEntity;
import com.coldlight.restapicrudapp.repository.EventRepository;

import java.util.List;

public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventEntity save(EventEntity eventEntity) {
        return eventRepository.save(eventEntity);
    }

    public List<EventEntity> getAllEvents() {
        return eventRepository.getAll();
    }

    public EventEntity getEventByID(Long id) {
        return eventRepository.getByID(id);
    }

}
