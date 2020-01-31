package pers.missp.springjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public ResponseEntity<Object> hello() {
        Map<String, String> map = new HashMap<>();
        map.put("message", "hello jwt !");
        return ResponseEntity.ok(map);
    }
    @GetMapping("/admin")
    public String admin() {
        return "hello admin !";
    }
}
