package org.acme;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class GreetingController {

    @Value("${app.name}")
    String name;

    @Autowired
    TimeService timeService;

    AtomicInteger counter = new AtomicInteger();

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String hello() {
        return "Hello "+name
            +"\nCounter: "+counter.get()
            +"\nTime: "+timeService.currentTime()
            +"\nFirst Time: "+timeService.cachedTime()
        ;
    }

    @Secured("admin")
    @GetMapping(value="admin", produces = MediaType.TEXT_PLAIN_VALUE)
    public String helloAdmin() {
        return "Hello admin";
    }

    @Scheduled(fixedRate = 1000)    
    void jobAtFixedRate() {
        counter.incrementAndGet();
    }
}
