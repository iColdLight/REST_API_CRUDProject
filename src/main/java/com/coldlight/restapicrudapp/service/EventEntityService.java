package com.coldlight.restapicrudapp.service;


import com.coldlight.restapicrudapp.model.EventEntity;
import com.coldlight.restapicrudapp.repository.EventEntityRepository;
import com.coldlight.restapicrudapp.repository.repoImpl.EventRepositoryImpl;

import java.util.List;

public class EventEntityService {
    private final EventEntityRepository eventEntityRepository = new EventRepositoryImpl();


    public EventEntity createEvent(Long date) {
        EventEntity eventEntity = EventEntity.builder()
                .date(date)
                .build();
        return eventEntityRepository.save(eventEntity);
    }

    public List<EventEntity> getAllEvents() {
        return eventEntityRepository.getAll();
    }

    public EventEntity getEventByID(Long id) {
        return eventEntityRepository.getByID(id);
    }

    public EventEntity updateEvent(Long id, Long newEvent) {
        EventEntity eventEntity = eventEntityRepository.getByID(id);
        if (eventEntity == null) {
            throw new RuntimeException("File with ID = " + id + "not found");
        }
        eventEntity.setDate(newEvent);
        return eventEntityRepository.update(eventEntity);
    }

    public EventEntity deleteEventByID(Long id) {
        EventEntity eventEntity = eventEntityRepository.getByID(id);
        if (eventEntity == null) {
            throw new RuntimeException("Event with ID = " + id + "not found");
        }
        eventEntityRepository.delete(eventEntity);
        return eventEntity;
    }
}
