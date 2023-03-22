package com.ziolkowski.dawid.jwtauth.Controller;

import com.ziolkowski.dawid.jwtauth.Model.TestModel;
import com.ziolkowski.dawid.jwtauth.Model.User;
import com.ziolkowski.dawid.jwtauth.Service.TestModelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor
public class ResourceController {
    private TestModelService service;
    @GetMapping("/model")
    public ResponseEntity<List<TestModel>> test(Authentication authentication){
        User u = (User) authentication.getPrincipal();
        log.info(u.getLogin());
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
