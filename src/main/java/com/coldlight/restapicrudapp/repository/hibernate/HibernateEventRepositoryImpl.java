package com.coldlight.restapicrudapp.repository.hibernate;

import com.coldlight.restapicrudapp.entity.EventEntity;
import com.coldlight.restapicrudapp.repository.EventRepository;
import com.coldlight.restapicrudapp.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateEventRepositoryImpl implements EventRepository {
    @Override
    public List<EventEntity> getAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("from EventEntity").list();
        }
    }

    @Override
    public EventEntity getByID(Long id) {
        try (Session session = HibernateUtils.getSession()) {
            return session.get(EventEntity.class, id);
        }
    }

    @Override
    public EventEntity save(EventEntity eventEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(eventEntity);
            transaction.commit();
            return eventEntity;
        }
    }

    @Override
    public EventEntity update(EventEntity eventEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(eventEntity);
            transaction.commit();
            return eventEntity;
        }
    }

    @Override
    public void delete(EventEntity eventEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(eventEntity);
            transaction.commit();
        }
    }
}
