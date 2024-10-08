package RateLimiter.RateLimiter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import RateLimiter.RateLimiter.Entity.UserLogin;

@Repository
public interface UserRepository extends JpaRepository<UserLogin,Integer> {

}
