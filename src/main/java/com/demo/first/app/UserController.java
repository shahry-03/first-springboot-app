package com.demo.first.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    public Map<Integer, User> userDb = new HashMap<>();

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        System.out.println(user.getEmail());
        userDb.putIfAbsent(user.getId(), user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User user){
        if(userDb.containsKey(user.getId())){
            userDb.put(user.getId(), user);
            return ResponseEntity.status(HttpStatus.OK).body("User updated!");
        }
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        if (!userDb.containsKey(id))
            return ResponseEntity.notFound().build();
        userDb.remove(id);
        return ResponseEntity.ok( "User deleted!");
    }

    @GetMapping
    public List<User> getUsers(){
        return new ArrayList<>(userDb.values());
    }

    //@GetMapping("/users", "/user/{id}")

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") int id){
        if (!userDb.containsKey(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userDb.get(id));
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<User> getUseOrder(
            @PathVariable("userId") int id,
            @PathVariable int orderId
    ){
        System.out.println("Order id: "+ orderId);
        if (!userDb.containsKey(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userDb.get(id));
    }

    // /search?name=shahryar
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(
            @RequestParam(required = false, defaultValue = "shahry") String name,
            @RequestParam(required = false, defaultValue = "email") String email
    ){
        System.out.println(name);
        List<User> users = userDb.values().stream()
                .filter(u -> u.getName().equalsIgnoreCase(name))
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .toList();
        return ResponseEntity.ok(users);
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
