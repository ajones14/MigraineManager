package org.launchcode.MigraineManager.data;

import org.launchcode.MigraineManager.models.Symptom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SymptomRepository extends CrudRepository<Symptom, Integer> {

    List<Symptom> findAllByUserId(int userId);

}
