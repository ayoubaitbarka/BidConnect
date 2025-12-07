package com.example.tenderservice.repository;

import com.example.tenderservice.entity.Tender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenderRepository extends JpaRepository<Tender, Long> {

   List<Tender> findByOrganizationId(Long organizationId);
   List<Tender>  findByOwnerUserId(String ownerUserId);

}
