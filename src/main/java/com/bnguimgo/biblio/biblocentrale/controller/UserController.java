package com.bnguimgo.biblio.biblocentrale.controller;

import com.bnguimgo.biblio.biblocentrale.dto.UserDTO;
import com.bnguimgo.biblio.biblocentrale.entity.User;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable(value = "email") String email) throws BiblioException {

        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }
}
