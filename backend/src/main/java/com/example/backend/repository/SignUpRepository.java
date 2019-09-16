package com.example.backend.repository;

import com.example.backend.model.SignUpEmail;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface SignUpRepository extends CrudRepository<SignUpEmail, Long> {
	Optional<SignUpEmail> findByEmail(String email);
}