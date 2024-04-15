package in.nilesh.service;

import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.nilesh.binding.Quote;

@Service
public class DashboardServiceImpl implements DashboardService {

	private String quoteUrl = "https://type.fit/api/quotes";

	private Quote[] quotes = null;

	Random r = new Random();
	
	@Override
	public String getQuote() {
		String msg = "";
		if (quotes == null) {
			RestTemplate rt = new RestTemplate();
			ResponseEntity<String> forEntity = rt.getForEntity(quoteUrl, String.class);
			String body = forEntity.getBody();

			ObjectMapper mapper = new ObjectMapper();
			try {
				quotes = mapper.readValue(body, Quote[].class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}else {
			int nextInt = r.nextInt(quotes.length - 1);
			return quotes[nextInt].getText();
		}
		return msg;
	}
}



