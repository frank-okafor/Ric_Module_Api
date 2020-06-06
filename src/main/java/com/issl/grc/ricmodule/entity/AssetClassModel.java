package com.issl.grc.ricmodule.entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetClassModel {

	String schemeId;
	Integer assetClassId;
	Double globalLimitMax;
	Double globalLimitMin;
	Double AAA;
	Double AA;
	Double A;
	Double BBB;
	Double BB;
	Double B;
	Double Junk;
	Double commercialPaper;
	Double discHouse;
	Double perStock;
	Double issdShareCap;
	Double perSector;
	Double fixedIncomeMax;
	Double limitPerBondIssuer;
	Double limitPerBondIssue;
	Double limitPerState;
	Double limitPerIssue;
	Double limitPerIssuer;
	Double limitPerFund;
	Double limitGovtBackedBonds;
	Double limitNonBackedBonds;
	Double limitofFgnBonds;
	Double limitofTreasuryBills;
	Double LimitofFGNSecurities;
	Double limitofFGNEurobond;
	Double limitPerIssueOfFGNEurobond;
	Double limitPerInfrastructureBond;
	Double limitPerEachCorporateEurobondIssue,limitPerIssuerForCommercialPaper,LimitPerStock;
}
