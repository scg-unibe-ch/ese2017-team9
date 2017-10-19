package Repository;

import PersistanceObject.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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

   @Modifying
   @Transactional
   @Query("update User set password = :password where userid=:userid")
   void setPasswordbyUsername(@Param("userid") long userid, @Param("password") String password);
}