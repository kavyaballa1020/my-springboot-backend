package com.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.main.model.Pond;
import com.main.model.Sensor;
import com.main.repository.PondRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/ponds")
@CrossOrigin(origins = "http://localhost:4200")
public class PondController {

    @Autowired
    private PondRepository pondRepository;

    @GetMapping
    public List<Pond> getAllPonds() {
        return pondRepository.findAll();
    }

    @PostMapping
    public Pond addPond(@RequestBody Pond pond) {
        pond.setCreatedAt(LocalDateTime.now());  // Set the created time when adding a new pond
        return pondRepository.save(pond);
    }

    @GetMapping("/{pondId}/sensors")
    public List<Sensor> getSensorsByPond(@PathVariable String pondId) {
        Pond pond = pondRepository.findById(pondId)
                .orElseThrow(() -> new RuntimeException("Pond not found with ID: " + pondId));
        return pond.getSensors();
    }

    @PostMapping("/{pondId}/sensors")
    public Pond addOrUpdateSensorToPond(@PathVariable String pondId, @RequestBody Sensor sensor) {
        Pond pond = pondRepository.findById(pondId)
                .orElseThrow(() -> new RuntimeException("Pond not found with ID: " + pondId));

        pond.getSensors().removeIf(existingSensor -> existingSensor.getType().equals(sensor.getType()));
        sensor.setTimestamp(LocalDateTime.now());
        pond.addSensor(sensor);
        return pondRepository.save(pond);
    }

    @GetMapping("/{pondId}")
    public Optional<Pond> getPondById(@PathVariable String pondId) {
        return pondRepository.findById(pondId);
    }

    @DeleteMapping("/{pondId}")
    public ResponseEntity<?> deletePond(@PathVariable String pondId) {
        if (!pondRepository.existsById(pondId)) {
            return ResponseEntity.notFound().build();
        }
        pondRepository.deleteById(pondId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{pondId}")
    public ResponseEntity<Pond> updatePond(@PathVariable String pondId, @RequestBody Pond updatedPond) {
        return pondRepository.findById(pondId).map(pond -> {
            pond.setName(updatedPond.getName());
            pond.setSensors(updatedPond.getSensors()); // Assuming you want to replace the entire list of sensors
            pondRepository.save(pond);
            return ResponseEntity.ok(pond);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}