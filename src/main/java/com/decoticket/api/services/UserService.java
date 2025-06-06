package com.decoticket.api.services;

import com.decoticket.api.domain.user.User;
import com.decoticket.api.domain.user.UserRequestDTO;
import com.decoticket.api.domain.user.UserResponseDTO;
import com.decoticket.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

//    public User createUser(UserRequestDTO data){
//        User newUser = new User();
//        newUser.setFullName(data.fullName());
//        newUser.setEmail(data.email());
//        newUser.setPassword(data.password());
//        newUser.setIdentification(data.identification());
//        newUser.setIsActive(data.isActive());
//
//        repository.save(newUser);
//
//        return newUser;
//    }

    public List<UserResponseDTO> listAll(){
        return repository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getEmail(),
                        user.getFullName(),
                        user.getIdentification(),
                        user.getRoles(),
                        user.getIsActive(),
                        user.getTicket()
                )).toList();
    }

    public UserResponseDTO findByEmail(String email){
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getIdentification(),
                user.getRoles(),
                user.getIsActive(),
                user.getTicket()
        );
    }

    public User editUser(UserResponseDTO data){
        User existingUser = repository.findById(data.id())
                .orElseThrow(() -> new RuntimeException("User not find."));

        existingUser.setEmail(data.email());
        existingUser.setFullName(data.fullName());
        existingUser.setIdentification(data.identification());
        existingUser.setIsActive(data.isActive());
        existingUser.setRoles(data.roles());

        return repository.save(existingUser);

    }
}
