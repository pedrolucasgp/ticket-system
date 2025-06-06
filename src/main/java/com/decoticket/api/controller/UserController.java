package com.decoticket.api.controller;

import com.decoticket.api.domain.user.User;
import com.decoticket.api.domain.user.UserRequestDTO;
import com.decoticket.api.domain.user.UserResponseDTO;
import com.decoticket.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{email}")

    public ResponseEntity<UserResponseDTO> getByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponseDTO>> getUsers(){
        List<UserResponseDTO> allUsers = this.userService.listAll();
        return ResponseEntity.ok(allUsers);
    }

    /*@PostMapping("")
    public ResponseEntity<User> create(@RequestBody UserRequestDTO body){
        User newUser = this.userService.createUser(body);
        return ResponseEntity.ok(newUser);
    }*/

    @PutMapping("")
    public ResponseEntity<User> edit(@RequestBody UserResponseDTO body){
        User editUser = userService.editUser(body);
        return ResponseEntity.ok(editUser);
    }
}
