package com.example.spring_microservice_proxy.endpoints;

import com.example.spring_microservice_proxy.dto.ComplaintDTO;
import com.example.spring_microservice_proxy.repositories.ComplaintJPAEntity;
import com.example.spring_microservice_proxy.services.ComplaintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintsEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(ComplaintsEndpoint.class);

    @Autowired
    private ComplaintService complaintService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user', 'technician')")
    public List<ComplaintDTO> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user', 'technician')")
    public ResponseEntity<ComplaintJPAEntity> getComplaintById(@PathVariable Long id) {
        Optional<ComplaintJPAEntity> complaint = complaintService.getComplaintById(id);
        return complaint.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('technician')")
    public ComplaintJPAEntity createComplaint(@RequestBody ComplaintJPAEntity complaint) {
        return complaintService.addComplaint(complaint);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('technician')")
    public ResponseEntity<ComplaintJPAEntity> updateComplaint(@PathVariable Long id, @RequestBody ComplaintJPAEntity updatedComplaint) {
        ComplaintJPAEntity complaint = complaintService.updateComplaint(id, updatedComplaint);
        return ResponseEntity.ok(complaint);
    }
}