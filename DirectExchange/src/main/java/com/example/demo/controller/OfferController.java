package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;

import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Common.CommonUtilities;
import com.example.demo.dto.Offer;
import com.example.demo.dto.User;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.service.IOfferService;

@RestController
public class OfferController {

	@Autowired
	private IOfferService offerService;
	
	@RequestMapping(value="offer", method = RequestMethod.POST)
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
	
	@RequestMapping(value="offer", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateOffer(@RequestBody Offer offer){
		Offer newOffer;
		if(offer.getId() == null) 
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Not Found", "404", "Offer Id not present") ,HttpStatus.BAD_REQUEST);
		if(offer.getUser().getId() == null) 
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Not Found", "404", "User Id not present") ,HttpStatus.BAD_REQUEST);
	
		try {
			newOffer = offerService.update(offer);
		} catch (InvalidRequestException e) {
				e.printStackTrace();
				return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newOffer,HttpStatus.CREATED);
	} 

	@RequestMapping(value="offer/userId/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllOffersByUser(@PathVariable("userId") Long userId){
		List<Offer> newOffer;
		try {
			newOffer = offerService.findOffersForUser(userId);
		} catch (InvalidRequestException e) {
				e.printStackTrace();
				return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newOffer,HttpStatus.OK);
	}

	@RequestMapping(value="offer", method = RequestMethod.GET)
	public ResponseEntity<?> getAllOffers(){
		List<Offer> newOffer;
		try {
			newOffer = offerService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newOffer,HttpStatus.OK);
	}
	
	@RequestMapping(value="offer/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllOffers(@PathParam("sourceCurrency") String sourceCurrency,
			@PathParam("destinationCurrency") String destinationCurrency,
			@PathParam("amount") Double amount,
			@PathParam("destinationAmount") Double destinationAmount,
			@PathParam("page") int page,@PathVariable("userId") Long userId){
		List<Offer> newOffer;
		try {
			newOffer = offerService.findAllWithFiltering(sourceCurrency, amount,destinationCurrency,destinationAmount, userId,page,10);
			
			//newOffer = offerService.findAllWithoutUserOffer(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newOffer,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="offer/offerId/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getOffferByID(@PathVariable("id") Long id){
		Offer newOffer;
		try {
			newOffer = offerService.findOne(id);
		} catch (InvalidRequestException e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newOffer,HttpStatus.OK);
	}
	
	@RequestMapping(value="offer/getCounterOffer/{id}/userId/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCounterOfferByOfferID(@PathVariable("id") Long id,@PathVariable("userId") Long userId){
		List<Offer> newOffer;
		try {
			newOffer = offerService.findCounterOffers(id,userId);
		} catch (InvalidRequestException e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newOffer,HttpStatus.OK);
	}

	@RequestMapping(value="offer/matchingOffer", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMatchingOffer(@PathParam("id") Long id, @PathParam("userId") Long userId){
		HashMap<String,Object> newOffer;
		try {
			newOffer = offerService.getMatchingOffer(id, userId);
		} catch (InvalidRequestException e) {
				e.printStackTrace();
				return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newOffer,HttpStatus.OK);
	} 
	
	@RequestMapping(value="offer/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteOffer(@PathVariable Long id){
		try {
			 offerService.delete(id);
		} catch (InvalidRequestException e) {
				e.printStackTrace();
				return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Offer Deleted Successfully",HttpStatus.OK);
	} 

	@RequestMapping(value="offer/acceptOfferFromBrosePage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> acceptOfferFromBrosePage(@PathParam("offerId") Long offerId, @RequestBody User user){
		boolean flag;
		try {
			flag = offerService.acceptOfferFromBrosePage(offerId,user);
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Offer Accepted",HttpStatus.CREATED);
	} 
	
	
	@RequestMapping(value="offer/acceptCounterOfferFromBrosePage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> acceptCounterOfferFromBrosePage(@PathParam("offerId") Long offerId){
		boolean flag;
		try {
			flag = offerService.acceptCounterOfferFromBrosePage(offerId);
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Offer Accepted is"+ flag,HttpStatus.CREATED);
	} 
	
	
	@RequestMapping(value="offer/acceptOfferFromMyOffer", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> acceptOfferFromMyOffer(@PathParam("myOfferId") Long myOfferId, @PathParam("acceptedOfferId") Long acceptedOfferId){
		boolean flag;
		try {
			flag = offerService.acceptOfferFromMyOffer(myOfferId,acceptedOfferId);
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Offer Accepted is"+ flag,HttpStatus.CREATED);
	}	

	//If flagType is True B Plus C
	@RequestMapping(value="offer/acceptSplitOfferFromMyOffer/{flagType}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> acceptSplitOfferFromMyOfferBPlusC(@PathVariable("flagType") Boolean flagType,@RequestBody List<Long> offers) throws Exception{
		boolean flag;
		try {
			if(flagType) {
				flag = offerService.acceptSplitOfferFromMyOfferBPlusC(offers);			
			}else {
				flag = offerService.acceptSplitOfferFromMyOfferCMinusB(offers);
				
			}
		} catch (Exception e) {
			return new ResponseEntity<>(CommonUtilities.getErrorMessage("Bad Request", "400", e.getMessage()) ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Offer Accepted is "+flag,HttpStatus.CREATED);
	}
	
}
