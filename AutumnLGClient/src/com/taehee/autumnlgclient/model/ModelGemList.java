package com.taehee.autumnlgclient.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelGemList {
	
	@JsonProperty("gemItems")
	public ArrayList<ModelGem> gemItems;
	
}
