package com.example.inventoryapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class ServerInfoController {

    @GetMapping("/api/server-info")
    public ResponseEntity<Map<String, Object>> serverInfo() {
        String ip = "unknown", hostname = "unknown";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip       = addr.getHostAddress();
            hostname = addr.getHostName();
        } catch (Exception ignored) {}

        return ResponseEntity.ok(Map.of(
                "serverIp",  ip,
                "hostname",  hostname,
                "timestamp", LocalDateTime.now().toString(),
                "message",   "3-Tier HA Lab — Spring Boot 정상 응답"
        ));
    }
}
