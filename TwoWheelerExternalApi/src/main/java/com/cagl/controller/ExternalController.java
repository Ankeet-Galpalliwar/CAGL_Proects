package com.cagl.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cagl.dto.BranchDto;
import com.cagl.dto.ComboCbReport;
import com.cagl.dto.Foirinsert;
import com.cagl.dto.Responce;
import com.cagl.dto.kendraDto;
import com.cagl.dto.loanId;
import com.cagl.dto.responce.ComboCbReportResponce;
import com.cagl.dto.responce.FoirinsertResponce;
import com.cagl.dto.responce.TwoWheelerResponsiveResponce;
import com.cagl.dto.responce.response;
import com.cagl.dto.responce.unnathiresponse;
import com.cagl.entity.Branch;
import com.cagl.entity.Kendra;
import com.cagl.entity.customer_eligible;
import com.cagl.repository.Customer_eligibleRepository;
import com.cagl.repository.KendraRepository;
import com.cagl.repository.branchRepository;

/**
 * 
 * @author Ankeet G.
 *
 */
//http://172.16.109.50:3000/
@RestController
@CrossOrigin(origins = "*")
public class ExternalController {

	@Autowired
	private Environment env;

//	 @Value("${uatIP}")
//	    private String uatIP;
//
//	    @Value("${prodIP}")
//		private int prodIP;

	@Autowired
	private KendraRepository kendraRepository;

	@Autowired
	private Customer_eligibleRepository customer_eligibleRepository;

	@Autowired
	private branchRepository branchRepository;

	@GetMapping("/getkendras")
	public ResponseEntity<Responce> getKendras(@RequestParam String branchID) {
		List<kendraDto> kendrasDto = new ArrayList<>();
		try {
			List<Kendra> Kendras = kendraRepository.getKendrasByBranchID(branchID);
			Kendras.stream().forEach(e -> {
				Kendra kendra = kendraRepository.getKendraId(e.getKendraId());
				kendrasDto.add(
						kendraDto.builder().kendraId(kendra.getKendraId()).kendraName(kendra.getKendraName()).build());
			});
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Responce.builder().Error(Boolean.TRUE)
					.message("Catch Execute...!\n ID Invalid...!").data("---NA---").build());
		}
		if (!kendrasDto.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(Responce.builder().Error(Boolean.FALSE).message("all kendras").data(kendrasDto).build());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Responce.builder().Error(Boolean.TRUE).message("kendras Not Present").data("---NA---").build());
	}

	@RequestMapping("/getcustomers")
	public ResponseEntity<Responce> getcustomers(@RequestParam String kendraId) {
		List<customer_eligible> customerDtos = new ArrayList<>();
		List<customer_eligible> customers = new ArrayList<>();
		try {
			customers = customer_eligibleRepository.getCustomerByKendraID(kendraId);
//			customers.stream().forEach(data -> {
//				customerDtos.add(CustomerDto.builder().customerID(data.getCustomerID())
//						.customerName(data.getCustomerName()).gender(data.getGender()).build());
//			});
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Responce.builder().Error(Boolean.TRUE)
					.message("Catch Execute...!\n ID Invalid...!").data("---NA---").build());
		}
		if (!customers.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(Responce.builder().Error(Boolean.FALSE).message("all Customers").data(customers).build());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Responce.builder().Error(Boolean.TRUE).message("Customer Not Present").data("---NA---").build());
	}

//  Below APIS For UAT

	@PostMapping("/ComboCbReport")
	public ResponseEntity<Responce> comboCbReport(@RequestBody ComboCbReport cbReport) {
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "http://" + env.getProperty("uatIP1") + ":8130/CAGLComboReport/ComboCbReport/makeRequest";
		// Create an HttpEntity with the request object and headers
		HttpEntity<ComboCbReport> requestEntity = new HttpEntity<>(cbReport);

		// Send the POST request to the third-party API
		//ComboCbReportResponce
		ResponseEntity<Object> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST,
				requestEntity, Object.class);

		return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().Error(Boolean.FALSE)
				.data(responseEntity.getBody()).message("(UAT)comboCbReport Responce").build());
	}

	@PostMapping("/InquiryAgent")
	public String inquiryAgent(@RequestParam String requestXml) {
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "http://172.16.109.29:8081/InquiryAgentOriginal/doGet.service/request";
		// Set request headers
		HttpHeaders headers = new HttpHeaders();
//				headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", "GK_NEWGEN_UAT");
		headers.set("password", "+GW1NiOxIf007lQmx5Llwzr4wic=");
		headers.set("requestId", "10000008");
		headers.set("responseType", "INDV|ALL");
		headers.set("Content-Type", "application/json");
		headers.set("requestXML", requestXml);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		// Send the POST request to the third-party API
		ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity,
				String.class);
		return responseEntity.getBody();
	}

	@PostMapping("/unnathiresponse")
	public String unnathiresponse(@RequestParam unnathiresponse unnathiResponse) {
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "http://172.16.106.7:9999/unnathiresponsive/unnathiresponse";

		HttpEntity<unnathiresponse> requestEntity = new HttpEntity<>(unnathiResponse);
		// Send the POST request to the third-party API
		ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity,
				String.class);
		return responseEntity.getBody();
	}

	@PostMapping("/foiriTwowheelerInsert")
	public ResponseEntity<Responce> FoiriInsert(@RequestBody Foirinsert foirinsert) {
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "http://" + env.getProperty("uatIP") + ":9999/twowheelerinsert/twowheelerinsert";
		// Create an HttpEntity with the request object and headers
		HttpEntity<Foirinsert> requestEntity = new HttpEntity<>(foirinsert);
		// Send the POST request to the third-party API
		//FoirinsertResponce
		ResponseEntity<Object> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST,
				requestEntity, Object.class);
//		if (responseEntity.getBody() != null)
			return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().Error(Boolean.FALSE)
					.data(responseEntity.getBody()).message("(UAT)foiriTwowheelerInsert Responce").build());
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(Responce.builder().Error(Boolean.TRUE).data(null).message("Data Not Insert").build());
	}

	@PostMapping("/foiriTwowheelerInsert/Prod")
	public ResponseEntity<Responce> Foiriinsertprod(@RequestBody Foirinsert foirinsert1) {
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "http://" + env.getProperty("prodIP") + ":9999/twowheelerinsert/twowheelerinsert";
		// Create an HttpEntity with the request object and headers
		HttpEntity<Foirinsert> requestEntity = new HttpEntity<>(foirinsert1);
		// Send the POST request to the third-party API
		ResponseEntity<Object> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST,
				requestEntity, Object.class);
//		if (responseEntity.getBody() != null)
			return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().Error(Boolean.FALSE)
					.data(responseEntity.getBody()).message("(Prod)foiriTwowheelerInsert Responce").build());
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(Responce.builder().Error(Boolean.TRUE).data(null).message("Data Not Insert").build());

	}

	//For Production Testing
	@PostMapping("foiriTwowheelerInsert/prod")
	public ResponseEntity<Responce> FoiriInsertprod(@RequestBody Foirinsert foirinsert) {
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "http://" + env.getProperty("prodIP") + ":9999/twowheelerinsert/twowheelerinsert";
		// Create an HttpEntity with the request object and headers
		HttpEntity<Foirinsert> requestEntity = new HttpEntity<>(foirinsert);
		// Send the POST request to the third-party API
		//FoirinsertResponce
		ResponseEntity<Object> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST,
				requestEntity, Object.class);
//		if (responseEntity.getBody() != null)
			return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().Error(Boolean.FALSE)
					.data(responseEntity.getBody()).message("foiriTwowheelerInsert_Responce").build());
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(Responce.builder().Error(Boolean.TRUE).data(null).message("Data Not Insert").build());
	}
	@PostMapping("/twowheelerresponse")
	public ResponseEntity<Responce> twowheelerresponse(@RequestBody loanId loanId) {
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "http://" + env.getProperty("uatIP") + ":9999/twowheelerresponsive/twowheelerresponse";
//		String apiUrl = "http://uatmobile.grameenkoota.in:9999/twowheelerresponsive/twowheelerresponse";
		// Create an HttpEntity with the request object and headers 172.16.106.7
		HttpEntity<loanId> requestEntity = new HttpEntity<>(loanId);

		// Send the POST request to the third-party API
		ResponseEntity<Object> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST,
				requestEntity, Object.class);

		return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().Error(Boolean.FALSE)
				.data(responseEntity.getBody()).message("twowheelerresponse").build());
	}
// Below APIS For Product...!

	@PostMapping("/twowheelerresponse/prod")
	public ResponseEntity<Responce> twowheelerresponseprod(@RequestBody loanId loanId) {
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "http://" + env.getProperty("prodIP") + ":9999/twowheelerresponsive/twowheelerresponse";
//		String apiUrl = "http://uatmobile.grameenkoota.in:9999/twowheelerresponsive/twowheelerresponse";
		// Create an HttpEntity with the request object and headers 172.16.3.178
		HttpEntity<loanId> requestEntity = new HttpEntity<>(loanId);

		// Send the POST request to the third-party API
		ResponseEntity<Object> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST,
				requestEntity, Object.class);
		

		return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().Error(Boolean.FALSE)
				.data(responseEntity.getBody()).message("twowheelerresponse").build());
	}

	@PostMapping("/ComboCbReport/prod")
	public ResponseEntity<Responce> comboCbReportprod(@RequestBody ComboCbReport cbReport) {
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "http://" + env.getProperty("prodIP") + ":8080/CAGLComboReport/ComboCbReport/makeRequest";
		// ":8130/CAGLComboReport/ComboCbReport/makeRequest";
		// Create an HttpEntity with the request object and headers
		HttpEntity<ComboCbReport> requestEntity = new HttpEntity<>(cbReport);

		// Send the POST request to the third-party API
		//ComboCbReportResponce
		ResponseEntity<Object> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST,
				requestEntity, Object.class);

		return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().Error(Boolean.FALSE)
				.data(responseEntity.getBody()).message("comboCbReport Responce").build());
	}

	

	@GetMapping("getbranchinfo")
	public ResponseEntity<Responce> getBranchDetails(@RequestParam String branchID) {
		Branch branch = branchRepository.getAllBranch(branchID);
		if (branch != null)
			return ResponseEntity.status(HttpStatus.OK)
					.body(Responce.builder()
							.data(BranchDto.builder().branch_id(branch.getBranch_id())
									.branch_name(branch.getBranch_name()).build())
							.Error(Boolean.FALSE).message("Branch Info").build());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				Responce.builder().data("---NA---").Error(Boolean.TRUE).message("Branch Info Not Present").build());
	}

}
