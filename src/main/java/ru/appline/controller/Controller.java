package ru.appline.controller;

import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {

    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(petModel.getAll().size());

    @PostMapping(value = "/createPet", consumes = "application/json", produces = "application/json")
    public JsonObject createPet(@RequestBody Pet pet) {

        petModel.add(pet, newId.incrementAndGet());
        String text = "Вы создали питомца. Его ID: " + newId;
        JsonObject message = new JsonObject();
        message.addProperty("message", text);
        return message;

    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll() {

        return petModel.getAll();

    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id) {

        return petModel.getFromList(id.get("id"));

    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json", produces = "application/json")
    public JsonObject deletePet(@RequestBody Map<String, Integer> id) {

        JsonObject message = new JsonObject();
        String text;
        if (petModel.contains(id.get("id"))) {

            petModel.delete(id.get("id"));
            text = "Вы удалили питомца с ID: " + id.get("id");
            message.addProperty("message", text);

        } else {

            text = "Питомец с ID: " + id.get("id") + " не найден";
            message.addProperty("message", text);

        }

        return message;

    }

    @DeleteMapping(value = "/deleteAll", produces = "application/json")
    public JsonObject deleteAll() {

        petModel.deletAll();
        String text = "Вы удалили всех питомцев";
        JsonObject message = new JsonObject();
        message.addProperty("message", text);
        return message;

    }

    @PutMapping(value = "/updatePet", consumes = "application/json", produces = "application/json")
    public Map<Integer, Pet> updatePet(@RequestBody JsonObject json) {

        int id = json.get("id").getAsInt();
        String name = json.get("name").getAsString();
        String type = json.get("type").getAsString();
        int age = json.get("age").getAsInt();
        Pet pet = new Pet(name, type, age);
        petModel.update(pet, id);

        return petModel.getAll();

    }

}
