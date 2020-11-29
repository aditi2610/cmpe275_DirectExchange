package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Common.CommonUtilities;
import com.example.demo.Exception.InvalidRequestException;
import com.example.demo.dto.Offer;
import com.example.demo.service.OfferService;

@RestController
public class OfferController {

	@Autowired
	private OfferService offerService;
	
	@RequestMapping(value="createOffer", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> createOffer(@RequestBody Offer offer){
		Offer newOffer;
		try {
			newOffer = offerService.add(offer);
		} catch (InvalidRequestException e) {
				e.printStackTrace();
				return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newOffer,HttpStatus.CREATED);
	} 
}
