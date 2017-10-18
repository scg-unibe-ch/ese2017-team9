package hello;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
   User findByUsername(String username);
   List<User> findAll();
   User findByUserid(long id);
   List<User> findByFirstname(String firstname);
   void removeByUserid(Long userid);

}