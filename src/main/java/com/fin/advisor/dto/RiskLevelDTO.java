/**
 * 
 */
package com.fin.advisor.dto;

/**
 * @author rajesh.kumar
 * DTO class for Risk Level DTO
 */
public class RiskLevelDTO {
	
	private Long toleranceLevel;

	private Long bondsShare;
	
	private Long largeCapShare;

    private Long midCapShare;
	
    private Long foreignCapShare;

    private Long smallCapShare;

	public Long getToleranceLevel() {
		return toleranceLevel;
	}

	public void setToleranceLevel(Long toleranceLevel) {
		this.toleranceLevel = toleranceLevel;
	}

	public Long getBondsShare() {
		return bondsShare;
	}

	public void setBondsShare(Long bondsShare) {
		this.bondsShare = bondsShare;
	}

	public Long getLargeCapShare() {
		return largeCapShare;
	}

	public void setLargeCapShare(Long largeCapShare) {
		this.largeCapShare = largeCapShare;
	}

	public Long getMidCapShare() {
		return midCapShare;
	}

	public void setMidCapShare(Long midCapShare) {
		this.midCapShare = midCapShare;
	}

	public Long getForeignCapShare() {
		return foreignCapShare;
	}

	public void setForeignCapShare(Long foreignCapShare) {
		this.foreignCapShare = foreignCapShare;
	}

	public Long getSmallCapShare() {
		return smallCapShare;
	}

	public void setSmallCapShare(Long smallCapShare) {
		this.smallCapShare = smallCapShare;
	}


}
