package RateLimiter.RateLimiter.Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Record;
import com.aerospike.client.policy.WritePolicy;
import com.google.gson.Gson;
import RateLimiter.RateLimiter.StaticData;
import RateLimiter.RateLimiter.Controller;
import RateLimiter.RateLimiter.DynamicData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DoFilter implements Filter {
	static String userid;
	static int userip;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		AerospikeClient client1 = new AerospikeClient("192.168.56.6", 3000); // to connect to static data
		AerospikeClient client2 = new AerospikeClient("192.168.56.6", 3000);// to connect to dynamic data
		WritePolicy writePolicy = new WritePolicy(); // setting 1 hour expiration time for dynamic data
		writePolicy.expiration = 60 * 60;
		writePolicy.sendKey = true;
		HttpServletRequest http = (HttpServletRequest) request;
		String apiurl = http.getRequestURI();

		Record user_static_record = StaticData.findStaticRecordFromCache(client1, apiurl + "truetrue");// to search the
																										// allowed
		Record api_static_record = StaticData.findStaticRecordFromCache(client1, apiurl + "falsefalse");// count from
																										// static
		Record ip_static_record = StaticData.findStaticRecordFromCache(client1, apiurl + "falsetrue");// data

		if (apiurl.equals("/login") || apiurl.equals("/post-static-data")
				|| apiurl.contentEquals("/api-exceed-details")) {
			chain.doFilter(request, response);
			userid = Controller.userid;
			userip = Controller.ip;
		}

		else if (userid != null && userip != 0) { // case 1- userid and ipaddrss not null
			if (user_static_record == null || api_static_record == null || ip_static_record == null)
				log.info("Record not found in static data");

			Record dynamic_record = DynamicData.findDynamicRecord(client2, writePolicy, userid, apiurl);
			Record api_dynamic_record = DynamicData.apiDynamicRecord(client2, writePolicy, apiurl);
			Record user_dynamic_record = DynamicData.userDynamicRecord(client2, writePolicy, apiurl, userid, userip);

			log.info("************************** USER INFO**********************");
			log.info("userid :{} ,ip address :{} ", userid, userip);
			log.info("allowed ip limit in min :{} hit made in min :{}", ip_static_record.getInt("mincount"),
					user_dynamic_record.getInt("mincount"));
			log.info("allowed ip limit in hour :{} hit made in hour :{}", ip_static_record.getInt("hourcount"),
					user_dynamic_record.getInt("hourcount"));
			log.info("allowed total limit in min :{} hit made  in min :{} ", user_static_record.getInt("mincount"),
					dynamic_record.getInt("mincount"));
			log.info("allowed total limit in hour :{} hit made in hour :{}", user_static_record.getInt("hourcount"),
					dynamic_record.getInt("hourcount"));

			log.info("************************** API INFO***********************");
			log.info("api name :{}_allowed count in min :{}  hit made in min :{} ", apiurl,
					api_static_record.getInt("mincount"), api_dynamic_record.getInt("mincount"));
			log.info("allowed count in hour :{} hit made in hour :{} ", api_static_record.getInt("hourcount"),
					api_dynamic_record.getInt("hourcount"));


			if ((dynamic_record.getInt("mincount") <= user_static_record.getInt("mincount"))
					&& (dynamic_record.getInt("hourcount") <= user_static_record.getInt("hourcount"))
					&& (api_dynamic_record.getInt("mincount") <= api_static_record.getInt("mincount"))
					&& (api_dynamic_record.getInt("hourcount") <= api_static_record.getInt("hourcount"))
					&& (user_dynamic_record.getInt("mincount") <= ip_static_record.getInt("mincount"))
					&& (user_dynamic_record.getInt("hourcount") <= ip_static_record.getInt("hourcount"))) {
				chain.doFilter(request, response);
			}

			else {
				log.info("\napi hit limit exceed");
				String json = new Gson().toJson("Exceeded api hit limit");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}

		}

		else if (userid == null && userip != 0) { // case 2 when userid=null
			if (user_static_record == null || api_static_record == null || ip_static_record == null)
				log.info("Record not found in stataic data");
			Record dynamic_record = DynamicData.findIpApiRecord(client2, writePolicy, userip, apiurl);
			Record api_dynamic_record = DynamicData.apiDynamicRecord(client2, writePolicy, apiurl);

			if ((dynamic_record.getInt("mincount") <= ip_static_record.getInt("mincount"))
					&& (dynamic_record.getInt("hourcount") <= ip_static_record.getInt("hourcount"))
					&& (api_dynamic_record.getInt("mincount") <= api_static_record.getInt("mincount"))
					&& (api_dynamic_record.getInt("hourcount") <= api_static_record.getInt("hourcount"))) {
				chain.doFilter(request, response);
			}

			else {
				String json = new Gson().toJson("Exceeded api hit limit");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);

				log.info("\napi hit limit exceed");

			}


		}

	}

	@Override
	public void destroy() {

	}

}

