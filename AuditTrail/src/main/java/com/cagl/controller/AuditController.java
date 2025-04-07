package com.cagl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cagl.Responce.Responce;
import com.cagl.Responce.Voterauthresponce;
import com.cagl.Responce.smsResponce;
import com.cagl.dto.AuditEnrollmentDto;
import com.cagl.dto.AuditTrailDto;
import com.cagl.dto.BranchPerformanceDetail;
import com.cagl.dto.Branchmasterhierarchy;
import com.cagl.dto.KendraWiseSummary;
import com.cagl.dto.LeadContactDetailsDto;
import com.cagl.dto.LucVisit;
import com.cagl.dto.NewMenberDetail;
import com.cagl.dto.PARintegration;
import com.cagl.dto.RFperformance;
import com.cagl.dto.RepaymentHistory;
import com.cagl.entity.datasource1.LeadContactDetails;
import com.cagl.repositorty.datasource1.LeadContactDetailsRepository;
import com.cagl.request.smsRequest;
import com.cagl.request.voterauth;
import com.cagl.service.AuditService;

import lombok.Builder;

@RestController
@CrossOrigin(origins = "*")
public class AuditController {
	@Autowired
	AuditService auditService;

	private final NamedParameterJdbcTemplate jdbcTemplate1;
	private final NamedParameterJdbcTemplate jdbcTemplate2;

	@Autowired
	public AuditController(@Qualifier("jdbcTemplate1") NamedParameterJdbcTemplate jdbcTemplate1,
			@Qualifier("jdbcTemplate2") NamedParameterJdbcTemplate jdbcTemplate2) {
		this.jdbcTemplate1 = jdbcTemplate1;
		this.jdbcTemplate2 = jdbcTemplate2;
	}

	/**
	 * @author Ankeet.G
	 * @req_BY @Savitha
	 */
	@GetMapping("/BranchPerformanceDetail")
	public ResponseEntity<Responce> branchPerformanceDetail(@RequestHeader("Authorization") String Authorization) {
//		System.out.println("BranchPerformanceDetail API CALLED");

		if (!Authorization.equals("Q2FnbCQyMDIz")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Responce.builder().error(Boolean.TRUE).Data(null).message("UNAUTHORIZED").build());
		}

		String sql = "SELECT branch_id,date(branch_opening_date),no_of_active_mem,noofactbrrwr,waitingbrrwr_zero_os,round(principal_os/10000000,2) as Portfolio,round((par/principal_os)*100,2) as Par_per,no_of_kendra,no_of_group,ld_amount_all,PAR_all,round(datediff(curdate(),date(branch_opening_date))/365,2) as Branch_Vintage FROM warehouse.t24_bpr_orig where report_date in(select max(report_date) from warehouse.t24_bpr_orig)";

		List<BranchPerformanceDetail> queryData = jdbcTemplate2.query(sql, (rs, rowNum) -> {

			BranchPerformanceDetail branchPerformance = BranchPerformanceDetail.builder().branch_id(rs.getString(1))
					.branch_opening_date(rs.getString(2)).no_of_active_mem(rs.getString(3))
					.noofactbrrwr(rs.getString(4)).waitingbrrwr_zero_os(rs.getString(5)).Portfolio(rs.getString(6))
					.Par_per(rs.getString(7)).no_of_kendra(rs.getString(8)).no_of_group(rs.getString(9)).build();

			try {
				branchPerformance.setLd_amount_all(rs.getBigDecimal(10));
			} catch (Exception e) {
				branchPerformance.setLd_amount_all(null);
			}
			try {
				branchPerformance.setPAR_all(rs.getBigDecimal(11).doubleValue());
			} catch (Exception e) {
				branchPerformance.setPAR_all(0.0);
			}
			try {
				branchPerformance.setBranch_Vintage(rs.getBigDecimal(12).doubleValue());
			} catch (Exception e) {
				branchPerformance.setBranch_Vintage(0.0);
			}

			return branchPerformance;
		});
		if (queryData != null & !queryData.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(Responce.builder().error(Boolean.FALSE).Data(queryData).message("DATA FETCH").build());
		return ResponseEntity.status(HttpStatus.OK)
				.body(Responce.builder().error(Boolean.TRUE).Data(null).message("DATA NOT FETCH").build());
	}

	/**
	 * @author Ankeet.G
	 * @req_BY @Savitha
	 */
	@GetMapping("/kendrawisesummary")
	public ResponseEntity<Responce> kendraWiseSummary(@RequestHeader("Authorization") String Authorization,
			@RequestParam List<String> branchId, @RequestParam(required = false) List<String> kendraId,
			@RequestParam(required = false) List<String> kendraManager,
			@RequestParam(required = false) List<String> MeetingDay) {

//		System.out.println("kendrawisesummary API CALLED");
		if (!Authorization.equals("Q2FnbCQyMDIz")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Responce.builder().error(Boolean.TRUE).Data(null).message("UNAUTHORIZED").build());
		}
		StringBuilder q = new StringBuilder(
				"SELECT BRANCH_ID,entity_id as Kendra_id,NAME,MEETING_DAY,MEETING_FREQ,MEETING_TIME_FROM,MEETING_TIME_TO, date(MEETING_DATE) as MEETING_DATE,KENDRA_MANAGER,KM_NAME,date(kendra_act_date) as kendra_act_date,report_date, ifnull(no_of_active_mem,0) as no_of_active_mem,ifnull(noofactbrrwr,0) as noofactbrrwr,ifnull(round(ld_principal_os/10000000,2),0) as Portfolio,ifnull(par,0) as par, ifnull(round((par/ld_principal_os)*100,2),0) as Par_per,ifnull(no_of_group,0) as no_of_group,ifnull(num_of_active_acc*1,0) as num_of_active_acc , ifnull(no_par_customer,0) as no_par_customer ,ifnull(highriskno_par_customer,0) as highriskno_par_customer,ifnull(highrisknoofloans,0) as highrisknoofloans,ifnull(highrisk_portfolio,0) as highrisk_portfolio FROM warehouse.kendrawise_summary k  where branch_id in (:branchId) ");
		// Prepare parameters
		Map<String, Object> params = new HashMap<>();
		params.put("branchId", branchId);

		// Optional Condition

		if (kendraId != null) {
			q.append("and  k.entity_id in (:kendraId)");
			params.put("kendraId", kendraId);
		}

		if (kendraManager != null) {
			q.append(" and KENDRA_MANAGER in (:kendraManager)");
			params.put("kendraManager", kendraManager);
		}

		if (MeetingDay != null) {
			q.append("and MEETING_DAY in (:MeetingDay)");
			params.put("MeetingDay", MeetingDay);
		}

		List<KendraWiseSummary> queryData = jdbcTemplate2.query(q.toString(), params, (rs, rowNum) -> {
			return KendraWiseSummary.builder().BRANCH_ID(rs.getString(1)).Kendra_id(rs.getString(2))
					.NAME(rs.getString(3)).MEETING_DAY(rs.getString(4)).MEETING_FREQ(rs.getString(5))
					.MEETING_TIME_FROM(rs.getString(6)).MEETING_TIME_TO(rs.getString(7)).MEETING_DATE(rs.getString(8))
					.KENDRA_MANAGER(rs.getString(9)).KM_NAME(rs.getString(10)).kendra_act_date(rs.getString(11))
					.report_date(rs.getString(12)).no_of_active_mem(rs.getString(13)).noofactbrrwr(rs.getString(14))
					.Portfolio(rs.getString(15)).par(rs.getString(16)).Par_per(rs.getString(17))
					.no_of_group(rs.getString(18)).num_of_active_acc(rs.getString(19)).no_par_customer(rs.getString(20))
					.highriskno_par_customer(rs.getString(21)).highrisknoofloans(rs.getString(22))
					.highrisk_portfolio(rs.getString(23)).build();
		});

		if (queryData != null & !queryData.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(Responce.builder().error(Boolean.FALSE).Data(queryData).message("DATA FETCH").build());
		return ResponseEntity.status(HttpStatus.OK)
				.body(Responce.builder().error(Boolean.TRUE).Data(null).message("DATA NOT FETCH").build());
	}

	/**
	 * @author Ankeet.G
	 * @req_BY @Savitha
	 */
	@GetMapping("/branchmasterhierarchy")
	public ResponseEntity<Responce> branchMasterHierarchy(@RequestHeader("Authorization") String Authorization) {

//		System.out.println("branchmasterhierarchy API CALLED");
		if (!Authorization.equals("Q2FnbCQyMDIz")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Responce.builder().error(Boolean.TRUE).Data(null).message("UNAUTHORIZED").build());
		}

		String sql = "select d.office_id,Branch_name,AreaName,Region_Name,zone,type,d.state,date(branch_opening_date),branch_status,round(datediff(curdate(),date(branch_opening_date))/365,2) as Branch_Vintage,area.recid as area_id,region.recid as region_id,state.recid as state_id from warehouse.t24_office d inner join eficaz_staging.efz_mcb_h_office_details o on o.recid=d.office_id INNER JOIN eficaz_staging.efz_mcb_h_office_details area ON (o.parent_office = area.recid) INNER JOIN eficaz_staging.efz_mcb_h_office_details region ON (area.parent_office = region.recid) INNER JOIN eficaz_staging.efz_mcb_h_office_details state ON (region.parent_office = state.recid) where branch_status not in ('INACTIVE')";

		List<Branchmasterhierarchy> queryData = jdbcTemplate2.query(sql, (rs, rowNum) -> {
			return Branchmasterhierarchy.builder().office_id(rs.getString(1)).Branch_name(rs.getString(2))
					.AreaName(rs.getString(3)).Region_Name(rs.getString(4)).zone(rs.getString(5)).type(rs.getString(6))
					.state(rs.getString(7)).branch_opening_date(rs.getString(8)).branch_status(rs.getString(9))
					.Branch_Vintage(rs.getString(10)).area_id(rs.getString(11)).region_id(rs.getString(12))
					.state_id(rs.getString(13)).build();
		});
		if (queryData != null & !queryData.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(Responce.builder().error(Boolean.FALSE).Data(queryData).message("DATA FETCH").build());
		return ResponseEntity.status(HttpStatus.OK)
				.body(Responce.builder().error(Boolean.TRUE).Data(null).message("DATA NOT FETCH").build());

	}

	/**
	 * @author Ankeet.G
	 * @req_BY @Savitha
	 */
	@GetMapping("/rfperformance")
	public ResponseEntity<Responce> RFBranchperformanceDetails(@RequestHeader("Authorization") String Authorization) {

//		System.out.println("rfperformance API CALLED");
		if (!Authorization.equals("Q2FnbCQyMDIz")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Responce.builder().error(Boolean.TRUE).Data(null).message("UNAUTHORIZED").build());
		}

		String sql = "SELECT branch_id ,no_of_active_borrower  as no_of_customers,no_of_active_borrower,round(ifnull(Outstanding_amount,0)/10000000,2) as Portfolio_Cr, active_loans,ifnull(no_of_borrowers_in_PAR,0) as no_of_borrowers_in_PAR, round(ifnull(overdue_principal,0)+ifnull(overdue_interest,0),2) as Overall_PAR_OS_Cr,ifnull(no_of_active_borrower,0)-ifnull(no_of_active_borrower_ex_hr,0) as NO_of_HR_members, round(ifnull(Outstanding_amount,0)-ifnull(Outstanding_amount_ex_hr,0),2) as HR_amount_Cr,round(ifnull(portfolio_at_risk,0),2) as Overall_Par_PER FROM actify.rf_bpr";

		List<RFperformance> queryData = jdbcTemplate2.query(sql, (rs, rowNum) -> {
			return RFperformance.builder().branchId(rs.getString(1)).numberOfCustomer(rs.getString(2))
					.numberOfActiveBorrower(rs.getString(3)).portfolioCr(rs.getString(4)).activeLoans(rs.getString(5))
					.numberOfBorrowerInPar(rs.getString(6)).overallParOsCr(rs.getString(7))
					.numberOfHrMember(rs.getString(8)).hrAmountCr(rs.getString(9)).overallParPer(rs.getString(10))
					.build();
		});
		if (queryData != null & !queryData.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(Responce.builder().error(Boolean.FALSE).Data(queryData).message("DATA FETCH").build());
		return ResponseEntity.status(HttpStatus.OK)
				.body(Responce.builder().error(Boolean.TRUE).Data(null).message("DATA NOT FETCH").build());

	}

	/**
	 * @author Ankeet.G
	 * @req_BY @Savitha
	 */
	@GetMapping("/PARintegration")
	public ResponseEntity<Responce> PARintegration(@RequestHeader("Authorization") String Authorization,
			@RequestParam List<String> branchId, @RequestParam(required = false) List<String> kendraId) {
//		System.out.println("PARintegration API CALLED");
		if (!Authorization.equals("Q2FnbCQyMDIz")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Responce.builder().error(Boolean.TRUE).Data(null).message("UNAUTHORIZED").build());
		}
		// SELECT a.*,if(fund_name like '%High%', 'High
		// Risk',if(datediff(curdate(),date(ln_value_date))>90,'QMA',if(arrived_dpd<30,'New
		// Arrear',if(arrived_dpd>30,'PAR','')))) as arrstatus FROM test.arrear_details
		// a where branch_id in (:branchId )
		StringBuilder q = new StringBuilder(
				"SELECT a.*,if(fund_name like '%High%', 'High Risk',if(datediff(curdate(),date(ln_value_date))<=90,'QMA',if(arrived_dpd<=30,'New Arrear',if(arrived_dpd>30,'PAR','')))) as arrstatus FROM test.arrear_details a where branch_id in (:branchId )");
		// Prepare parameters
		Map<String, Object> params = new HashMap<>();
		params.put("branchId", branchId);
		// Optional Condition
		if (kendraId != null) {
			q.append("and kendra_id in(:kendraId)");
			params.put("kendraId", kendraId);
		}
		List<PARintegration> queryData = jdbcTemplate2.query(q.toString(), params, (rs, rowNum) -> {
			return PARintegration.builder().report_date(rs.getString(1)).account_id(rs.getString(2))
					.CUSTOMER_ID(rs.getString(3)).CUST_NAME(rs.getString(4)).KENDRA_ID(rs.getString(5))
					.KENDRA_NAME(rs.getString(6)).BRANCH_ID(rs.getString(7)).BRANCH(rs.getString(8))
					.AREA(rs.getString(9)).DIVISION(rs.getString(10)).OD_PRINCIPAL(rs.getString(11))
					.OD_interest(rs.getString(12)).unpaid_principal(rs.getString(13)).LD_ID(rs.getString(14))
					.category(rs.getString(15)).MAT_DATE(rs.getString(16)).FUNDER_ID(rs.getString(17))
					.FUND_NAME(rs.getString(18)).customer_status(rs.getString(19)).ln_value_date(rs.getString(20))
					.DAYS_IN_ARREARs(rs.getString(21)).meeting_day(rs.getString(22)).kendra_manager(rs.getString(23))
					.km_name(rs.getString(24)).product_name(rs.getString(25)).freq(rs.getString(26))
					.installment_amount(rs.getString(27)).arrived_dpd(rs.getString(28)).OTS_ELIGIBLE(rs.getString(29))
					.arrstatus(rs.getString(30)).build();
		});
		if (queryData != null & !queryData.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(Responce.builder().error(Boolean.FALSE).Data(queryData).message("DATA FETCH").build());
		return ResponseEntity.status(HttpStatus.OK)
				.body(Responce.builder().error(Boolean.TRUE).Data(null).message("DATA NOT FETCH").build());
	}

	/**
	 * @author Ankeet.G
	 * @req_BY @Savitha
	 */
	@GetMapping("NewMenberDetail")
	public ResponseEntity<Responce> getNewMenberDetail(@RequestHeader("Authorization") String Authorization,
			@RequestParam List<String> branchId, @RequestParam(required = false) List<String> kendraId) {

//		System.out.println("NewMenberDetail API CALLED");

		if (!Authorization.equals("Q2FnbCQyMDIz")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Responce.builder().error(Boolean.TRUE).Data(null).message("UNAUTHORIZED").build());
		}
		StringBuilder q = new StringBuilder(
				"select k.entity_id as kendraid,k.name as kendra,p.user_name as kendramanager ,c.entity_id as customer_id,c.name as Memeber_name,concat(c.entity_id, ' | ', c.name) as Customer_name,date(c.first_act_date) as created_date,date(c.first_act_date) as customer_activation_date,d.bank_name as Bank_Name,acc_holder_name as Account_Holder_Name,c.bank_ac_no as Account_Number,c.ifsc_code IFSC_code,legal_doc_name as KYC_type,legal_id as KYC_ID from data_warehouse.w_mcb_entity_d c inner join data_warehouse.w_mcb_entity_f d on (c.entity_id=d.entity_id and d.m=1 and s=1) inner join data_warehouse.w_mcb_kendra_group_d k on (c.kendra_id=k.entity_id) inner join data_warehouse.w_user_d p on (k.kendra_manager=p.user_id) where c.branch_id in (:branchId) and C.record_type='ACTIVE' AND C.ENTITY_TYPE='MEMBER' and DATE_FORMAT(c.first_act_date, '%Y-%m-%d')>='2024-06-01' and DATE_FORMAT(c.first_act_date, '%Y-%m-%d') <=curdate() ");
		// Prepare parameters
		Map<String, Object> params = new HashMap<>();
		params.put("branchId", branchId);

		// Optional Condition

		if (kendraId != null) {
			q.append("and c.kendra_id in(:kendraId)");
			params.put("kendraId", kendraId);
		}
		// Query End point
		q.append(" group by c.entity_id order by customer_activation_date desc");
		List<NewMenberDetail> queryData = jdbcTemplate2.query(q.toString(), params, (rs, rowNum) -> {
			return NewMenberDetail.builder().kendraid(rs.getString(1)).kendra(rs.getString(2))
					.kendramanager(rs.getString(3)).customer_id(rs.getString(4)).Memeber_name(rs.getString(5))
					.Customer_name(rs.getString(6)).created_date(rs.getString(7))
					.customer_activation_date(rs.getString(8)).Bank_Name(rs.getString(9))
					.Account_Holder_Name(rs.getString(10)).Account_Number(rs.getString(11)).IFSC_code(rs.getString(12))
					.KYC_type(rs.getString(13)).KYC_ID(rs.getString(14)).build();
		});

		if (queryData != null & !queryData.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(Responce.builder().error(Boolean.FALSE).Data(queryData).message("DATA FETCH").build());
		return ResponseEntity.status(HttpStatus.OK)
				.body(Responce.builder().error(Boolean.TRUE).Data(null).message("DATA NOT FETCH").build());

	}

	/**
	 * @author Ankeet.G
	 * @req_BY @Savitha
	 */
	@GetMapping("LUCvisit")
	public ResponseEntity<Responce> lucVisit(@RequestHeader("Authorization") String Authorization,
			@RequestParam List<String> branchId, @RequestParam(required = false) List<String> kendraId,
			@RequestParam(required = false) List<String> customerId) {

//		System.out.println("LUCvisit API CALLED");
		if (!Authorization.equals("Q2FnbCQyMDIz")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Responce.builder().error(Boolean.TRUE).Data(null).message("UNAUTHORIZED").build());
		}

		StringBuilder q = new StringBuilder(
				"select k.entity_id as kendraid,k.name as kendra,p.user_name as kendramanager,c.group_id,c.group_id as group_name,c.entity_id,c.name as Memeber_name,DATE_FORMAT(c.first_act_date, '%d-%m-%Y') as customer_activation_date,a.loan_id as global_account_num,if(a.approved_amt='',a.amount*1,a.approved_amt*1)as loan_amount,date(ln_value_date) as disbursement_date,a.loan_id as account_id,product as product,SUB_PURPOSE as Purpose,a.co_code as branch_id,ifnull(Principal_outstanding,0) as Principal_outstanding,ifnull(Overdue_principal,0)+ifnull(Overdue_interest,0) as Overdue,customer_id from data_warehouse.w_mcb_entity_d  c inner join data_warehouse.w_mcb_kendra_group_d k on (c.kendra_id=k.entity_id) inner join  data_warehouse.w_user_d p on (k.kendra_manager=p.user_id) inner join data_warehouse.w_mcb_h_loan_d a on c.entity_id=a.member_id inner  join  data_warehouse.w_ld_loans_and_deposits pr on pr.lt_mcb_loan_ref=a.loan_id where a.co_code in (:branchId) and a.status in ('DISBURSED') ");
		// Prepare parameters
		Map<String, Object> params = new HashMap<>();
		params.put("branchId", branchId);
		// ghOptional Condition
		if (kendraId != null) {
			q.append(" and k.entity_id in (:kendraId)");
			params.put("kendraId", kendraId);
		}
		if (customerId != null) {
			q.append(" and customer_id in (:customerId)");
			params.put("customerId", customerId);
		}
		// Query End point
		q.append(" and c.record_type='ACTIVE' AND C.ENTITY_TYPE='MEMBER' group by a.loan_id");

		List<LucVisit> queryData = jdbcTemplate2.query(q.toString(), params, (rs, rowNum) -> {

			return LucVisit.builder().kendraid(rs.getString(1)).kendra(rs.getString(2)).kendramanager(rs.getString(3))
					.group_id(rs.getString(4)).group_name(rs.getString(5)).entity_id(rs.getString(6))
					.Memeber_name(rs.getString(7)).customer_activation_date(rs.getString(8))
					.global_account_num(rs.getString(9)).loan_amount(rs.getString(10))
					.disbursement_date(rs.getString(11)).account_id(rs.getString(12)).product(rs.getString(13))
					.Purpose(rs.getString(14)).branch_id(rs.getString(15)).Principal_outstanding(rs.getString(16))
					.Overdue(rs.getString(17)).customer_id(rs.getString(18)).build();

		});

		if (queryData != null & !queryData.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(Responce.builder().error(Boolean.FALSE).Data(queryData).message("DATA FETCH").build());
		return ResponseEntity.status(HttpStatus.OK)
				.body(Responce.builder().error(Boolean.TRUE).Data(null).message("DATA NOT FETCH").build());
	}

	/**
	 * @author Ankeet.G
	 * @req_BY @Savitha
	 */
	@GetMapping("RepaymentHistory")
	public ResponseEntity<Responce> getRepaymentHistory(@RequestHeader("Authorization") String Authorization,
			@RequestParam List<String> memberId) {
//		System.out.println("RepaymentHistory API CALLED");
		if (!Authorization.equals("Q2FnbCQyMDIz")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Responce.builder().error(Boolean.TRUE).Data(null).message("UNAUTHORIZED").build());
		}

		StringBuilder q = new StringBuilder(
				"SELECT member_id, date(coll_date) as coll_date, total_due, tot_collected, attendance, kendra_id,coll_type,id FROM recovery_memberwise_collection_datewise where member_id in (:memberId) order by coll_date desc limit 10");
		// Prepare parameters
		Map<String, Object> params = new HashMap<>();
		params.put("memberId", memberId);

		List<RepaymentHistory> queryData = jdbcTemplate2.query(q.toString(), params, (rs, rowNum) -> {

			return RepaymentHistory.builder().member_id(rs.getString(1)).coll_date(rs.getString(2))
					.total_due(rs.getString(3)).tot_collected(rs.getString(4)).attendance(rs.getString(5))
					.kendra_id(rs.getString(6)).coll_type(rs.getString(7)).id(rs.getString(8)).build();

		});
		if (queryData != null & !queryData.isEmpty())
			return ResponseEntity.status(HttpStatus.OK)
					.body(Responce.builder().error(Boolean.FALSE).Data(queryData).message("DATA FETCH").build());
		return ResponseEntity.status(HttpStatus.OK)
				.body(Responce.builder().error(Boolean.TRUE).Data(null).message("DATA NOT FETCH").build());
	}

	/**
	 * @author Ankeet.G
	 * @req_BY @Satish.G
	 */
	@PostMapping("/auditenrollment")
	public ResponseEntity<Responce> insertAuditEnrollment(@RequestHeader("Authorization") String Authorization,
			@RequestBody AuditEnrollmentDto auditEnrollmentDto) {
		if (Authorization.equals("Q2FnbCQyMDIz")) {
			AuditEnrollmentDto addAuditEnrollment = auditService.addAuditEnrollment(auditEnrollmentDto);
			return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().error(Boolean.FALSE)
					.message("Data Add or Edit Sucessfully").Data(addAuditEnrollment).build());
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Responce.builder().error(Boolean.TRUE)
					.message("Data Not Added or Edited {INVALID USER..!}").Data(null).build());
		}
	}

	/**
	 * @author Ankeet.G
	 * @req_BY @Satish.G
	 */
	@PostMapping("/audittrail")
	public ResponseEntity<Responce> insertAuditTrail(@RequestHeader("Authorization") String headerValue,
			@RequestBody AuditTrailDto auditTrailDto) {

		if (headerValue.equals("Q2FnbCQyMDIz")) {
			AuditTrailDto addAuditTrail = auditService.addAuditTrail(auditTrailDto);
			return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().error(Boolean.FALSE)
					.message("Data Add or Edit Sucessfully").Data(addAuditTrail).build());
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Responce.builder().error(Boolean.TRUE)
					.message("Data Not Added or Edited {INVALID USER..!}").Data(null).build());
		}
	}

	@Autowired
	LeadContactDetailsRepository leadContactDetailsRepository;

	/**
	 * @author Ankeet.G
	 * @req_BY @Satish.G
	 */
	@PostMapping("/lead-details")
	public ResponseEntity<Responce> leadContactDetails(@RequestBody List<LeadContactDetailsDto> contactDetailsDto) {
		try {
			contactDetailsDto.stream().forEach(e->{
				leadContactDetailsRepository
				.save(LeadContactDetails.builder().contactNumber(e.getContactNumber()).name(e.getName()).build());
				
			});
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					Responce.builder().error(Boolean.TRUE).message("Data Not Inserted").Data(e.getMessage()).build());
		}
		return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().error(Boolean.TRUE).message("Data Inserted")
				.Data(contactDetailsDto).build());
	}

	/**
	 * @author Ankeet.G
	 * @req_BY @Satish.G
	 */
	@GetMapping("/get-lead-details")
	public ResponseEntity<Responce> GetleadContactDetails() {
		
		ArrayList<LeadContactDetailsDto> leaddtos=new ArrayList<>();
		try {
			List<LeadContactDetails> leadData = leadContactDetailsRepository.findAll();
			leadData.stream().forEach(e->{
				LeadContactDetailsDto leadsDto = LeadContactDetailsDto.builder().build();
				BeanUtils.copyProperties(e, leadsDto);
				leaddtos.add(leadsDto);			});

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					Responce.builder().error(Boolean.TRUE).message("Data Not Inserted").Data(e.getMessage()).build());
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(Responce.builder().error(Boolean.TRUE).message("Leads Details").Data(leaddtos).build());
	}

	/**
	 * @author Ankeet.G
	 * @req_BY @Satish.G
	 */
	@PostMapping("requestotp")
	public ResponseEntity<Responce> sms(@RequestBody smsRequest requestData) {
		RestTemplate restTemplate = new RestTemplate();

//		String apiUrl = "https://caglinterface.grameenkoota.in/cga_sms/v1/SMS";
		String apiUrl = "http://172.16.8.119:4444/cga_sms/v1/SMS";

		// Set request headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("key", "175d725e-73b5-11ec-9c5d-0abea36e3286");

		// Create an HttpEntity with the request object and headers
		HttpEntity<smsRequest> requestEntity = new HttpEntity<>(requestData, headers);

		// Send the POST request to the third-party API
		ResponseEntity<smsResponce> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity,
				smsResponce.class);

		return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().error(Boolean.FALSE)
				.Data(responseEntity.getBody()).message("Voter Auth Responce").build());
	}

	/**
	 * @author Ankeet.G
	 * @req_BY @Satish.G
	 */
	@PostMapping("voterauth")
	public ResponseEntity<Responce> voterauth(@RequestBody com.cagl.request.voterauth requestData) {
		RestTemplate restTemplate = new RestTemplate();

//		String apiUrl1 = "https://caglinterface.grameenkoota.in/DL/digivoterauth/v1/voterauth";
		String apiUrl1 = "http://172.16.8.119:4444/DL/digivoterauth/v1/voterauth";

		// Set request headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create an HttpEntity with the request object and headers
		HttpEntity<voterauth> requestEntity = new HttpEntity<>(requestData, headers);

		// Send the POST request to the third-party API
		ResponseEntity<Voterauthresponce> responseEntity = restTemplate.exchange(apiUrl1, HttpMethod.POST,
				requestEntity, Voterauthresponce.class);
		return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().error(Boolean.FALSE)
				.Data(responseEntity.getBody()).message("Voter Auth Responce").build());
	}

	// non use API
//=============================================================================================================
	@GetMapping("/editauditenrollment")
	public ResponseEntity<Responce> editAuditEnrollment(@RequestBody AuditEnrollmentDto auditEnrollmentDto) {
		AuditEnrollmentDto editAuditEnrollment = auditService.editAuditEnrollment(auditEnrollmentDto);
		if (editAuditEnrollment != null)
			return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().error(Boolean.FALSE)
					.message("Data Modified sucessfully").Data(editAuditEnrollment).build());
		return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
				.body(Responce.builder().error(Boolean.TRUE).message("Data Not Modified").Data(null).build());

	}

	@GetMapping("/editaudittrail")
	public ResponseEntity<Responce> editAuditTrail(@RequestBody AuditTrailDto auditTrailDto) {
		AuditTrailDto addAuditTrail = auditService.editAuditTrail(auditTrailDto);
		if (addAuditTrail != null)
			return ResponseEntity.status(HttpStatus.OK).body(Responce.builder().error(Boolean.FALSE)
					.message("Data Modified sucessfully").Data(addAuditTrail).build());

		return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
				.body(Responce.builder().error(Boolean.FALSE).message("Data Not Modified").Data(null).build());
	}

}
