package com.coldlight.restapicrudapp.repository;


import com.coldlight.restapicrudapp.model.UserEntity;


public interface UserEntityRepository extends GenericRepository<UserEntity, Long> {
    UserEntity getEverythingByID(Long id);
}
