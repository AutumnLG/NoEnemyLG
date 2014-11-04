package com.taehee.autumnlgclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelGem/* extends ModelBase*/ {

	/**
	 * 알코드
	 */
	@JsonProperty("code")
	public String code;

	/**
	 * 컷
	 */
	@JsonProperty("cut")
	public String cut;

	/**
	 * 알
	 */
	@JsonProperty("gemType")
	public String gemType;

	/**
	 * 가로
	 */
	@JsonProperty("width")
	public String width;

	/**
	 * 세로
	 */
	@JsonProperty("height")
	public String height;

	/**
	 * 알값
	 */
	@JsonProperty("gemPrice")
	public String gemPrice;

	/**
	 * 중량
	 */
	@JsonProperty("weight")
	public String weight;

	/**
	 * 구입가
	 */
	@JsonProperty("purchasePrice")
	public String purchasePrice;

	/**
	 * 변경
	 */
	@JsonProperty("changeDate")
	public String changeDate;

	@Override
	public String toString() {
		return "ModelGem [code=" + code + ", cut=" + cut + ", gemType=" + gemType + ", width=" + width + ", height=" + height + ", gemPrice=" + gemPrice + ", weight=" + weight + ", purchasePrice="
				+ purchasePrice + ", changeDate=" + changeDate + "]";
	}

}
