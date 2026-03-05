package com.company.saas.repository;

import com.company.saas.entity.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LicenseRepository extends JpaRepository<License, Long> {

    List<License> findByLastUsedDateBefore(LocalDate date);

}