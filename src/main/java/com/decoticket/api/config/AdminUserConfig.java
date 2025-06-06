package com.decoticket.api.config;

import com.decoticket.api.domain.role.Role;
import com.decoticket.api.domain.user.User;
import com.decoticket.api.repositories.RoleRepository;
import com.decoticket.api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        for (Role.Values roleEnum : Role.Values.values()) {
            roleRepository.findByName(roleEnum.name())
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName(roleEnum.name());
                        return roleRepository.save(role);
                    });
        }

        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Admin role not found"));

        var userAdmin = userRepository.findByEmail("admin");


        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("Admin already exists.");
                },
                () -> {
                    var user = new User();
                    user.setEmail("admin");
                    user.setPassword(passwordEncoder.encode("admin"));
                    user.setFullName("Admin");
                    user.setIdentification("Admin");
                    user.setRoles(Set.of(roleAdmin));
                    user.setIsActive(true);
                    userRepository.save(user);
                }
        );
    }
}
