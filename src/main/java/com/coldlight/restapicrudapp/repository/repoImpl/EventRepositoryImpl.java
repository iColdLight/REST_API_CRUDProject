package com.coldlight.restapicrudapp.repository.repoImpl;

import com.coldlight.restapicrudapp.model.EventEntity;
import com.coldlight.restapicrudapp.repository.EventEntityRepository;
import com.coldlight.restapicrudapp.repository.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EventRepositoryImpl implements EventEntityRepository {
    @Override
    public List<EventEntity> getAll() {
        List<EventEntity> skillList;
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        skillList = session.createQuery("from EventEntity").list();
        transaction.commit();
        session.close();
        return skillList;
    }

    @Override
    public EventEntity getByID(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        EventEntity eventEntity = session.get(EventEntity.class, id);
        transaction.commit();
        session.close();
        return eventEntity;
    }

    @Override
    public EventEntity save(EventEntity eventEntity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(eventEntity);
        transaction.commit();
        session.close();
        return eventEntity;
    }

    @Override
    public EventEntity update(EventEntity eventEntity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(eventEntity);
        transaction.commit();
        session.close();
        return eventEntity;
    }

    @Override
    public void delete(EventEntity eventEntity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(eventEntity);
        transaction.commit();
        session.close();
    }
}
