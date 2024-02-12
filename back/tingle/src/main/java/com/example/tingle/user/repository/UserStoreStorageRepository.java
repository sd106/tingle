package com.example.tingle.user.repository;

import com.example.tingle.user.entity.UserStoreStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStoreStorageRepository extends JpaRepository<UserStoreStorage, Long> {
}
