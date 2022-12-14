package com.coldlight.restapicrudapp.repository.repoImpl;


import com.coldlight.restapicrudapp.model.UserEntity;
import com.coldlight.restapicrudapp.repository.HibernateUtils;
import com.coldlight.restapicrudapp.repository.UserEntityRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserRepositoryImpl implements UserEntityRepository {
    @Override
    public List<UserEntity> getAll() {
        List<UserEntity> userEntityList;
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        userEntityList = session.createQuery("""
                        select distinct u from UserEntity u
                        left join fetch u.files f
                        """, UserEntity.class)
                .getResultList();
        userEntityList = session.createQuery("""
                        select distinct u from UserEntity u
                        left join fetch u.events e
                        where u in :userEntityList
                        """, UserEntity.class)
                .setParameter("userEntityList", userEntityList)
                .getResultList();
        transaction.commit();
        session.close();
        return userEntityList;
    }

    @Override
    public UserEntity getEverythingByID(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        UserEntity userEntity = session.createQuery("""
                        select distinct u from UserEntity u
                        left join fetch u.files f 
                        where u.id = :id""", UserEntity.class)
                .setParameter("id", id)
                .getSingleResult();
        userEntity = session.createQuery("""
                        select distinct u from UserEntity u
                        left join fetch u.events e
                        where u = :user""", UserEntity.class)
                .setParameter("user", userEntity)
                .getSingleResult();
        transaction.commit();
        session.close();
        return userEntity;
    }

    @Override
    public UserEntity getByID(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        UserEntity userEntity = session.get(UserEntity.class, id);
        transaction.commit();
        session.close();
        return userEntity;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(userEntity);
        transaction.commit();
        session.close();
        return userEntity;
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(userEntity);
        transaction.commit();
        session.close();
        return userEntity;
    }

    @Override
    public void delete(UserEntity userEntity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(userEntity);
        transaction.commit();
        session.close();
    }
}
