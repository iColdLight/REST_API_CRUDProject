package com.coldlight.restapicrudapp.repository;


import com.coldlight.restapicrudapp.entity.UserEntity;


public interface UserRepository extends GenericRepository<UserEntity, Long> {
    UserEntity getUserWithFilesAndEventsByID(Long id);
}
