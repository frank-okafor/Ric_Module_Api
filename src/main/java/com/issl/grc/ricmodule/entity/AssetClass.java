package com.issl.grc.ricmodule.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetClass {

	private String valuationDate,fundId,fundname,rag,sector,assetInstrRating,assetInstrName,assetInstrId,assetClassName,issuer;
	private Double netAssetValue,assetClassTotal,price,assetClassLimitValue,assetClassLimitPct,assetLimitperItemPct,AssetLimitperSharePct,unusedLimit,percentCoyHeldByPortfolio,
	                    assetIssuerLimitValue,assetIssuerLimitPct,assetLimitperItemValue,currentValue,exposurePct,exposureToNavPct,exposurePctToNavVariance,issuePct,assetInstrValue,variance,issueVariance,PropertyCost;
	private long totalShares,assetInstrQty;
	private Date maturityDate;
}
