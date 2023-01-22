package com.coldlight.restapicrudapp.service;


import com.coldlight.restapicrudapp.entity.EventEntity;
import com.coldlight.restapicrudapp.entity.FileEntity;
import com.coldlight.restapicrudapp.entity.Status;
import com.coldlight.restapicrudapp.entity.UserEntity;
import com.coldlight.restapicrudapp.repository.FileRepository;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final UserService userService;
    private final EventService eventService;

    public FileEntity createFile(String name, String filePath, Long userId) {
        UserEntity user = userService.getUserByID(userId);
        FileEntity fileEntity = FileEntity.builder()
                .name(name)
                .filePath(filePath)
                .build();
        FileEntity createdFileEntity = fileRepository.save(fileEntity);
        EventEntity eventEntity = new EventEntity();
        eventEntity.setFile(fileEntity);
        eventEntity.setUser(user);
        eventEntity.setStatus(Status.CREATED);
        eventEntity.setDate(new Date());
        eventService.save(eventEntity);
        return createdFileEntity;
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.getAll();
    }

    public FileEntity getFileByID(Long id) {
        return fileRepository.getByID(id);
    }

    public void deleteFileByID(Long id) {
        FileEntity fileEntity = fileRepository.getByID(id);
        if (fileEntity == null) {
            throw new RuntimeException("File with ID = " + id + "not found");
        }
        EventEntity eventEntity = new EventEntity();
        eventEntity.setFile(fileEntity);
        eventEntity.setStatus(Status.DELETED);
        eventEntity.setDate(new Date());
        fileEntity.getEvents().add(eventEntity);
        fileRepository.delete(fileEntity);
    }
}
