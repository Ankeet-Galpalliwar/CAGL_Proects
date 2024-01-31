package com.cag.twowheeler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cag.twowheeler.security.CustomeUserDetailService;
import com.cag.twowheeler.security.JwtAuthenticationEntryPoint;
import com.cag.twowheeler.security.JwtAutheticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomeUserDetailService customeUserDetailService;

	@Autowired
	JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	JwtAutheticationFilter autheticationFilter;

	// Checkers APIs
	public static final String[] checkerApis = { "/checker/**",
			// vehicle checker Screen Apis
			"/editOrApproveCheckerData", "/getCheckerVehicleData" };

	// Makers APIs.
	public static final String[] makersApis = {

			// Dealer Apis
			"/addsubdealer", "/addmaindealer", "/editmaindealer", "/editsubdealer"
			// variant Apis
			 };

	// Checker-maker APIs
	public static final String[] CMApis = { "/allstatesdropdown", "/getoems", "/statedropdown", "/regiondropdown",
			"/areadropdown",
			// only for view purpose for checker
			"/getsubdealers", "/getmaindealers"
			// variant api
			, "/vehicleallstate", "/vehiclealloem", "/vehicleModels", "/vehiclevariantdropdown",
			"/vehicaldata",
			// Main variant
			"/mainvariants", "/avaliablemainvariants", "/addmainvariant", "/removemainvariant",
			// Sub variant
			"/subvariants", "/avaliablesubvariants", "/addsubvariant", "/removesubvariant"
			// Document upload Apis
			, "/uploadfile", "/getdocumentstatus",
			// Branch Apis
			"/addmainbranch", "/removemainbranch", "/showmainbranches", "/showavaliablemainbranches",
			"/addallmainbranches", "/addsubbranch", "/removesubbranch", "/showsubbranchesS",
			"/showavaliablesubbranches",
			// variant image upload
			"/allvariants", "/uploadVariantimage" };

	// PermitAll APi Login APi..
	public static final String[] permitAllAPIs = { "/login/userlogin", "/getspecificvariants", "/districtdropdown",
			"/generatepassword", "/alldistrictdropdown","/vehiclevariants","/alloem"
			// image view
			, "/viewimage", "/viewvariantimage", "/getfile", "/downloadexcel", "/downloadvariantsexcel",
			"/getbranchexcel", "/getbranchstate", "/insertvehicle", "/editdata" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().authorizeHttpRequests().antMatchers(permitAllAPIs).permitAll()
				.antMatchers(CMApis).hasRole("CM").antMatchers(makersApis).hasRole("MAKER").antMatchers(checkerApis)
				.hasRole("CHECKER").anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(this.autheticationFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customeUserDetailService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
