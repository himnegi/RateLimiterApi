package RateLimiter.RateLimiter.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@Entity
@Table(name = "userlogin")
public class UserLogin {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="userid")
	private String userid;
	@Column(name = "ip")
	private int ip;
	@Column(name = "password")
	private String password;

	public UserLogin() {
	}

}
