package org.acme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TimeService {
    
    @Autowired
    private TimeRepository repository;

    public String currentTime() {
        return repository.currentTime();
    }

    @Cacheable("times")
    public String cachedTime() {
        return repository.currentTime();
    }

}
