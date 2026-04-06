package com.akhila.finance.service;

import com.akhila.finance.model.User;
import com.akhila.finance.DTO.UserDTO;
import com.akhila.finance.model.Role;
import com.akhila.finance.repository.UserRepository;
import com.akhila.finance.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;


   public User createUser(UserDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new RuntimeException("Name is required");
        }
        User user = new User();
        user.setId(null);          // ensure auto-generation
        user.setName(dto.getName());
        user.setActive(dto.isActive());
        user.setRole(null);        // no role initially

        return userRepo.save(user);
    }

   
    public User assignRole(Long userId, Long roleId) {

        if (userId == null || roleId == null) {
            throw new RuntimeException("userId and roleId are required");
        }
          User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        user.setRole(role);

        return userRepo.save(user);
    }
}