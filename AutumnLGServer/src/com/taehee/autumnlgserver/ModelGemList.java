package com.taehee.autumnlgserver;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelGemList/* extends ModelBase*/ {

	@JsonProperty("gemItems")
	ArrayList<ModelGem> gemItems;
}
