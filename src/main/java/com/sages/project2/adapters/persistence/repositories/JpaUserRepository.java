package com.sages.project2.adapters.persistence.repositories;

import com.sages.project2.adapters.persistence.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<AdminEntity,String> {

}
