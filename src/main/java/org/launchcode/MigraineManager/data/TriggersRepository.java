package org.launchcode.MigraineManager.data;

import org.launchcode.MigraineManager.models.Triggers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriggersRepository extends CrudRepository<Triggers, Integer> {
}
