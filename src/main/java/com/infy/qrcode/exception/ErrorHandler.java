package com.infy.qrcode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ErrorHandler {
	
    public static ResponseEntity<Object> generateResponse(String message,String responseId, HttpStatus status) {

        Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorMessage", message);
            map.put("responseId", responseId);
            map.put("httpStatus", status.value());
            
            return new ResponseEntity<Object>(map,status);
    }

}
