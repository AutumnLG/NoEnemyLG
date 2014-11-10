package com.taehee.autumnlgserver.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelGoodsList {

	@JsonProperty("list")
	public ArrayList<ModelGoods> modelGoods;

	@JsonProperty("status")
	public String status;
}
