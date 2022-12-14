package com.coldlight.restapicrudapp.service;


import com.coldlight.restapicrudapp.model.EventEntity;
import com.coldlight.restapicrudapp.model.FileEntity;
import com.coldlight.restapicrudapp.repository.FileEntityRepository;
import com.coldlight.restapicrudapp.repository.repoImpl.FileRepositoryImpl;

import java.util.List;

public class FileEntityService {
   private final FileEntityRepository fileEntityRepository = new FileRepositoryImpl();

    public FileEntity createFile(String name, EventEntity event) {
        return FileEntity.builder()
                .name(name)
                .events(List.of(event))
                .build();
    }

    public List<EventEntity> getAllEventsByFileID (Long id){
        FileEntity fileByID = getFileByID(id);
        return fileByID.getEvents();
    }

    public List<FileEntity> getAllFiles() {
        return fileEntityRepository.getAll();
    }

    public FileEntity getFileByID(Long id) {
        return fileEntityRepository.getByID(id);
    }

    public FileEntity updateFile(Long id, String newFile, EventEntity event) {
        FileEntity fileEntity = fileEntityRepository.getByID(id);
        if (fileEntity == null) {
            throw new RuntimeException("File with ID = " + id + "not found");
        }
        fileEntity.setName(newFile);
        fileEntity.getEvents().add(event);
        return fileEntityRepository.update(fileEntity);
    }

    public void deleteFileByID(Long id, EventEntity event) {
        FileEntity fileEntity = fileEntityRepository.getByID(id);
        if (fileEntity == null) {
            throw new RuntimeException("File with ID = " + id + "not found");
        }
        fileEntity.getEvents().add(event);
        fileEntityRepository.delete(fileEntity);
    }
}
