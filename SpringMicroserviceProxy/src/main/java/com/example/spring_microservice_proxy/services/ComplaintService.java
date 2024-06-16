package com.example.spring_microservice_proxy.services;

import com.example.spring_microservice_proxy.dto.ComplaintDTO;
import com.example.spring_microservice_proxy.repositories.ComplaintJPAEntity;
import com.example.spring_microservice_proxy.repositories.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public List<ComplaintDTO> getAllComplaints() {
        List<ComplaintJPAEntity> complaints = complaintRepository.findAll();
        return complaints.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ComplaintJPAEntity> getComplaintById(Long id) {
        return complaintRepository.findById(id);
    }

    public ComplaintJPAEntity addComplaint(ComplaintJPAEntity complaint) {
        complaint.setComplaintNumber(UUID.randomUUID().toString()); // Ustawienie numeru reklamacji
        complaint.setCreatedAt(Instant.now()); // Ustawienie daty dodania
        return complaintRepository.save(complaint);
    }

    public ComplaintJPAEntity updateComplaint(Long id, ComplaintJPAEntity updatedComplaint) {
        return complaintRepository.findById(id).map(complaint -> {
            complaint.setCompanyName(updatedComplaint.getCompanyName());
            complaint.setDeviceModel(updatedComplaint.getDeviceModel());
            complaint.setFaultDescription(updatedComplaint.getFaultDescription());
            complaint.setComplaintStatus(updatedComplaint.getComplaintStatus());
            return complaintRepository.save(complaint);
        }).orElseGet(() -> {
            updatedComplaint.setId(id);
            updatedComplaint.setComplaintNumber(UUID.randomUUID().toString()); // Ustawienie numeru reklamacji
            updatedComplaint.setCreatedAt(Instant.now()); // Ustawienie daty dodania
            return complaintRepository.save(updatedComplaint);
        });
    }

    @PostConstruct
    public void initData() {
        for (int i = 1; i <= 20; i++) {
            ComplaintJPAEntity complaint = new ComplaintJPAEntity();
            complaint.setCompanyName("Company " + i);
            complaint.setDeviceModel("Model " + i);
            complaint.setFaultDescription("Description " + i);
            complaint.setComplaintStatus("W trakcie realizacji");
            complaint.setCreatedAt(Instant.now()); // Ustawienie daty dodania
            complaintRepository.save(complaint);
        }
    }

    private ComplaintDTO convertToDTO(ComplaintJPAEntity complaint) {
        ComplaintDTO dto = new ComplaintDTO();
        dto.setId(complaint.getId());
        dto.setCompanyName(complaint.getCompanyName());
        dto.setDeviceModel(complaint.getDeviceModel());
        dto.setFaultDescription(complaint.getFaultDescription());
        dto.setComplaintNumber(complaint.getComplaintNumber());
        dto.setComplaintStatus(complaint.getComplaintStatus());
        dto.setCreatedAt(complaint.getCreatedAt());
        return dto;
    }
}
