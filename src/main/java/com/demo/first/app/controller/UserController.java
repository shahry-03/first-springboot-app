package com.demo.first.app.controller;

import com.demo.first.app.service.UserService;
import com.demo.first.app.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    private UserService userService = new UserService();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User updatedUser = userService.updatedUser(user);
        if (updatedUser == null)
            return ResponseEntity.notFound().build();
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        return ResponseEntity.ok(updatedUser);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        boolean isDeleted = userService.deleteUser(id);
        if (!isDeleted)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok( "User deleted!");
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    //@GetMapping("/users", "/user/{id}")

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") int id){
        User user =  userService.getUserById(id);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<User> getUseOrder(
            @PathVariable("userId") int id,
            @PathVariable int orderId
    ){
        System.out.println("Order id: "+ orderId);
        User user =  userService.getUserById(id);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    // /search?name=shahryar
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(
            @RequestParam(required = false, defaultValue = "shahry") String name,
            @RequestParam(required = false, defaultValue = "email") String email
    ){
        return ResponseEntity.ok(userService.searchUser(name, email));
    }

    @GetMapping("/Info/{id}")
    public String getInfo(
            @PathVariable int id,
            @RequestParam String name,
            @RequestHeader("User-Agent") String userAgent){
        return "User Agent: " + userAgent
                + " id: " + id
                + " name: " + name;
    }


}
