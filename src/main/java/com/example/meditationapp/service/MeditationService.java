package com.example.meditationapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.meditationapp.model.MeditationSession;
import com.example.meditationapp.repository.MeditationRepository;

@Service
public class MeditationService {
    @Autowired
    private MeditationRepository repository;

    public MeditationSession saveSession(MeditationSession session) {
        return repository.save(session);
    }
}
