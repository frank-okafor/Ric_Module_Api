package com.issl.grc.ricmodule.service.implimentation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.issl.grc.ricmodule.entity.AssetClass;
import com.issl.grc.ricmodule.entity.InvestorEntity;
import com.issl.grc.ricmodule.repository.ApplicationRepository;

@Service
public class ApplicationService {
	
	@Autowired
	private ApplicationRepository repo;
	
	public Integer getInvestorsCount(String sector, String assetClass,String rag, LocalDate start) {
		return repo.investorsCount(sector, assetClass, rag, start);
	}
	
	public List<InvestorEntity> getInvestors(){
		return repo.investors();
	}
	
	public List<AssetClass> getInvestorsAssets(String assetClass,String sector,LocalDate start,String rag){
		return repo.investorsAssets1(assetClass, sector, start, rag);
	}
	
	public List<InvestorEntity> getInvestorsName(){
		return repo.assetClass();
	}
	
	public List<AssetClass> getInvestorsAssetsName(String id, String assetClass, String sector, LocalDate start, String rag){
		return repo.investorsAssets(id, assetClass, sector, start, rag);
	}

}
