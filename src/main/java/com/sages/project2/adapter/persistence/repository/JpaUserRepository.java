package com.sages.project2.adapter.persistence.repository;

import com.sages.project2.adapter.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity,Long> {

}
