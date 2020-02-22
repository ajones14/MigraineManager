package org.launchcode.MigraineManager.data;

import org.launchcode.MigraineManager.models.DaysTriggers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaysTriggersRepository extends CrudRepository<DaysTriggers, Integer> {
}
