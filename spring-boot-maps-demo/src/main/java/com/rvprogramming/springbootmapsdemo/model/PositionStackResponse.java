package com.rvprogramming.springbootmapsdemo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/* Response:
 * {"data":[
 * 		{"latitude":39.989706,
 *       "longitude":-82.986311,
 *       "type":"localadmin","name":"Columbus","number":null,"postal_code":null,"street":null,"confidence":0.6,"region":"Ohio","region_code":"OH","county":null,"locality":null,"administrative_area":"Columbus","neighbourhood":null,"country":"United States","country_code":"USA","continent":"North America","label":"Columbus, OH, USA",
 *       "map_url":"https:\/\/map.positionstack.com\/export\/embed.html?bbox=-83.21028,39.808631,-82.771378,40.139212\u0026layer=mapnik\u0026marker=39.989706,-82.986311"}]}
 */
 
@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionStackResponse {
	public PositionStackError error;
	public List<PositionStackData> data = new ArrayList<PositionStackData>();
}

