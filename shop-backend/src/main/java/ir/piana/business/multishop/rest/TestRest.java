package ir.piana.business.multishop.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestRest {
    @GetMapping(path = "test")
    public ResponseEntity testGet(HttpServletRequest request) {
        return ResponseEntity.ok("Hello World");
    }
}
