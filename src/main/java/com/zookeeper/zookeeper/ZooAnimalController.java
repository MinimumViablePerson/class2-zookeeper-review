package com.zookeeper.zookeeper;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ZooAnimalController {
  @Autowired
  private ZooAnimalRepository zooAnimalRepo;

  @GetMapping("/zoo-animals")
  public List<ZooAnimal> getZooAnimals() {
    return zooAnimalRepo.findAll();
  }

  @GetMapping("/zoo-animals/{id}")
  public ResponseEntity<ZooAnimal> getSingleZooAnimal(@PathVariable Integer id) {
    Optional<ZooAnimal> match = zooAnimalRepo.findById(id);

    if (match.isPresent()) {
      return new ResponseEntity<>(match.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/zoo-animals")
  public ZooAnimal createZooAnimal(@RequestBody ZooAnimal zooAnimalData) {
    return zooAnimalRepo.save(zooAnimalData);
  }

  @DeleteMapping("/zoo-animals/{id}")
  public ResponseEntity<Reply> deleteZooAnimal(@PathVariable Integer id) {
    Reply reply = new Reply();

    if (zooAnimalRepo.existsById(id)) {
      zooAnimalRepo.deleteById(id);
      reply.message = "It worked!";
      return new ResponseEntity<>(reply, HttpStatus.OK);
    } else {
      reply.error = "Not found.";
      return new ResponseEntity<>(reply, HttpStatus.NOT_FOUND);
    }
  }

  @PatchMapping("/zoo-animals/{id}")
  public ZooAnimal updateZooAnimal(@RequestBody ZooAnimal userSentData, @PathVariable Integer id) {
    Optional<ZooAnimal> match = zooAnimalRepo.findById(id);

    if (match.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
    }

    ZooAnimal zooAnimal = match.get();
    zooAnimal.isHungry = userSentData.isHungry == null ? zooAnimal.isHungry : userSentData.isHungry;
    zooAnimal.name = userSentData.name == null ? zooAnimal.name : userSentData.name;
    zooAnimal.species = userSentData.species == null ? zooAnimal.species : userSentData.species;
    zooAnimal.origin = userSentData.origin == null ? zooAnimal.origin : userSentData.origin;

    return zooAnimalRepo.save(zooAnimal);
  }
}

class Reply {
  public String message;
  public String error;
}

interface ZooAnimalRepository extends JpaRepository<ZooAnimal, Integer> {
}
