package org.acme;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

@Repository
public class TimeRepository {

    public String currentTime() {
        return LocalDateTime.now().toString();
    }
    
}
