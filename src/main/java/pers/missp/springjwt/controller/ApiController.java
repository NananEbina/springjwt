package pers.missp.springjwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public-api")
public class ApiController {

    @GetMapping("/api1")
    public ResponseEntity<Object> api1(String username){
        return ResponseEntity.ok("api1");
    }

    @GetMapping("/api2")
    public ResponseEntity<Object> api2(){
        return ResponseEntity.ok("api2");
    }

    @GetMapping("/api3")
    public ResponseEntity<Object> api3(){
        return ResponseEntity.ok("api3");
    }

    @GetMapping("/title")
    public ResponseEntity<Object> title() {
        Map<String, String> map = new HashMap<>(1);
        map.put("title", "hello world");
        return ResponseEntity.ok(map);
    }
}
