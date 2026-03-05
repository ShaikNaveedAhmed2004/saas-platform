package com.company.saas.service;

import com.company.saas.dto.UserRegisterDTO;
import com.company.saas.entity.User;
import com.company.saas.repository.UserRepository;
import com.company.saas.repository.LicenseRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuditService auditService;

    private static final List<String> VALID_ROLES =
            Arrays.asList("ADMIN","EMPLOYEE","MANAGER");

    public User registerUser(UserRegisterDTO dto){

        String email = dto.getEmail().toLowerCase();

        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("User already exists with this email");
        }

        String role;

        // check if someone is logged in
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        boolean isAdminLoggedIn =
                authentication != null &&
                        authentication.isAuthenticated() &&
                        authentication.getAuthorities()
                                .stream()
                                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if(isAdminLoggedIn){

            // admin can assign roles
            role = dto.getRole() == null ? "EMPLOYEE" : dto.getRole().toUpperCase();

            if(!VALID_ROLES.contains(role)){
                throw new RuntimeException("Invalid role");
            }

        } else {

            // public registration
            role = "EMPLOYEE";
        }

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(email);

        // 🔐 encode password
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setRole(role);

        User savedUser = userRepository.save(user);

        auditService.logAction("Created user " + email);

        return savedUser;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(Long id){

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long id, UserRegisterDTO dto){

        User user = getUser(id);

        if(dto.getName()!=null)
            user.setName(dto.getName());

        if(dto.getRole()!=null){

            String role = dto.getRole().toUpperCase();

            if(!VALID_ROLES.contains(role))
                throw new RuntimeException("Invalid role");

            user.setRole(role);
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id){

        boolean hasLicense =
                licenseRepository.findAll().stream()
                        .anyMatch(l -> l.getUserId().equals(id)
                                && l.getStatus().equals("ACTIVE"));

        if(hasLicense){
            throw new RuntimeException("Cannot delete user with active licenses");
        }

        userRepository.deleteById(id);
    }
}