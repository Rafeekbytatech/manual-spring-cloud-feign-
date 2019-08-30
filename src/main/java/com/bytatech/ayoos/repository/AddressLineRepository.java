package com.bytatech.ayoos.repository;

import com.bytatech.ayoos.domain.AddressLine;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AddressLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressLineRepository extends JpaRepository<AddressLine, Long> {

	List<AddressLine> findByPatientId(@Param("patientId")Long patientId);
	
}
