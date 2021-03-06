package com.carpedia.carpedia.controller;

import com.carpedia.carpedia.model.EngineModel;
import com.carpedia.carpedia.repository.EngineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

@CrossOrigin
@RestController
public class EngineController {

    @Autowired
    EntityManager entityManager;

    @Autowired
    EngineRepository engine;


    @GetMapping("/engine")
    public List<EngineModel> getAllEngines() {
        return (List<EngineModel>) engine.findAll();
    }

    @GetMapping("/engine/id/{id}")
    public EngineModel getEngineById(@PathVariable long id) {
        return engine.findById(id);
    }

    @GetMapping("/engine/name/{name}")
    public List<EngineModel> getEngineByName(@PathVariable String name) {
        return engine.findAllByName(name);
    }

    @GetMapping("/engine/power/{power}")
    public List<EngineModel> getEngineByPower(@PathVariable int power) {
        return engine.findAllByPower(power);
    }

    @GetMapping("/engine/capacity/{capacity}")
    public List<EngineModel> getEngineByCapacity(@PathVariable int capacity) {
        return engine.findAllByCapacity(capacity);
    }

    @PostMapping("/engine/save")
    @Transactional
    public String saveEngine(@RequestBody EngineModel engineModel) {
        try {
            if (getEngineByIdForSave(engineModel.getId()) != null || doesEngineExist(engineModel.getName()) ) {
                return "Engine already exists, or incorrect input format";
            }
            else {
                engine.save(engineModel);
                return "Engine saved";
            }
        }
        catch (Exception exc) {
            return "Not saved. Exception: " + exc.getMessage();
        }
    }

    @PutMapping("engine/update")
    @Transactional
    public String updateEngine(@RequestBody EngineModel engineModel) {
        try {
            entityManager.createQuery("UPDATE EngineModel engineModel " +
                    "SET engineModel.name=?2, engineModel.power=?3, engineModel.capacity=?4 WHERE engineModel.id=?1")
                    .setParameter(1, engineModel.getId())
                    .setParameter(2, engineModel.getName())
                    .setParameter(3, engineModel.getPower())
                    .setParameter(4, engineModel.getCapacity())
                    .executeUpdate();
            return "Engine updated";
        }
        catch (Exception exc){
            return "Not updated. Exception: " + exc.getMessage();
        }
    }

    @DeleteMapping("/engine/delete")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public String deleteEngine(@RequestParam("id") long id) {
        try {
            engine.deleteById(id);
            return "Engine Deleted";
        }
        catch (Exception exc) {
            return "Not deleted. Exception: " + exc.getMessage();
        }
    }

    private EngineModel getEngineByIdForSave(long id) {
        return engine.findById(id);
    }

    private boolean doesEngineExist(String name) {
        return (engine.findByName(name) != null);
    }

}
