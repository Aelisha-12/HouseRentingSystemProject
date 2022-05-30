package com.ohrs.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString

public class MessageResponse {
	
	private String message;

	public MessageResponse(String message) {
	    this.message = message;
	  }

}
