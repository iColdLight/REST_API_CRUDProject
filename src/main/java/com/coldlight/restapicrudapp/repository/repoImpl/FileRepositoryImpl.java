package com.coldlight.restapicrudapp.repository.repoImpl;

import com.coldlight.restapicrudapp.model.FileEntity;
import com.coldlight.restapicrudapp.repository.FileEntityRepository;
import com.coldlight.restapicrudapp.repository.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FileRepositoryImpl implements FileEntityRepository {
    @Override
    public List<FileEntity> getAll() {
        List<FileEntity> fileEntityList;
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        fileEntityList = session.createQuery("from FileEntity").list();
        transaction.commit();
        session.close();
        return fileEntityList;
    }

    @Override
    public FileEntity getByID(Long id) {
            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            FileEntity fileEntity = session.get(FileEntity.class, id);
            transaction.commit();
            session.close();
            return fileEntity;
    }

    @Override
    public FileEntity save(FileEntity fileEntity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(fileEntity);
        transaction.commit();
        session.close();
        return fileEntity;
    }

    @Override
    public FileEntity update(FileEntity fileEntity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(fileEntity);
        transaction.commit();
        session.close();
        return fileEntity;
    }

    @Override
    public void delete(FileEntity fileEntity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(fileEntity);
        transaction.commit();
        session.close();
    }
}
