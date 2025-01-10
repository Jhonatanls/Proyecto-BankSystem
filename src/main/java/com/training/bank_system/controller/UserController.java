package com.training.bank_system.controller;

import com.training.bank_system.modelos.User;
import com.training.bank_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity saveUser(@RequestBody User user){
        return new ResponseEntity(userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable("id") Long idUser){
        return new ResponseEntity(userService.getUser(idUser), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity getAllUsers(){
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity editUser(@PathVariable("id") Long idUser, @RequestBody User user){
        return new ResponseEntity(userService.editUser(idUser, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long idUser){
        boolean response = userService.deleteUser(idUser);
        if(response){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
