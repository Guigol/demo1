package com.internaltools.demo1.services;

import com.internaltools.demo1.entities.CostTracking;
import com.internaltools.demo1.repositories.CostTrackingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CostTrackingService {

    private final CostTrackingRepository repository;

    public List<CostTracking> getAllCosts() {
        return repository.findAll();
    }

    public Optional<CostTracking> getCostById(Integer id) {
        return repository.findById(id);
    }

    public CostTracking createCost(CostTracking cost) {
        return repository.save(cost);
    }

    public Optional<CostTracking> updateCost(Integer id, CostTracking updatedCost) {
        return repository.findById(id).map(existing -> {
            existing.setTools(updatedCost.getTools());
            existing.setMonthYear(updatedCost.getMonthYear());
            existing.setTotalMonthlyCost(updatedCost.getTotalMonthlyCost());
            existing.setActiveUsersCount(updatedCost.getActiveUsersCount());
            return repository.save(existing);
        });
    }

    public boolean deleteCost(Integer id) {
        return repository.findById(id).map(cost -> {
            repository.delete(cost);
            return true;
        }).orElse(false);
    }
}
