package com.issl.grc.ricmodule.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.issl.grc.ricmodule.entity.AssetClass;
import com.issl.grc.ricmodule.entity.InvestorEntity;
import com.issl.grc.ricmodule.exception.CustomErrorMessage;
import com.issl.grc.ricmodule.service.implimentation.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/ric_module/v1/api")
@Api(value = "RIC Module API calls")
public class ApplicationController {
	
	@Autowired
	private ApplicationService service;
	
	@GetMapping("/getinvestorscount")
    @ApiOperation(value = "Get count of investors")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "successful", response = Integer.class),
        @ApiResponse(code = 400, message = "incorrect information provided", response = CustomErrorMessage.class),
        @ApiResponse(code = 500, message = "internal error from database or other system functions - critical!", response = CustomErrorMessage.class)
    })
    public ResponseEntity<?> getInvestorsCount(@RequestParam(value = "sector", required = true)String sector, @RequestParam(value = "assetclassname", required = true)String assetClass,
    		@RequestParam(value = "rag", required = true)String rag, @RequestParam(value = "startDate", required = true)LocalDate startDate) throws Exception {
        return new ResponseEntity<>(service.getInvestorsCount(sector, assetClass, rag, startDate), HttpStatus.OK);
    }
	
	@GetMapping("/getinvestors")
    @ApiOperation(value = "Get list of investors")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "successful", response = InvestorEntity.class,responseContainer = "List"),
        @ApiResponse(code = 400, message = "incorrect information provided", response = CustomErrorMessage.class),
        @ApiResponse(code = 500, message = "internal error from database or other system functions - critical!", response = CustomErrorMessage.class)
    })
    public ResponseEntity<?> getInvestors() throws Exception {
        return new ResponseEntity<>(service.getInvestors(), HttpStatus.OK);
    }
	
	@GetMapping("/getinvestorsassets")
    @ApiOperation(value = "Get list of investors Assets")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "successful", response = AssetClass.class,responseContainer = "List"),
        @ApiResponse(code = 400, message = "incorrect information provided", response = CustomErrorMessage.class),
        @ApiResponse(code = 500, message = "internal error from database or other system functions - critical!", response = CustomErrorMessage.class)
    })
    public ResponseEntity<?> getInvestorsAssets(@RequestParam(value = "assetclassname", required = true)String assetClass,@RequestParam(value = "sector", required = true)String sector,
    		@RequestParam(value = "startdate", required = true)LocalDate startDate,@RequestParam(value = "rag", required = true)String rag) throws Exception {
        return new ResponseEntity<>(service.getInvestorsAssets(assetClass, sector, startDate, rag), HttpStatus.OK);
    }
	
	@GetMapping("/getinvestorsname")
    @ApiOperation(value = "Get list of investors name")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "successful", response = InvestorEntity.class,responseContainer = "List"),
        @ApiResponse(code = 400, message = "incorrect information provided", response = CustomErrorMessage.class),
        @ApiResponse(code = 500, message = "internal error from database or other system functions - critical!", response = CustomErrorMessage.class)
    })
    public ResponseEntity<?> getInvestorsName() throws Exception {
        return new ResponseEntity<>(service.getInvestorsName(), HttpStatus.OK);
    }
	
	@GetMapping("/getinvestorsassetsname")
    @ApiOperation(value = "Get list of investors Assets Names")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "successful", response = AssetClass.class,responseContainer = "List"),
        @ApiResponse(code = 400, message = "incorrect information provided", response = CustomErrorMessage.class),
        @ApiResponse(code = 500, message = "internal error from database or other system functions - critical!", response = CustomErrorMessage.class)
    })
    public ResponseEntity<?> getInvestorsAssetsName(@RequestParam(value = "assetclassname", required = true)String assetClass,@RequestParam(value = "sector", required = true)String sector,
    		@RequestParam(value = "startdate", required = true)LocalDate startDate,@RequestParam(value = "rag", required = true)String rag,@RequestParam(value = "fundid", required = true)String id) throws Exception {
        return new ResponseEntity<>(service.getInvestorsAssetsName(id, assetClass, sector, startDate, rag), HttpStatus.OK);
    }
}
