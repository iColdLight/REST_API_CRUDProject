package com.coldlight.restapicrudapp.repository.hibernate;

import com.coldlight.restapicrudapp.entity.FileEntity;
import com.coldlight.restapicrudapp.repository.FileRepository;
import com.coldlight.restapicrudapp.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateFileRepositoryImpl implements FileRepository {
    @Override
    public List<FileEntity> getAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("""
                    select distinct f from FileEntity f
                    left join fetch f.events e
                    """, FileEntity.class).getResultList();
        }
    }

    @Override
    public FileEntity getByID(Long id) {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("""
                    select distinct f from FileEntity f
                    left join fetch f.events e
                    where f.id = :id""", FileEntity.class).setParameter("id", id).getSingleResult();
        }
    }

    @Override
    public FileEntity save(FileEntity fileEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(fileEntity);
            transaction.commit();
            return fileEntity;
        }
    }

    @Override
    public FileEntity update(FileEntity fileEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(fileEntity);
            transaction.commit();
            return fileEntity;
        }
    }

    @Override
    public void delete(FileEntity fileEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(fileEntity);
            transaction.commit();
        }

    }
}
