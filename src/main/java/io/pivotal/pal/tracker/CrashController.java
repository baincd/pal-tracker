package io.pivotal.pal.tracker;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrashController {
	
	boolean stopScheduled = false;
	
    @GetMapping("/stop")
    public String stop() {
    	if (stopScheduled) {
    		return "Stop already scheduled on this instance!!";
    	}
    	
    	Executors
    		.newSingleThreadScheduledExecutor()
    		.schedule(() -> {System.exit(1);}, 10, SECONDS);
    	stopScheduled = true;
    	return "stop scheduled...";
        
    }
}
