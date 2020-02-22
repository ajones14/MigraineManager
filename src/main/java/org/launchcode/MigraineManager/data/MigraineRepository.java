package org.launchcode.MigraineManager.data;

import org.launchcode.MigraineManager.models.Migraine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MigraineRepository extends CrudRepository<Migraine, Integer> {
}
