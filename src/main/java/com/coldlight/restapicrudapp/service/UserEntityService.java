package com.coldlight.restapicrudapp.service;


import com.coldlight.restapicrudapp.model.EventEntity;
import com.coldlight.restapicrudapp.model.FileEntity;
import com.coldlight.restapicrudapp.model.UserEntity;
import com.coldlight.restapicrudapp.repository.UserEntityRepository;
import com.coldlight.restapicrudapp.repository.repoImpl.UserRepositoryImpl;

import java.util.List;

public class UserEntityService {
    private final UserEntityRepository userEntityRepository = new UserRepositoryImpl();


    public UserEntity createUser(String firstName, String lastName) {
        UserEntity userEntity = UserEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        return userEntityRepository.save(userEntity);
    }

    public void addFile(UserEntity user, FileEntity file){
        user.getFiles().add(file);
        user.getEvents().addAll(file.getEvents());
        userEntityRepository.save(user);
    }

    public List<EventEntity> getAllEventsByUserID (Long id){
        UserEntity userByID = getUserByID(id);
        return userByID.getEvents();
    }
    public List<FileEntity> getAllFilesByUserID (Long id){
        UserEntity userByID = getUserByID(id);
        return userByID.getFiles();
    }

    public List<UserEntity> getAllUsers() {
        return userEntityRepository.getAll();
    }

    public UserEntity getUserByID(Long id) {
        return userEntityRepository.getByID(id);
    }

    public UserEntity getEverythingUserByID(Long id) {
        return userEntityRepository.getEverythingByID(id);
    }

    public UserEntity updateUser(Long id, String newFirstName, String newLastName) {
        UserEntity userEntity = userEntityRepository.getByID(id);
        if (userEntity == null) {
            throw new RuntimeException("User with ID = " + id + "not found");
        }
        userEntity.setFirstName(newFirstName);
        userEntity.setLastName(newLastName);
        return userEntityRepository.update(userEntity);
    }

    public void deleteUserByID(Long id) {
        UserEntity userEntity = userEntityRepository.getByID(id);
        if (userEntity == null) {
            throw new RuntimeException("User with ID = " + id + "not found");
        }
        userEntityRepository.delete(userEntity);
    }

}
