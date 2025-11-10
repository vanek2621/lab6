package com.lab6.service;

import com.lab6.model.Company;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CompanyService {
    private final Map<Long, Company> companies = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Company> getAllCompanies() {
        return new ArrayList<>(companies.values());
    }

    public Optional<Company> getCompanyById(Long id) {
        return Optional.ofNullable(companies.get(id));
    }

    public Company createCompany(Company company) {
        if (company.getName() == null || company.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be empty");
        }

        Company newCompany = new Company();
        newCompany.setId(idGenerator.getAndIncrement());
        newCompany.setName(company.getName().trim());
        newCompany.setDescription(company.getDescription() != null ? company.getDescription().trim() : "");
        newCompany.setAddress(company.getAddress() != null ? company.getAddress().trim() : "");
        newCompany.setPhone(company.getPhone() != null ? company.getPhone().trim() : "");
        newCompany.setWebsite(company.getWebsite() != null ? company.getWebsite().trim() : "");
        newCompany.setCreatedAt(LocalDateTime.now());
        newCompany.setUpdatedAt(LocalDateTime.now());

        companies.put(newCompany.getId(), newCompany);
        return newCompany;
    }

    public Company updateCompany(Long id, Company company) {
        Company existingCompany = companies.get(id);
        if (existingCompany == null) {
            throw new NoSuchElementException("Company with id " + id + " not found");
        }

        if (company.getName() != null && !company.getName().trim().isEmpty()) {
            existingCompany.setName(company.getName().trim());
        }
        if (company.getDescription() != null) {
            existingCompany.setDescription(company.getDescription().trim());
        }
        if (company.getAddress() != null) {
            existingCompany.setAddress(company.getAddress().trim());
        }
        if (company.getPhone() != null) {
            existingCompany.setPhone(company.getPhone().trim());
        }
        if (company.getWebsite() != null) {
            existingCompany.setWebsite(company.getWebsite().trim());
        }
        existingCompany.setUpdatedAt(LocalDateTime.now());

        return existingCompany;
    }

    public void deleteCompany(Long id) {
        Company removed = companies.remove(id);
        if (removed == null) {
            throw new NoSuchElementException("Company with id " + id + " not found");
        }
    }
}

