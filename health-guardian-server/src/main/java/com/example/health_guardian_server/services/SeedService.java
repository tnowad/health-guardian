package com.example.health_guardian_server.services;

import org.springframework.stereotype.Service;

@Service
public interface SeedService {
    public void clear();

    public void defaultSeed();

    public void mockSeed();
}
