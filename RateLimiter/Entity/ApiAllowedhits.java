package RateLimiter.RateLimiter.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
@Entity
@Table(name = "apiallowdhits")
public class ApiAllowedhits {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer sno;
	@Column(name = "apiname")
	private String apiname;
	@Column(name = "mincount")
	private int mincount;
	@Column(name = "hourcount")
	private int hourcount;

	public ApiAllowedhits() {}

}
