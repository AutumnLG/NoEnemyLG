package com.taehee.autumnlgclient.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelGoodsList {

	@JsonProperty("list")
	public ArrayList<ModelGoods> modelGoods;

	@JsonProperty("status")
	public String status;
}
