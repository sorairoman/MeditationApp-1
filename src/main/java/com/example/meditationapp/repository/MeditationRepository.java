package com.example.meditationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.meditationapp.model.MeditationSession;

public interface MeditationRepository extends JpaRepository<MeditationSession, Long> {
}
