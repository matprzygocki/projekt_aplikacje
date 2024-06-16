package com.example.spring_microservice_proxy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<ComplaintJPAEntity, Long> {
}
