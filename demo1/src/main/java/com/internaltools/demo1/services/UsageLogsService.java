package com.internaltools.demo1.services;

import com.internaltools.demo1.entities.UsageLogs;
import com.internaltools.demo1.repositories.UsageLogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsageLogsService {

    private final UsageLogsRepository repository;

    public List<UsageLogs> getAllLogs() {
        return repository.findAll();
    }

    public Optional<UsageLogs> getLogById(Integer id) {
        return repository.findById(id);
    }

    public UsageLogs createLog(UsageLogs log) {
        return repository.save(log);
    }

    public Optional<UsageLogs> updateLog(Integer id, UsageLogs updatedLog) {
        return repository.findById(id).map(existing -> {
            existing.setDUsers(updatedLog.getDUsers());
            existing.setTools(updatedLog.getTools());
            existing.setSessionDate(updatedLog.getSessionDate());
            existing.setUsageMinutes(updatedLog.getUsageMinutes());
            existing.setActionsCount(updatedLog.getActionsCount());
            return repository.save(existing);
        });
    }

    public boolean deleteLog(Integer id) {
        return repository.findById(id).map(log -> {
            repository.delete(log);
            return true;
        }).orElse(false);
    }

    public List<UsageLogs> getLogsByUser(Long userId) {
        return repository.findBydUsers_Id(userId);
    }

    public List<UsageLogs> getLogsByTool(Integer toolId) {
        return repository.findByTools_Id(toolId);
    }
}
