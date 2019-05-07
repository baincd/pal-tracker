package io.pivotal.pal.tracker;

import static java.util.concurrent.TimeUnit.SECONDS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

@RestController
public class EnvController {

    private String port;
    private String memLim;
    private String cfInstanceIdx;
    private String cfInstanceAddr;

    public EnvController(@Value("${port:NOT SET}") String port,
                         @Value("${memory.limit:NOT SET}") String memLim,
                         @Value("${cf.instance.index:NOT SET}") String cfInstanceIdx,
                         @Value("${cf.instance.addr:NOT SET}") String cfInstanceAddr) {
        this.port = port;
        this.memLim = memLim;
        this.cfInstanceIdx = cfInstanceIdx;
        this.cfInstanceAddr = cfInstanceAddr;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        Map map = new HashMap();
        map.put("PORT",port);
        map.put("MEMORY_LIMIT",memLim);
        map.put("CF_INSTANCE_INDEX",cfInstanceIdx);
        map.put("CF_INSTANCE_ADDR",cfInstanceAddr);
        return map;
    }

    @GetMapping("/stop")
    public String stop() {
    	Executors
    		.newSingleThreadScheduledExecutor()
    		.schedule(() -> {System.exit(1);}, 10, SECONDS);
    	return "stop scheduled...";
        
    }
}
