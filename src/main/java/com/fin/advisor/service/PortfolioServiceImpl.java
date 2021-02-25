/**
 * 
 */
package com.fin.advisor.service;

import static com.fin.advisor.constant.ApplicationConstant.BONDS_STRING;
import static com.fin.advisor.constant.ApplicationConstant.FOREIGN_STRING;
import static com.fin.advisor.constant.ApplicationConstant.LARGE_CAP_STRING;
import static com.fin.advisor.constant.ApplicationConstant.MID_CAP_STRING;
import static com.fin.advisor.constant.ApplicationConstant.SMALL_CAP_STRING;
import static com.fin.advisor.constant.ApplicationConstant.TRANSFER_MSG;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fin.advisor.common.CommonUtil;
import com.fin.advisor.dto.CustomPortfolioDTO;
import com.fin.advisor.dto.IdealPortfolioDTO;
import com.fin.advisor.dto.RiskLevelDTO;

/**
 * @author amartya.bhattacharyya
 * Service Impl class for Portfolio.
 */
@Service
public class PortfolioServiceImpl implements PortfolioService {

	@Autowired
	RiskService riskService;

	List<String> msgList;
	
	@Autowired
	CommonUtil commonUtil;

	@Override
	public IdealPortfolioDTO calculateIdealPortfolio(Double totalAmount, Integer riskToleranceScore) {
		IdealPortfolioDTO portfolioDTO = new IdealPortfolioDTO();
		portfolioDTO.setToleranceLevel(riskToleranceScore);
		portfolioDTO.setTotalAmount(totalAmount);
		RiskLevelDTO riskLevelDTO = riskService.getRiskToleranceData(riskToleranceScore);

		if (!ObjectUtils.isEmpty(riskLevelDTO.getToleranceLevel())) {
			portfolioDTO.setBondsCalVal((double) Math.round(totalAmount * (riskLevelDTO.getBondsShare() / 100.0)));
			portfolioDTO.setLargeCapCalVal(
					(double) Math.round(totalAmount * (riskLevelDTO.getLargeCapShare() / 100.0)));
			portfolioDTO
					.setMidCapCalVal((double) Math.round(totalAmount * (riskLevelDTO.getMidCapShare() / 100.0)));
			portfolioDTO.setForeignCapCalVal(
					(double) Math.round(totalAmount * (riskLevelDTO.getForeignCapShare() / 100.0)));
			portfolioDTO.setSmallCapCalVal(
					(double) Math.round(totalAmount * (riskLevelDTO.getSmallCapShare() / 100.0)));
		}
		return portfolioDTO;

	}

	@Override
	public CustomPortfolioDTO calculateCustomPortfolio(CustomPortfolioDTO customPortfolioDTO) {
		msgList = new ArrayList<>();
		Double total_amount = customPortfolioDTO.getBondsCustomVal() + customPortfolioDTO.getLargeCapCustomVal()
				+ customPortfolioDTO.getMidCapCustomVal() + customPortfolioDTO.getForeignCapCustomVal()
				+ customPortfolioDTO.getSmallCapCustomVal();

		customPortfolioDTO
				.setIdealPortfolioDTO(calculateIdealPortfolio(total_amount, customPortfolioDTO.getToleranceLevel()));

		customPortfolioDTO.setBondsDiffVal(customPortfolioDTO.getIdealPortfolioDTO().getBondsCalVal()
				- customPortfolioDTO.getBondsCustomVal());
		customPortfolioDTO.setLargeCapDiffVal(customPortfolioDTO.getIdealPortfolioDTO().getLargeCapCalVal()
				- customPortfolioDTO.getLargeCapCustomVal());
		customPortfolioDTO.setMidCapDiffVal(customPortfolioDTO.getIdealPortfolioDTO().getMidCapCalVal()
				- customPortfolioDTO.getMidCapCustomVal());
		customPortfolioDTO.setForeignCapDiffVal(customPortfolioDTO.getIdealPortfolioDTO().getForeignCapCalVal()
				- customPortfolioDTO.getForeignCapCustomVal());
		customPortfolioDTO.setSmallCapDifVal(customPortfolioDTO.getIdealPortfolioDTO().getSmallCapCalVal()
				- customPortfolioDTO.getSmallCapCustomVal());
		// required calculation.
		Map<String, Double> differenceMap = new HashMap<>();
		differenceMap.put(BONDS_STRING, customPortfolioDTO.getBondsDiffVal());
		differenceMap.put(LARGE_CAP_STRING, customPortfolioDTO.getLargeCapDiffVal());
		differenceMap.put(MID_CAP_STRING, customPortfolioDTO.getMidCapDiffVal());
		differenceMap.put(FOREIGN_STRING, customPortfolioDTO.getForeignCapDiffVal());
		differenceMap.put(SMALL_CAP_STRING, customPortfolioDTO.getSmallCapDifVal());

		cal(BONDS_STRING, differenceMap);
		cal(LARGE_CAP_STRING, differenceMap);
		cal(MID_CAP_STRING, differenceMap);
		cal(FOREIGN_STRING, differenceMap);

		customPortfolioDTO.setRecommendedTransferList(msgList);

		return customPortfolioDTO;
	}



	private void cal(String entity, Map<String, Double> differenceMap) {
		if (differenceMap.get(entity) > 0) {
			double min = 0.00;
			if (entity == BONDS_STRING) {
				min = commonUtil.findMin(differenceMap.get(LARGE_CAP_STRING), differenceMap.get(MID_CAP_STRING),
						differenceMap.get(FOREIGN_STRING), differenceMap.get(SMALL_CAP_STRING));
			} else if (entity == LARGE_CAP_STRING) {
				min = commonUtil.findMin(differenceMap.get(MID_CAP_STRING), differenceMap.get(FOREIGN_STRING),
						differenceMap.get(SMALL_CAP_STRING), differenceMap.get(BONDS_STRING));
			} else if (entity == MID_CAP_STRING) {
				min = commonUtil.findMin(differenceMap.get(FOREIGN_STRING), differenceMap.get(SMALL_CAP_STRING),
						differenceMap.get(BONDS_STRING), differenceMap.get(LARGE_CAP_STRING));
			} else if (entity == FOREIGN_STRING) {
				min = commonUtil.findMin(differenceMap.get(LARGE_CAP_STRING), differenceMap.get(MID_CAP_STRING),
						differenceMap.get(BONDS_STRING), differenceMap.get(SMALL_CAP_STRING));
			} else {
				min = commonUtil.findMin(differenceMap.get(LARGE_CAP_STRING), differenceMap.get(MID_CAP_STRING),
						differenceMap.get(FOREIGN_STRING), differenceMap.get(BONDS_STRING));
			}

			if (differenceMap.get(SMALL_CAP_STRING) != 0.00 && differenceMap.get(SMALL_CAP_STRING) == min) {
				if (differenceMap.get(entity) + min == 0.00) {
					updateCalcuatedData(min, differenceMap, SMALL_CAP_STRING, entity);
				} else if (differenceMap.get(entity) + min > 0.00) {
					updateCalcuatedData(min, differenceMap, SMALL_CAP_STRING, entity);
					cal(entity, differenceMap);
				} else {
					msgList.add(MessageFormat.format(TRANSFER_MSG, commonUtil.getCurrencyValueOf(differenceMap.get(entity)),
							SMALL_CAP_STRING, entity));
					differenceMap.put(SMALL_CAP_STRING, differenceMap.get(entity) + min);
					differenceMap.put(entity, 0.00);

				}

			}
			if (differenceMap.get(FOREIGN_STRING) != 0.00 && differenceMap.get(FOREIGN_STRING) == min) {
				if (differenceMap.get(entity) + min == 0.00) {
					updateCalcuatedData(min, differenceMap, FOREIGN_STRING, entity);
				} else if (differenceMap.get(entity) + min > 0.00) {
					updateCalcuatedData(min, differenceMap, FOREIGN_STRING, entity);
					cal(entity, differenceMap);

				} else {
					msgList.add(MessageFormat.format(TRANSFER_MSG, commonUtil.getCurrencyValueOf(differenceMap.get(entity)),
							FOREIGN_STRING, entity));
					differenceMap.put(FOREIGN_STRING, differenceMap.get(entity) + min);
					differenceMap.put(entity, 0.00);

				}
			}
			if (differenceMap.get(MID_CAP_STRING) != 0.00 && differenceMap.get(MID_CAP_STRING) == min) {
				if (differenceMap.get(entity) + min == 0.00) {
					updateCalcuatedData(min, differenceMap, MID_CAP_STRING, entity);
				} else if (differenceMap.get(entity) + min > 0.00) {
					updateCalcuatedData(min, differenceMap, MID_CAP_STRING, entity);
					cal(entity, differenceMap);

				} else {
					msgList.add(MessageFormat.format(TRANSFER_MSG, commonUtil.getCurrencyValueOf(differenceMap.get(entity)),
							MID_CAP_STRING, entity));
					differenceMap.put(MID_CAP_STRING, (differenceMap.get(entity) + min));
					differenceMap.put(entity, 0.00);

				}
			}
			if (differenceMap.get(LARGE_CAP_STRING) != 0.00 && differenceMap.get(LARGE_CAP_STRING) == min) {
				if (differenceMap.get(entity) + min == 0.00) {
					updateCalcuatedData(min, differenceMap, LARGE_CAP_STRING, entity);
				} else if (differenceMap.get(entity) + min > 0.00) {
					updateCalcuatedData(min, differenceMap, LARGE_CAP_STRING, entity);
				} else {
					msgList.add(MessageFormat.format(TRANSFER_MSG, commonUtil.getCurrencyValueOf(differenceMap.get(entity)),
							LARGE_CAP_STRING, entity));
					differenceMap.put(LARGE_CAP_STRING, differenceMap.get(entity) + min);
					differenceMap.put(entity, 0.00);

				}
			}
			
			if (differenceMap.get(BONDS_STRING) != 0.00 && differenceMap.get(BONDS_STRING) == min) {
				if (differenceMap.get(entity) + min == 0.00) {
					updateCalcuatedData(min, differenceMap, BONDS_STRING, entity);
				} else if (differenceMap.get(entity) + min > 0.00) {
					updateCalcuatedData(min, differenceMap, BONDS_STRING, entity);
				} else {
					msgList.add(MessageFormat.format(TRANSFER_MSG, commonUtil.getCurrencyValueOf(differenceMap.get(entity)),
							BONDS_STRING, entity));
					differenceMap.put(BONDS_STRING, differenceMap.get(entity) + min);
					differenceMap.put(entity, 0.00);

				}
			}

		}
	}

	private void updateCalcuatedData(double value, Map<String, Double> differenceMap, String sourceEntity,
			String targetEntity) {
		differenceMap.put(targetEntity, differenceMap.get(targetEntity) + value);
		differenceMap.put(sourceEntity, 0.00);
		msgList.add(MessageFormat.format(TRANSFER_MSG, commonUtil.getCurrencyValueOf(value), sourceEntity, targetEntity));
	}

	
}
