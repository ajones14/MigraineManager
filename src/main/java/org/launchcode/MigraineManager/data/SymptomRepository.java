package org.launchcode.MigraineManager.data;

import org.launchcode.MigraineManager.models.Symptom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymptomRepository extends CrudRepository<Symptom, Integer> {

    Iterable<Symptom> findAllByUserId(int userId);

}
