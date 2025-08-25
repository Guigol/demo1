package com.internaltools.demo1.services;

import com.internaltools.demo1.entities.DUsers;
import com.internaltools.demo1.repositories.DUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DUsersService {

    private final DUsersRepository repository;

    public List<DUsers> getAllUsers() {
        return repository.findAll();
    }

    public Optional<DUsers> getUserById(Long id) {
        return repository.findById(id);
    }

    public DUsers createUser(DUsers user) {
        return repository.save(user);
    }

    public Optional<DUsers> updateUser(Long id, DUsers updatedUser) {
        return repository.findById(id).map(existing -> {
            existing.setName(updatedUser.getName());
            existing.setEmail(updatedUser.getEmail());
            existing.setRole(updatedUser.getRole());
            existing.setDepartment(updatedUser.getDepartment());
            existing.setStatus(updatedUser.getStatus());
            existing.setHireDate(updatedUser.getHireDate());
            return repository.save(existing);
        });
    }

    public boolean deleteUser(Long id) {
        return repository.findById(id).map(user -> {
            repository.delete(user);
            return true;
        }).orElse(false);
    }
}
