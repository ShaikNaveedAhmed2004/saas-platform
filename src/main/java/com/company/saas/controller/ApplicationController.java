package com.company.saas.controller;

import com.company.saas.dto.ApplicationDTO;
import com.company.saas.entity.Application;
import com.company.saas.service.ApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apps")

public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public Application addApplication(@RequestBody ApplicationDTO dto) {

        return applicationService.addApplication(dto);
    }

    @GetMapping
    public List<Application> getApplications() {

        return applicationService.getApplications();
    }

    @GetMapping("/{id}")
    public Application getApplication(@PathVariable Long id) {

        return applicationService.getApplication(id);
    }

    @PutMapping("/{id}")
    public Application updateApplication(@PathVariable Long id,
                                         @RequestBody ApplicationDTO dto) {

        return applicationService.updateApplication(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable Long id) {

        applicationService.deleteApplication(id);

        return "Application deleted successfully";
    }

}