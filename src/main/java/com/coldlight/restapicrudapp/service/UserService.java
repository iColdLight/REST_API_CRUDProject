package com.coldlight.restapicrudapp.service;


import com.coldlight.restapicrudapp.entity.UserEntity;
import com.coldlight.restapicrudapp.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserEntity createUser(String firstName, String lastName) {
        UserEntity userEntity = UserEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        return userRepository.save(userEntity);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.getAll();
    }

    public UserEntity getUserByID(Long id) {
        return userRepository.getByID(id);
    }

    public UserEntity getUserWithFilesAndEventsByID(Long id) {
        return userRepository.getUserWithFilesAndEventsByID(id);
    }

    public UserEntity updateUser(Long id, String newFirstName, String newLastName) {
        UserEntity userEntity = userRepository.getByID(id);
        if (userEntity == null) {
            throw new RuntimeException("User with ID = " + id + "not found");
        }
        userEntity.setFirstName(newFirstName);
        userEntity.setLastName(newLastName);
        return userRepository.update(userEntity);
    }

    public void deleteUserByID(Long id) {
        UserEntity userEntity = userRepository.getByID(id);
        if (userEntity == null) {
            throw new RuntimeException("User with ID = " + id + "not found");
        }
        userRepository.delete(userEntity);
    }

}
