package com.codetreatise.service.impl;

import com.codetreatise.bean.Pet;
import com.codetreatise.bean.User;
import com.codetreatise.repository.PetRepository;

import com.codetreatise.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {


    @Autowired
    private PetRepository petRepository;

    @Override
    public Pet save(Pet entity) {
        return petRepository.save(entity);
    }

    @Override
    public Pet update(Pet entity) {
        return petRepository.save(entity);
    }

    @Override
    public void delete(Pet entity) {
        petRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {

    }



    @Override
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public boolean authenticate(String username, String password){
        Pet pet = this.findByEmail(username);
        if(pet == null){
            return false;
        }else{
            if(password.equals(pet.getPassword())) return true;
            else return false;
        }
    }

    @Override
    public Pet findByEmail(String email) {
        return petRepository.findByEmail(email);
    }

    @Override
    public void deleteInBatch(List<Pet> users) {
        petRepository.deleteInBatch(users);
    }

    @Override
    public Pet find(Long id) {
        return null;
    }

}
