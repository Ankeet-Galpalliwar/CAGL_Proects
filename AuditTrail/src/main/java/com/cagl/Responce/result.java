package com.cagl.Responce;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class result {
	
  @JsonProperty("id")
  private String id;
  
  @JsonProperty("age")
  private String age;
  
  @JsonProperty("name")
  private String name;
  
  @JsonProperty("ac_no")
  private String ac_no;
  
  @JsonProperty("pc_no")
  private String pc_no;
  
  @JsonProperty("gender")
  private String gender;
  
  @JsonProperty("ac_name")
  private String ac_name;
  
  @JsonProperty("dist_no")
  private String dist_no;
  
  @JsonProperty("epic_no")
  private String epic_no;
  
  @JsonProperty("name_v1")
  private String name_v1;
  @JsonProperty("name_v2")
  private String name_v2;
  @JsonProperty("name_v3")
  private String name_v3;
  @JsonProperty("part_no")
  private String part_no;
  @JsonProperty("pc_name")
  private String pc_name;
  @JsonProperty("ps_name")
  private String ps_name;
  @JsonProperty("st_code")
  private String st_code;
  @JsonProperty("st_name")
  private String st_name;
  @JsonProperty("rln_name")
  private String rln_name;
  @JsonProperty("rln_type")
  private String rln_type;
  @JsonProperty("_version_")
  private String _version_;
  @JsonProperty("dist_name")
  private String dist_name;
  @JsonProperty("part_name")
  private String part_name;
  @JsonProperty("ac_name_v1")
  private String ac_name_v1;
  @JsonProperty("pc_name_v1")
  private String pc_name_v1;
  @JsonProperty("ps_name_v1")
  private String ps_name_v1;
  @JsonProperty("section_no")
  private String section_no;
  @JsonProperty("last_update")
  private String last_update;
  @JsonProperty("ps_lat_long")
  private String ps_lat_long;
  @JsonProperty("rln_name_v1")
  private String rln_name_v1;
  @JsonProperty("rln_name_v2")
  private String rln_name_v2;
  @JsonProperty("rln_name_v3")
  private String rln_name_v3;
  @JsonProperty("slno_inpart")
  private String slno_inpart;
  @JsonProperty("dist_name_v1")
  private String dist_name_v1;
  @JsonProperty("part_name_v1")
  private String part_name_v1;
  @JsonProperty("ps_lat_long_0_coordinate")
  private String ps_lat_long_0_coordinate;
  @JsonProperty("ps_lat_long_1_coordinate")
  private String ps_lat_long_1_coordinate;
}
