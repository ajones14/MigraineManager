package org.launchcode.MigraineManager.data;

import org.launchcode.MigraineManager.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}
