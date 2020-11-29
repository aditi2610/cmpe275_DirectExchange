package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Offer;

@RestController
public class OfferController {

	@RequestMapping(value="createOffer", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> createOffer(@RequestBody Offer offer){
		System.out.print("Here"+offer.getDestinationCountry());
		return null;
	} 
}
