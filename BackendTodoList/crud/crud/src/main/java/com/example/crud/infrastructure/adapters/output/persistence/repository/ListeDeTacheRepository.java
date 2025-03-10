package com.example.crud.infrastructure.adapters.output.persistence.repository;

import com.example.crud.infrastructure.adapters.output.persistence.entity.ListeDeTacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListeDeTacheRepository extends JpaRepository<ListeDeTacheEntity, Long> {
}