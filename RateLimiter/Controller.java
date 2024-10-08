package RateLimiter.RateLimiter;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import RateLimiter.RateLimiter.Entity.ApiAllowedhits;
import RateLimiter.RateLimiter.Entity.UserLogin;
import RateLimiter.RateLimiter.Repository.ApiAllowedhitsRepository;
import RateLimiter.RateLimiter.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RestController
public class Controller {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ApiAllowedhitsRepository apiAllowedhitsRepository;

	public static String userid;
	public static int ip;

	@PostMapping("/login") // to store user details
	public UserLogin getUsername(@RequestBody UserLogin userdetail) {
		userdetail = userRepository.save(userdetail);
		userid = userdetail.getUserid();
		ip = userdetail.getIp();
		return userdetail;
	}

	@GetMapping("/get-user-data") // to see the users details
	public List<UserLogin> getdata() {
		List<UserLogin> u = userRepository.findAll();

		return u;

	}

	@PostMapping("/post-static-data") // to post count limit for api
	public ApiAllowedhits savedata(@RequestBody ApiAllowedhits apiAllowdhits) {
		ApiAllowedhits currentdata = apiAllowedhitsRepository.save(apiAllowdhits);
		StaticData.toPostDataInCache(currentdata);
		return currentdata;
	}

	@GetMapping("/get-static-data") // to view limit count of apis
	public List<ApiAllowedhits> getdaaata() {
		log.info("/getapillowedhitdata api is hit");
		List<ApiAllowedhits> apiAllowedhits = apiAllowedhitsRepository.findAll();
		return apiAllowedhits;
	}


	@GetMapping("/home")
	public String getSt() {
		log.info("home api is hit");
		return "home";
	}

	@GetMapping("/hello")
	public String getHello() {
		log.info("home api is hit");

		return "hello";
	}

}
