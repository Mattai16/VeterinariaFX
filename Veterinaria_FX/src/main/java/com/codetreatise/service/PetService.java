package com.codetreatise.service;

import com.codetreatise.bean.Pet;
import com.codetreatise.generic.GenericService;

public interface PetService extends GenericService<Pet> {
    boolean authenticate(String email, String password);

    Pet findByEmail(String email);

}
