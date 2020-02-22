package org.launchcode.MigraineManager.data;

import org.launchcode.MigraineManager.models.Symptoms;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymptomsRepository extends CrudRepository<Symptoms, Integer> {
}
