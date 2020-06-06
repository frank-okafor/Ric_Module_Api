package com.issl.grc.ricmodule.repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

import com.issl.grc.ricmodule.entity.AssetClass;
import com.issl.grc.ricmodule.entity.InvestorEntity;

import lombok.Data;

@Service
@Data
public class ApplicationRepository {

	private Connection con;
	private PreparedStatement pt;
	private ResultSet rs;

	private final static String URL = "jdbc:sqlserver://db.issl.ng:1595;databaseName=gresham";
	private final static String PWD = "Issl$$dev";
	private final static String USER = "devteam";
	
	public ApplicationRepository() {
		try {
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			con = DriverManager.getConnection(URL, USER, PWD);
		} catch (Exception ex) {
			Logger.getLogger(ApplicationRepository.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public Integer investorsCount( String sector, String assetClass,String rag, LocalDate start) {
		Integer total = 0;
		try { 
			String query = "select count(fundname) as total from RICLimitDetail where AssetInstrRAG = '"+rag+"' and AssetClassName = '"+assetClass+"' and Sector = '"+sector+"' and CheckType = 'G' and ValuationDate = '"+start+"'";
			//System.out.println(query);
			pt = con.prepareStatement(query);
			rs = pt.executeQuery();
			while (rs.next()) {
				total = rs.getInt("total");
			}
		} catch (SQLException ex) {
			Logger.getLogger(ApplicationRepository.class.getName()).log(Level.SEVERE, null, ex);
		}
		return total;
	}
	
	public List<AssetClass> investorsAssets1(String assetClass,String sector,LocalDate start,String rag) {
		List<AssetClass> investors = new ArrayList<>();
		
		try {
			String query = "";
		
			 if(sector.equalsIgnoreCase("FGN Securities")) {
			    query = "SELECT FundName,AssetClassTotal,Sector,AssetClassLimitValue,AssetClassName,NetAssetValue,AssetClassLimitPct,FundId FROM RICLimitDetail"
			    + " WHERE CheckType = 'G' and Sector = 'Nigerian Treasury Bill' or Sector = 'FGN Bond' or Sector = 'FGN Securities' "
			    + "or Sector = 'FGN Euro Bond' and ValuationDate = '"+start+"' and AssetInstrRAG = '"+rag+"'";
			 }else {
				 query = "SELECT FundName,AssetClassTotal,Sector,AssetClassLimitValue,AssetClassName,NetAssetValue,AssetClassLimitPct,FundId FROM RICLimitDetail WHERE CheckType = 'G' and AssetClassName = '"+ assetClass +"' and "
							+ "Sector = '"+ sector +"' and ValuationDate = '"+start+"' and AssetInstrRAG = '"+rag+"'";
			 }
			pt = con.prepareStatement(query);
			rs = pt.executeQuery();
			while (rs.next()) {
				AssetClass asset = new AssetClass();
				asset.setFundname(rs.getString("FundName") == null ? "" : rs.getString("FundName"));
				asset.setFundId(rs.getString("FundId") == null ? "" : rs.getString("FundId"));
				asset.setAssetClassLimitValue(rs.getDouble("AssetClassLimitValue")== 0.0d ? 0.0d : rs.getDouble("AssetClassLimitValue"));
				asset.setAssetClassTotal(rs.getDouble("AssetClassTotal") == 0.0d ? 0.00 : rs.getDouble("AssetClassTotal"));
				asset.setNetAssetValue(rs.getDouble("NetAssetValue")== 0.0d ? 0.0d : rs.getDouble("NetAssetValue"));
				asset.setAssetClassLimitPct(rs.getDouble("AssetClassLimitPct") == 0.0d ? 0.00 : rs.getDouble("AssetClassLimitPct"));
				asset.setAssetClassName(rs.getString("AssetClassName") == null ? "" : rs.getString("AssetClassName"));
				asset.setSector(rs.getString("Sector") == null ? "" : rs.getString("Sector"));
     			
				
				double VarianceVal = rs.getDouble("AssetClassLimitValue") - rs.getDouble("AssetClassTotal") ;
     			asset.setVariance(VarianceVal);
				
     			
     			double ExposureToNav = BigDecimal.valueOf(((rs.getDouble("AssetClassTotal") *100)/rs.getDouble("NetAssetValue"))).setScale(4,RoundingMode.HALF_UP).doubleValue();
     			asset.setExposureToNavPct(ExposureToNav);
     			double exposurePctToNavVariance = BigDecimal.valueOf(((rs.getDouble("AssetClassLimitPct") - asset.getExposureToNavPct()))).setScale(4,RoundingMode.HALF_UP).doubleValue();
     			asset.setExposurePctToNavVariance(exposurePctToNavVariance);

     			
				investors.add(asset);
				//System.out.println(investors);
			}
		} catch (SQLException ex) {
			Logger.getLogger(ApplicationRepository.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return investors;
}
	
	public List<InvestorEntity> investors() {
		List<InvestorEntity> investors = new ArrayList<>();
		try {
			String query = "SELECT scheme_name,cast(cast(SCHEME_ID as bigint) as nvarchar(20)) as SCHEME_ID FROM scheme where SCHEME_NAME is not null "
					+ "and SCHEME_ID is not null order by SCHEME_NAME asc";

			pt = con.prepareStatement(query);
			rs = pt.executeQuery();
			while (rs.next()) {
				InvestorEntity entity = new InvestorEntity();
				entity.setName(rs.getString("scheme_name") == null ? "" : rs.getString("scheme_name"));
				entity.setId(rs.getString("SCHEME_ID"));
				entity.setRowId(rs.getRow());
				
				investors.add(entity);
			}
			//con.close();
		} catch (SQLException ex) {
			Logger.getLogger(ApplicationRepository.class.getName()).log(Level.SEVERE, null, ex);
		}
		return investors;
	}
	
	public List<InvestorEntity> assetClass() {
		List<InvestorEntity> investors = new ArrayList<>();
		try {
			String query = "SELECT assetClass_name,assetClassID FROM assetClass where assetClass_name is not null "
					+ "and assetClassID is not null";

			pt = con.prepareStatement(query);
			rs = pt.executeQuery();
			while (rs.next()) {
				InvestorEntity entity = new InvestorEntity();
				entity.setName(rs.getString("assetClass_name") == null ? "" : rs.getString("scheme_name"));
				entity.setId(rs.getString("assetClassID"));
				investors.add(entity);
			}
			//con.close();
		} catch (SQLException ex) {
			Logger.getLogger(ApplicationRepository.class.getName()).log(Level.SEVERE, null, ex);
		}
		return investors;
	}
	
	public List<AssetClass> investorsAssets(String id, String assetClass, String sector, LocalDate start, String rag) {
		List<AssetClass> investors = new ArrayList<>();
		try {
			String query = "";
			
			if(rag.equalsIgnoreCase("")){
				query = "SELECT * FROM RICLimitDetail WHERE CheckType = 'I' and AssetClassName = '"+ assetClass +"' and "
						+ "AssetClassSubName = '"+ sector +"' and ValuationDate = '"+ start +"' and  FundId = " + id ;
			}else {
				query = "SELECT * FROM RICLimitDetail WHERE CheckType = 'I' and AssetClassName = '"+ assetClass +"' and "
						+ "AssetClassSubName = '"+ sector +"' and ValuationDate = '"+ start +"' and AssetInstrRAG = '"+ rag +"'";
			}
			if(sector.equalsIgnoreCase("FGN Securities")) {
				query = "SELECT * FROM RICLimitDetail WHERE CheckType = 'I' and AssetClassSubName = 'Corporate Euro Bond' or "
						+ "AssetClassSubName = 'Corporate Bond' and ValuationDate = '"+ start +"' and AssetInstrRAG = '"+ rag +"'";
			}else if(sector.equalsIgnoreCase("Corporate Bond")) {
				query = "SELECT * FROM RICLimitDetail WHERE CheckType = 'I' and AssetClassSubName = 'Nigerian Treasury Bill' or "
						+ "AssetClassSubName = 'FGN Bond' or AssetClassSubName = 'FGN Securities' or AssetClassSubName = 'FGN Euro Bond' and "
						+ "ValuationDate = '"+ start +"' and AssetInstrRAG = '"+ rag +"'";
			}
			
			pt = con.prepareStatement(query);
			rs = pt.executeQuery();
			while (rs.next()) {
				AssetClass asset = new AssetClass();
				asset.setFundname(rs.getString("FundName") == null ? "" : rs.getString("FundName"));
				asset.setFundId(rs.getString("FundId") == null ? "" : rs.getString("FundId"));
				asset.setValuationDate(rs.getString("ReportDate"));
				asset.setNetAssetValue(rs.getDouble("NetAssetValue") == 0.0d ? 0.0d : rs.getDouble("NetAssetValue"));
				asset.setAssetClassLimitPct(rs.getDouble("AssetClassLimitPct")== 0.0d ? 0.0d : rs.getDouble("AssetClassLimitPct"));
				asset.setAssetClassLimitValue(rs.getDouble("AssetClassLimitValue")== 0.0d ? 0.0d : rs.getDouble("AssetClassLimitValue"));
				asset.setAssetInstrValue(rs.getDouble("AssetInstrValue") == 0.0d ? 0.00 : rs.getDouble("AssetInstrValue"));
				asset.setAssetClassTotal(rs.getDouble("AssetClassTotal") == 0.0d ? 0.00 : rs.getDouble("AssetClassTotal"));
				asset.setSector(rs.getString("AssetClassSubName")== null ? "" : rs.getString("AssetClassSubName"));
				asset.setCurrentValue(rs.getDouble("currentValue") == 0.00 ? 0.00 : rs.getDouble("currentValue"));
				asset.setAssetIssuerLimitPct(rs.getDouble("AssetIssuerLimitPct") == 0.0d ? 0.0d : rs.getDouble("AssetIssuerLimitPct"));
				asset.setRag(rs.getString("AssetInstrRAG") == null ? "" : rs.getString("AssetInstrRAG"));
				asset.setAssetIssuerLimitValue(rs.getDouble("AssetIssuerLimitValue"));
				asset.setPrice(rs.getDouble("AssetInstrAvgUnitPrice") == 0.0d ? 0.0d : rs.getDouble("AssetInstrAvgUnitPrice"));
				asset.setAssetInstrQty(rs.getLong("AssetInstrQty") == 0l ? 0l : rs.getLong("AssetInstrQty"));
				asset.setAssetInstrName(rs.getString("AssetInstrName") == null ? "" : rs.getString("AssetInstrName"));
				asset.setAssetInstrId(rs.getString("AssetInstrId") == null ? "" : rs.getString("AssetInstrId"));
     			asset.setAssetInstrRating(rs.getString("AssetInstrRating")== null ? "" : rs.getString("AssetInstrRating"));
     			asset.setTotalShares(rs.getLong("TotalShares"));// == 0l ? 0l : rs.getLong("TotalShares"));
     		    asset.setAssetClassName(rs.getString("AssetClassName") == null ? "" : rs.getString("AssetClassName"));
     		    asset.setUnusedLimit(rs.getDouble("AssetIssuerLimitValue") - rs.getDouble("currentValue"));
     		    asset.setAssetLimitperItemPct(rs.getDouble("AssetLimitperItemPct") == 0.0d ? 0.0d : rs.getDouble("AssetLimitperItemPct"));
     		    asset.setAssetLimitperSharePct(rs.getDouble("AssetLimitperSharePct") == 0.0d ? 0.0d : rs.getDouble("AssetLimitperSharePct"));
     		    asset.setMaturityDate(rs.getDate("MM_Maturity_Date") == null ? null : rs.getDate("MM_Maturity_Date"));
    		    asset.setPropertyCost(rs.getDouble("Property_cost") == 0.0d ? 0.0d : rs.getDouble("Property_cost"));
    		    asset.setIssuer(rs.getString("Issuer") == null ? "" : rs.getString("Issuer"));
                double heldByPortfolio= 0.0d;
     		    
//     		     System.out.println("Total Shares >>>"+rs.getLong("TotalShares"));
//    		     System.out.println("Total Quanitity >>>"+rs.getLong("TotalShares"));
     		   
     		    if (rs.getLong("TotalShares")!= 0l) { 
					heldByPortfolio = BigDecimal.valueOf((((double)rs.getLong("AssetInstrQty") * 100) / (double)rs.getLong("TotalShares"))).setScale(4,RoundingMode.HALF_UP).doubleValue();
					//System.out.println("heldByPortfolio >>>"+heldByPortfolio);
				}
     		    
     		    asset.setPercentCoyHeldByPortfolio(heldByPortfolio);
     			
				double Exposure = BigDecimal.valueOf(((rs.getDouble("currentValue") *100)/rs.getDouble("NetAssetValue"))).setScale(4,RoundingMode.HALF_UP).doubleValue();
     			asset.setExposurePct(Exposure);
     			double VarianceVal = BigDecimal.valueOf(((rs.getDouble("AssetIssuerLimitPct") - asset.getExposurePct()))).setScale(4,RoundingMode.HALF_UP).doubleValue();
     			asset.setVariance(VarianceVal);
     			
     			double ExposureToNav = BigDecimal.valueOf(((rs.getDouble("AssetClassTotal") *100)/rs.getDouble("NetAssetValue"))).setScale(4,RoundingMode.HALF_UP).doubleValue();
     			asset.setExposureToNavPct(ExposureToNav);
     			double exposurePctToNavVariance = BigDecimal.valueOf(((rs.getDouble("AssetClassLimitPct") - asset.getExposureToNavPct()))).setScale(4,RoundingMode.HALF_UP).doubleValue();
     			asset.setExposurePctToNavVariance(exposurePctToNavVariance);
     			
     			if (rs.getLong("TotalShares")!= 0l) { 
	     			double issue = BigDecimal.valueOf(((rs.getDouble("currentValue") *100)/(double)rs.getLong("TotalShares"))).setScale(4,RoundingMode.HALF_UP).doubleValue();
	     			asset.setIssuePct(issue);
     			}
     			
     			if (rs.getLong("TotalShares")!= 0l) { 
	     			double Varianceissue = BigDecimal.valueOf(((rs.getDouble("AssetIssuerLimitPct") - asset.getIssuePct()))).setScale(4,RoundingMode.HALF_UP).doubleValue();
	     			asset.setIssueVariance(Varianceissue);
     			}
     			//asset.setExposurePctStr(String.valueOf(Exposure));
     			//double EVarianceVal = BigDecimal.valueOf(((rs.getDouble("AssetLimitperItemPct") - asset.getExposurePct()))).setScale(4,RoundingMode.HALF_UP).doubleValue();
     			//asset.setEquityVariance(EVarianceVal);
     			//asset.setAssetLimitperItemPct(rs.getDouble("AssetLimitperItemPct") == 0.0d ? 0.0d : rs.getDouble("AssetLimitperItemPct"));
				//asset.setAssetLimitperItemValue(rs.getDouble("AsssetLimitperItemValue") == 0.0d ? 0.0d : rs.getDouble("AsssetLimitperItemValue"));
//				asset.setNetAssetValueDou(rs.getDouble("NetAssetValue") == 0.0d ? 0.0d : rs.getDouble("NetAssetValue"));
//     			asset.setExposurePct(Math.floor((rs.getDouble("currentValue") *100)/rs.getDouble("NetAssetValue")));
//     			asset.setExposurePctStr(String.valueOf(Math.floor((rs.getDouble("currentValue") *100)/rs.getDouble("NetAssetValue"))));
//				asset.setExposurePctStr(asset.getExposurePct().toString());
//				asset.setPortfolioValueAsANav(rs.getDouble("AssetIssuerLimitPct"));
//				asset.setCheckType(rs.getString("CheckType") == null ? "" : rs.getString("CheckType"));
//				asset.setAssetIssuerLimitValueMM((rs.getDouble("AssetIssuerLimitPct") / 100) * rs.getDouble("NetAssetValue"));
//				asset.setAssetInstrId(rs.getString("AssetInstrId") == null ? "" : rs.getString("AssetInstrId"));
//				asset.setNoOfSharesInCompany(rs.getLong("AssetInstrQty") == 0l ? 0l : rs.getLong("AssetInstrQty"));
//				asset.setBond(rs.getDouble("AssetLimitperItemPct") == 0.0d ? 0.0d : rs.getDouble("AssetLimitperItemPct"));
//				asset.setAssetClassTotal(rs.getDouble("AssetClassTotal") == 0.0d ? 0.0d : rs.getDouble("AssetClassTotal"));
				
				investors.add(asset);
				//System.out.println(investors);
				//con.close();
			}
		} catch (SQLException ex) {
			Logger.getLogger(ApplicationRepository.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return investors;
	}
}
