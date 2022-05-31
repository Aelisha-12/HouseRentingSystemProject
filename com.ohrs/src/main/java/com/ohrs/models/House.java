package com.ohrs.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data 
@Entity
@Table(name = "house_details")
public class House {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "house_name")
	private String houseName;

	@Column(name = "house_rent")
	private Double houseRent;

	@Column(name = "house_address")
	private String houseAddress;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String houseimage;

	



	

}
