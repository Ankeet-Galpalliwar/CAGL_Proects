package com.cag.twowheeler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cag.twowheeler.entity.VehicalVariant;

public interface VehicalVariantRepository extends JpaRepository<VehicalVariant,String> {
	@Query(value = "SELECT variant_id FROM vehical_variant WHERE vehicaloemid =:OemID", nativeQuery = true)
	public List<String> getSimilarVariantId(@Param("OemID") int OemID);
	
	public List<VehicalVariant> findByOemVehicalOem(String oem); //type
	
//	@Query(value = "SELECT m.vehicle_model_name,p.vehicalvariant_id,p.on_road_price,ROUND(0.9*p.on_road_price,-3) as max_loan_amount,v.file_path,v.file_name from vehicle_modal m join vehical_variant v on m.vehicle_model_id=v.vehicle_modal_id join vehical_price p on v.variant_id=p.vehicalvariant_id where v.variant_id=p.vehicalvariant_id and m.vehicle_model_name:modal and p.state:state", nativeQuery = true)
//	public List<CustomVariantInfo> getVariantsByStateAndmodal(@Param("modal") String modal, @Param("state") String state);

	public List<VehicalVariant> findByVehicleModal(String model);
	

	public VehicalVariant findByVehicalvariantName(String variantName);
	

}
