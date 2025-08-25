package com.internaltools.demo1.services;

import com.internaltools.demo1.entities.UserToolAccess;
import com.internaltools.demo1.repositories.UserToolAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserToolAccessService {

    private final UserToolAccessRepository repository;

    public List<UserToolAccess> getAllAccesses() {
        return repository.findAll();
    }

    public Optional<UserToolAccess> getAccessById(Integer id) {
        return repository.findById(id);
    }

    public List<UserToolAccess> getAccessByUser(Long userId) {
        return repository.findByDusers_Id(userId);
    }

    public List<UserToolAccess> getAccessByTool(Long toolId) {
        return repository.findByTools_Id(toolId);
    }

    public UserToolAccess grantAccess(UserToolAccess access) {
        access.setStatus(UserToolAccess.Status.ACTIVE);
        access.setGrantedAt(LocalDateTime.now());
        return repository.save(access);
    }

    public Optional<UserToolAccess> revokeAccess(Integer id, Long revokedByUserId) {
        return repository.findById(id).map(existing -> {
            existing.setStatus(UserToolAccess.Status.REVOKED);
            existing.setRevokedAt(LocalDateTime.now());

            // ⚠️ ici tu devras récupérer l’entité DUsers correspondant à revokedByUserId
            // (via DUsersRepository) et faire un setRevokedBy(user)
            // Pour l’instant on laisse null ou un setter direct

            return repository.save(existing);
        });
    }

    public boolean deleteAccess(Integer id) {
        return repository.findById(id).map(existing -> {
            repository.delete(existing);
            return true;
        }).orElse(false);
    }
}
