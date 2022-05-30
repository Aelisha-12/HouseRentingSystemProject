package com.ohrs.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
