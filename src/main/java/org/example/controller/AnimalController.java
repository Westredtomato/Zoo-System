package org.example.controller;

import org.example.model.Animal;
import org.example.service.AnimalService;

import java.sql.SQLException;
import java.util.List;

/**
 * Framework-agnostic controller (contract) for Animal operations.
 * Intended HTTP mappings (example):
 * - GET /api/animals -> listAll()
 * - GET /api/animals/{id} -> get(id)
 * - POST /api/animals -> create(body)
 * - PUT /api/animals/{id} -> update(body)
 * - DELETE /api/animals/{id} -> delete(id)
 * - POST /api/animals/{id}/transfer -> transfer(animalId, targetEnclosureId)
 */
public class AnimalController {
    private final AnimalService animalService = new AnimalService();

    public List<Animal> listAll() throws SQLException {
        return animalService.listAllAnimals();
    }

    public Animal get(String animalId) throws SQLException {
        return animalService.getAnimal(animalId);
    }

    public void create(Animal animal) throws SQLException {
        animalService.createAnimal(animal);
    }

    public void update(Animal animal) throws SQLException {
        animalService.updateAnimal(animal);
    }

    public void delete(String animalId) throws SQLException {
        animalService.deleteAnimal(animalId);
    }

    public void transfer(String animalId, String targetEnclosureId) throws SQLException {
        animalService.transferAnimal(animalId, targetEnclosureId);
    }
}
