package com.coldlight.restapicrudapp.repository.hibernate;


import com.coldlight.restapicrudapp.entity.UserEntity;
import com.coldlight.restapicrudapp.repository.UserRepository;
import com.coldlight.restapicrudapp.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateUserRepositoryImpl implements UserRepository {
    @Override
    public List<UserEntity> getAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("""
                    select distinct u from UserEntity u
                    left join fetch u.events e
                    """, UserEntity.class).getResultList();
        }
    }

    @Override
    public UserEntity getUserWithFilesAndEventsByID(Long id) {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("""
                    select distinct u from UserEntity u
                    left join fetch u.events e
                    where u.id = :id""", UserEntity.class).setParameter("id", id).getSingleResult();
        }
    }

    @Override
    public UserEntity getByID(Long id) {
        try (Session session = HibernateUtils.getSession()) {
            return session.get(UserEntity.class, id);
        }
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(userEntity);
            transaction.commit();
            return userEntity;
        }
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(userEntity);
            transaction.commit();
            session.close();
            return userEntity;
        }
    }

    @Override
    public void delete(UserEntity userEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(userEntity);
            transaction.commit();
            session.close();
        }
    }
}
