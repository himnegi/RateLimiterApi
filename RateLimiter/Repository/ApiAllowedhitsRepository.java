package RateLimiter.RateLimiter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import RateLimiter.RateLimiter.Entity.ApiAllowedhits;

@Repository
public interface ApiAllowedhitsRepository extends JpaRepository<ApiAllowedhits, Integer> {

	ApiAllowedhits findByApiname(String apiurl);

	ApiAllowedhits findBySno(int sno);

}
