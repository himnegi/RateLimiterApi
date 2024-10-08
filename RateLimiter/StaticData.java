package RateLimiter.RateLimiter;

import org.springframework.stereotype.Component;
import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.WritePolicy;
import RateLimiter.RateLimiter.Entity.ApiAllowedhits;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StaticData {

	public static void toPostDataInCache(ApiAllowedhits al) { // to post static data in cache
		AerospikeClient client1 = new AerospikeClient("192.168.56.6", 3000);

		Key key = new Key("test", "staticdata", al.getApiname());
		Bin bin1 = new Bin("hourcount", al.getHourcount());
		Bin bin2 = new Bin("mincount", al.getMincount());
		WritePolicy writePolicy = new WritePolicy();
		writePolicy.expiration = -1;
		writePolicy.sendKey = true;
		client1.put(writePolicy, key, bin1, bin2); // to write a record on aerospike
		Record record = client1.get(null, key);// Read a record from aerospike
		client1.close();
		log.info("Record values are key {} bins {}", key.userKey, record);
	}

	public static Record findStaticRecordFromCache(AerospikeClient client1, String apiurl) { // to search allowed counts
																								// on api from static
																								// data
		Key key = new Key("test", "staticdata", apiurl);
		return client1.get(null, key);

	}

}
