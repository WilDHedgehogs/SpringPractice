package ru.appline.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PetModel implements Serializable {

    private static final PetModel instance = new PetModel();
    private final Map<Integer, Pet> model;

    private PetModel() {

        model = new HashMap<Integer, Pet>();

    }

    public static PetModel getInstance() {

        return instance;

    }

    public void add(Pet pet, int id) {

        model.put(id, pet);

    }

    public Pet getFromList(int id) {

        return model.get(id);

    }

    public Map<Integer, Pet> getAll() {

        return model;

    }

    public void delete(int id) {

        model.remove(id);

    }

    public void deletAll() {

        model.clear();

    }

    public void update(Pet pet, int id) {

        model.replace(id, pet);

    }

    public boolean contains(int id) {

        if (model.containsKey(id)) {

            return true;

        } else {

            return false;

        }

    }

}
