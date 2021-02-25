package com.fin.advisor.dto;

/**
 * @author rajesh.kumar
 * DTO class for Ideal Portfolio
 */
public class IdealPortfolioDTO {
	
	private Integer toleranceLevel;
	
	private Double totalAmount;

	private Double bondsCalVal;
	
	private Double largeCapCalVal;

    private Double midCapCalVal;
	
    private Double foreignCapCalVal;

    private Double smallCapCalVal;

	public Integer getToleranceLevel() {
		return toleranceLevel;
	}

	public void setToleranceLevel(Integer toleranceLevel) {
		this.toleranceLevel = toleranceLevel;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getBondsCalVal() {
		return bondsCalVal;
	}

	public void setBondsCalVal(Double bondsCalVal) {
		this.bondsCalVal = bondsCalVal;
	}

	public Double getLargeCapCalVal() {
		return largeCapCalVal;
	}

	public void setLargeCapCalVal(Double largeCapCalVal) {
		this.largeCapCalVal = largeCapCalVal;
	}

	public Double getMidCapCalVal() {
		return midCapCalVal;
	}

	public void setMidCapCalVal(Double midCapCalVal) {
		this.midCapCalVal = midCapCalVal;
	}

	public Double getForeignCapCalVal() {
		return foreignCapCalVal;
	}

	public void setForeignCapCalVal(Double foreignCapCalVal) {
		this.foreignCapCalVal = foreignCapCalVal;
	}

	public Double getSmallCapCalVal() {
		return smallCapCalVal;
	}

	public void setSmallCapCalVal(Double smallCapCalVal) {
		this.smallCapCalVal = smallCapCalVal;
	}

	
}
