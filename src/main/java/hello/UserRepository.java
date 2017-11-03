package hello;

import org.springframework.beans.factory.annotation.Autowired;
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
   long findUserIdByUsername(String username);

   @Modifying
   @Transactional
   @Query("update User set userid = :userid where username=:username")
   void setUserIdByUsername(@Param("username") String username, @Param("userid") long userid);

   @Modifying
   @Transactional
   @Query("update User set password = :password where userid=:userid")
   void setPasswordbyUsername(@Param("userid") long userid, @Param("password") String password);

   @Query("select u from User u, UserRole ur where u.userid = ur.userid and ur.role = :role")
   List<User> findAllUserByRole(@Param("role") String role);

}