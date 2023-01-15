package com.coldlight.restapicrudapp.service;


import com.coldlight.restapicrudapp.entity.EventEntity;
import com.coldlight.restapicrudapp.entity.Status;
import com.coldlight.restapicrudapp.repository.EventRepository;
import com.coldlight.restapicrudapp.repository.hibernate.HibernateEventRepositoryImpl;

import java.util.Date;

public class EventService {

    private final EventRepository eventRepository = new HibernateEventRepositoryImpl();

    public EventEntity createEvent(Status status) {
        return EventEntity.builder()
                .date(new Date())
                .status(status)
                .build();
    }
}
