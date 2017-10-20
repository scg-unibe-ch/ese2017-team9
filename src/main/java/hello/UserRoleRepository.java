package hello;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    @Query("select a.role from UserRole a, User b where b.username=?1 and a.userid=b.userid")
    List<String> findRoleByUserName(String username);

    @Transactional
    void removeAllByUserid(long userId);

    @Transactional
    void removeByUseridAndRole(long userId, String role);

    @Transactional
    UserRole findByUseridAndRole(long userId, String role);

}
