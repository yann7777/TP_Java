package com.example.crud.infrastructure.adapters.output.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud.infrastructure.adapters.output.persistence.entity.TacheEntity;

public interface TacheRepository extends JpaRepository<TacheEntity, Long> {
}