package com.codetreatise.repository;

import com.codetreatise.bean.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.User;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Pet findByEmail(String email);
}
