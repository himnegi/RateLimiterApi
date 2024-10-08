package RateLimiter.RateLimiter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;
import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.WritePolicy;


@Component
public class DynamicData {

	public static Record findDynamicRecord(AerospikeClient client1, WritePolicy writePolicy, String userid,
			String apiurl) {
		Bin bin1, bin2, bin3;
		LocalDateTime currentdatetime = LocalDateTime.now();
		Key key = new Key("test", "dynamicdata", userid + apiurl);
		Record getrecord = client1.get(null, key);// Read a record from aerospike
		if (getrecord == null) {
			bin1 = new Bin("time", currentdatetime.toString());
			bin2 = new Bin("mincount", 1);
			bin3 = new Bin("hourcount", 1);
		} else {
			LocalDateTime apidateTime = LocalDateTime.parse((CharSequence) getrecord.getValue("time"));
			long elapsedSeconds = ChronoUnit.SECONDS.between(apidateTime, currentdatetime);
			if (elapsedSeconds >= 60) {
				bin1 = new Bin("time", currentdatetime.toString());
				bin2 = new Bin("mincount", 1);
				bin3 = new Bin("hourcount", getrecord.getInt("hourcount") + 1);
			} else {
				bin1 = new Bin("time", getrecord.getValue("time"));
				bin2 = new Bin("mincount", getrecord.getInt("mincount") + 1);
				bin3 = new Bin("hourcount", getrecord.getInt("hourcount") + 1);
			}
		}
		client1.put(writePolicy, key, bin1, bin2, bin3);
		return client1.get(null, key);
	}

	public static Record userDynamicRecord(AerospikeClient client1, WritePolicy writePolicy, String apiurl,
			String userid, int userip) {
		Bin bin1, bin2, bin3;
		LocalDateTime currendatetime = LocalDateTime.now();
		Key key = new Key("test", "dynamicdata", userid + userip + apiurl);
		Record getrecord = client1.get(null, key);// Read a record from aerospike
		if (getrecord == null) {
			bin1 = new Bin("time", currendatetime.toString());
			bin2 = new Bin("mincount", 1);
			bin3 = new Bin("hourcount", 1);
		} else {
			LocalDateTime apidateTime = LocalDateTime.parse((CharSequence) getrecord.getValue("time"));

			long elapsedSeconds = ChronoUnit.SECONDS.between(apidateTime, currendatetime);

			if (elapsedSeconds >= 60) {
				bin1 = new Bin("time", currendatetime.toString());
				bin2 = new Bin("mincount", 1);
				bin3 = new Bin("hourcount", getrecord.getInt("hourcount") + 1);
			} else {
				bin1 = new Bin("time", getrecord.getValue("time"));
				bin2 = new Bin("mincount", getrecord.getInt("mincount") + 1);
				bin3 = new Bin("hourcount", getrecord.getInt("hourcount") + 1);
			}
		}
		client1.put(writePolicy, key, bin1, bin2, bin3); // to write a record on aerospike
		return client1.get(null, key);
	}

	public static Record apiDynamicRecord(AerospikeClient client1, WritePolicy writePolicy, String apiurl) {

		Bin bin1, bin2, bin3;
		LocalDateTime currentdatetime = LocalDateTime.now();
		Key key = new Key("test", "dynamicdata", apiurl);
		Record getrecord = client1.get(null, key);// Read a record from aerospike
		if (getrecord == null) {
			bin1 = new Bin("time", currentdatetime.toString());
			bin2 = new Bin("mincount", 1);
			bin3 = new Bin("hourcount", 1);
		} else {
			LocalDateTime apidateTime = LocalDateTime.parse((CharSequence) getrecord.getValue("time"));
			long elapsedSeconds = ChronoUnit.SECONDS.between(apidateTime, currentdatetime);
			if (elapsedSeconds >= 60) {
				bin1 = new Bin("time", currentdatetime.toString());
				bin2 = new Bin("mincount", 1);
				bin3 = new Bin("hourcount", getrecord.getInt("hourcount") + 1);
			} else {
				bin1 = new Bin("time", getrecord.getValue("time"));
				bin2 = new Bin("mincount", getrecord.getInt("mincount") + 1);
				bin3 = new Bin("hourcount", getrecord.getInt("hourcount") + 1);
			}
		}
		client1.put(writePolicy, key, bin1, bin2, bin3);
		return client1.get(null, key);
	}

	public static Record findIpApiRecord(AerospikeClient client1, WritePolicy writePolicy, int userip, String apiurl) {
		Bin bin1, bin2, bin3;
		LocalDateTime currentdatetime = LocalDateTime.now();
		Key key = new Key("test", "dynamicdata", userip + apiurl);
		Record getrecord = client1.get(null, key);// Read a record from aerospike
		if (getrecord == null) {
			bin1 = new Bin("time", currentdatetime.toString());
			bin2 = new Bin("mincount", 1);
			bin3 = new Bin("hourcount", 1);
		} else {
			LocalDateTime apidateTime = LocalDateTime.parse((CharSequence) getrecord.getValue("time"));
			long elapsedSeconds = ChronoUnit.SECONDS.between(apidateTime, currentdatetime);
			if (elapsedSeconds >= 60) {
				bin1 = new Bin("time", currentdatetime.toString());
				bin2 = new Bin("mincount", 1);
				bin3 = new Bin("hourcount", getrecord.getInt("hourcount") + 1);
			} else {
				bin1 = new Bin("time", getrecord.getValue("time"));
				bin2 = new Bin("mincount", getrecord.getInt("mincount") + 1);
				bin3 = new Bin("hourcount", getrecord.getInt("hourcount") + 1);
			}
		}
		client1.put(writePolicy, key, bin1, bin2, bin3);
		return client1.get(null, key);
	}


}
