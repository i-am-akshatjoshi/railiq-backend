package com.railiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.railiq.entity.PredictionLog;

import jakarta.persistence.Entity;

@Repository
public interface PredictionLogRepository extends JpaRepository<PredictionLog, Long>{

}
