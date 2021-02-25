/**
 * 
 */
package com.fin.advisor.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amartya.bhattacharyya
 * DTO class for Custom Portfolio
 */
public class CustomPortfolioDTO{
	private Integer toleranceLevel;

	private Double bondsCustomVal;
	private Double largeCapCustomVal;
    private Double midCapCustomVal;
    private Double foreignCapCustomVal;
    private Double smallCapCustomVal;
    
    private Double bondsDiffVal;
	private Double largeCapDiffVal;
    private Double midCapDiffVal;
    private Double foreignCapDiffVal;
    private Double smallCapDifVal;
    
    private IdealPortfolioDTO idealPortfolioDTO;
    private List<String> recommendedTransferList = new ArrayList<>();

    
	public Integer getToleranceLevel() {
		return toleranceLevel;
	}


	public void setToleranceLevel(Integer toleranceLevel) {
		this.toleranceLevel = toleranceLevel;
	}


	public Double getBondsCustomVal() {
		return bondsCustomVal;
	}


	public void setBondsCustomVal(Double bondsCustomVal) {
		this.bondsCustomVal = bondsCustomVal;
	}


	public Double getLargeCapCustomVal() {
		return largeCapCustomVal;
	}


	public void setLargeCapCustomVal(Double largeCapCustomVal) {
		this.largeCapCustomVal = largeCapCustomVal;
	}


	public Double getMidCapCustomVal() {
		return midCapCustomVal;
	}


	public void setMidCapCustomVal(Double midCapCustomVal) {
		this.midCapCustomVal = midCapCustomVal;
	}


	public Double getForeignCapCustomVal() {
		return foreignCapCustomVal;
	}


	public void setForeignCapCustomVal(Double foreignCapCustomVal) {
		this.foreignCapCustomVal = foreignCapCustomVal;
	}


	public Double getSmallCapCustomVal() {
		return smallCapCustomVal;
	}


	public void setSmallCapCustomVal(Double smallCapCustomVal) {
		this.smallCapCustomVal = smallCapCustomVal;
	}


	public Double getBondsDiffVal() {
		return bondsDiffVal;
	}


	public void setBondsDiffVal(Double bondsDiffVal) {
		this.bondsDiffVal = bondsDiffVal;
	}


	public Double getLargeCapDiffVal() {
		return largeCapDiffVal;
	}


	public void setLargeCapDiffVal(Double largeCapDiffVal) {
		this.largeCapDiffVal = largeCapDiffVal;
	}


	public Double getMidCapDiffVal() {
		return midCapDiffVal;
	}


	public void setMidCapDiffVal(Double midCapDiffVal) {
		this.midCapDiffVal = midCapDiffVal;
	}


	public Double getForeignCapDiffVal() {
		return foreignCapDiffVal;
	}


	public void setForeignCapDiffVal(Double foreignCapDiffVal) {
		this.foreignCapDiffVal = foreignCapDiffVal;
	}


	public Double getSmallCapDifVal() {
		return smallCapDifVal;
	}


	public void setSmallCapDifVal(Double smallCapDifVal) {
		this.smallCapDifVal = smallCapDifVal;
	}

	
	public List<String> getRecommendedTransferList() {
		return recommendedTransferList;
	}

	
	public void setRecommendedTransferList(List<String> recommendedTransferList) {
		this.recommendedTransferList = recommendedTransferList;
	}

	
	public IdealPortfolioDTO getIdealPortfolioDTO() {
		return idealPortfolioDTO;
	}

	
	public void setIdealPortfolioDTO(IdealPortfolioDTO idealPortfolioDTO) {
		this.idealPortfolioDTO = idealPortfolioDTO;
	}


}