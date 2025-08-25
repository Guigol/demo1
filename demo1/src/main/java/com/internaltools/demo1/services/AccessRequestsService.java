package com.internaltools.demo1.services;

import com.internaltools.demo1.entities.AccessRequests;
import com.internaltools.demo1.entities.DUsers;
import com.internaltools.demo1.repositories.AccessRequestsRepository;
import com.internaltools.demo1.repositories.DUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccessRequestsService {

    private final AccessRequestsRepository accessRequestsRepository;
    private final DUsersRepository dUsersRepository;

    // Récupérer toutes les demandes
    public List<AccessRequests> getAllRequests() {
        return accessRequestsRepository.findAll();
    }

    // Récupérer les demandes d’un utilisateur
    public List<AccessRequests> getRequestsByUser(Long userId) {
        return accessRequestsRepository.findByDusers_Id(userId);
    }

    // Récupérer une demande par son ID
    public Optional<AccessRequests> getRequestById(Long requestId) {
        return accessRequestsRepository.findById(requestId);
    }

    // Créer une nouvelle demande
    public AccessRequests createRequest(AccessRequests request) {
        request.setStatus(AccessRequests.Status.PENDING);
        request.setRequestedAt(LocalDateTime.now());
        return accessRequestsRepository.save(request);
    }

    // Approuver une demande
    public Optional<AccessRequests> approveRequest(Long requestId, Long processedById, String notes) {
        return accessRequestsRepository.findById(requestId).map(request -> {
            DUsers processedBy = dUsersRepository.findById(processedById)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            request.setStatus(AccessRequests.Status.APPROVED);
            request.setProcessedBy(processedBy);
            request.setProcessedAt(LocalDateTime.now());
            request.setProcessingNotes(notes);

            return accessRequestsRepository.save(request);
        });
    }

    // Rejeter une demande
    public Optional<AccessRequests> rejectRequest(Long requestId, Long processedById, String notes) {
        return accessRequestsRepository.findById(requestId).map(request -> {
            DUsers processedBy = dUsersRepository.findById(processedById)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            request.setStatus(AccessRequests.Status.REJECTED);
            request.setProcessedBy(processedBy);
            request.setProcessedAt(LocalDateTime.now());
            request.setProcessingNotes(notes);

            return accessRequestsRepository.save(request);
        });
    }
}
