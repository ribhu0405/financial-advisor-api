/**
 * 
 */
package com.fin.advisor.service;

import static com.fin.advisor.constant.ApplicationConstant.YES_FLAG;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fin.advisor.dto.RiskLevelDTO;
import com.fin.advisor.entity.RiskLevel;
import com.fin.advisor.repository.RiskLevelRepo;

/**
 * @author rajesh.kumar
 * Service Impl class for Risk Levels. 
 */
@Service
public class RiskServiceImpl implements RiskService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RiskLevelRepo riskLevelRepo;

	List<RiskLevelDTO> riskLevelDTOList = new ArrayList<>();

	/**
	 * Method to get Risk Levels from cache.
	 * 
	 * @return List<RiskLevel>
	 */
	public List<RiskLevelDTO> getRiskLevels() {
		if (ObjectUtils.isEmpty(riskLevelDTOList)) {
			fetchRiskLevelsFromDb();
		}
		logger.info("riskLevelDTOList size: "+riskLevelDTOList.size());
		return riskLevelDTOList;
	}

	/**
	 * Method to fetch Risk Levels from database
	 */
	
	@PostConstruct
	public void fetchRiskLevelsFromDb() {
		convertToRiskLevelDTO(riskLevelRepo.findByActiveFlag(YES_FLAG));
	}
	
	private void convertToRiskLevelDTO(List<RiskLevel> riskLevelList ) {
		riskLevelList.stream().forEach(x -> {
			RiskLevelDTO riskLevelDTO = new RiskLevelDTO();
			riskLevelDTO.setToleranceLevel(x.getId());
			riskLevelDTO.setBondsShare(x.getBonds());
			riskLevelDTO.setLargeCapShare(x.getLargeCap());
			riskLevelDTO.setMidCapShare(x.getMidCap());
			riskLevelDTO.setForeignCapShare(x.getForeignCap());
			riskLevelDTO.setSmallCapShare(x.getSmallCap());
			riskLevelDTOList.add(riskLevelDTO);
		});
	}

	@Override
	public RiskLevelDTO getRiskToleranceData(Integer riskToleranceScore) {
		if (ObjectUtils.isEmpty(riskLevelDTOList)) {
			fetchRiskLevelsFromDb();
		}
		logger.debug("size:: "+riskLevelDTOList.size());
		return riskLevelDTOList.get(riskToleranceScore-1);
	}
}
