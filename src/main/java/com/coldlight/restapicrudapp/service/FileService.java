package com.coldlight.restapicrudapp.service;


import com.coldlight.restapicrudapp.entity.EventEntity;
import com.coldlight.restapicrudapp.entity.FileEntity;
import com.coldlight.restapicrudapp.entity.UserEntity;
import com.coldlight.restapicrudapp.repository.FileRepository;
import com.coldlight.restapicrudapp.util.HibernateUtils;
import org.hibernate.Hibernate;

import java.sql.Blob;
import java.util.List;

public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileEntity createFile(String name, String filePath, EventEntity event, UserEntity user, byte[] payload) {
        Blob blob = Hibernate.getLobCreator(HibernateUtils.getSession()).createBlob(payload);
        FileEntity fileEntity = FileEntity.builder()
                .name(name)
                .filePath(filePath)
                .events(List.of(event))
                .user(user)
                .payLoad(blob)
                .build();
        return fileRepository.save(fileEntity);
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.getAll();
    }

    public FileEntity getFileByID(Long id) {
        return fileRepository.getByID(id);
    }

    public void deleteFileByID(Long id, EventEntity event) {
        FileEntity fileEntity = fileRepository.getByID(id);
        if (fileEntity == null) {
            throw new RuntimeException("File with ID = " + id + "not found");
        }
        fileEntity.getEvents().add(event);
        fileRepository.delete(fileEntity);
    }
}
