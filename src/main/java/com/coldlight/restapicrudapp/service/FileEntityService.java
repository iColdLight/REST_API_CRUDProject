package com.coldlight.restapicrudapp.service;


import com.coldlight.restapicrudapp.model.FileEntity;
import com.coldlight.restapicrudapp.repository.FileEntityRepository;
import com.coldlight.restapicrudapp.repository.repoImpl.FileRepositoryImpl;

import java.util.List;

public class FileEntityService {
    private final FileEntityRepository fileEntityRepository = new FileRepositoryImpl();

    public FileEntity createFile(String name) {
        FileEntity fileEntity = FileEntity.builder()
                .name(name)
                .build();
        return fileEntityRepository.save(fileEntity);
    }

    public List<FileEntity> getAllFiles() {
        return fileEntityRepository.getAll();
    }

    public FileEntity getFileByID(Long id) {
        return fileEntityRepository.getByID(id);
    }

    public FileEntity updateFile(Long id, String newFile) {
        FileEntity fileEntity = fileEntityRepository.getByID(id);
        if (fileEntity == null) {
            throw new RuntimeException("File with ID = " + id + "not found");
        }
        fileEntity.setName(newFile);
        return fileEntityRepository.update(fileEntity);
    }

    public void deleteFileByID(Long id) {
        FileEntity fileEntity = fileEntityRepository.getByID(id);
        if (fileEntity == null) {
            throw new RuntimeException("File with ID = " + id + "not found");
        }
        fileEntityRepository.delete(fileEntity);
    }
}
