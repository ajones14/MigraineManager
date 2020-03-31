package org.launchcode.MigraineManager.data;

import org.launchcode.MigraineManager.models.Migraine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MigraineRepository extends CrudRepository<Migraine, Integer> {

    List<Migraine> findAllByUserId(int userId);


}
