package vn.edu.uit.ms.qltt.cuoiky.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.edu.uit.ms.qltt.cuoiky.database.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
