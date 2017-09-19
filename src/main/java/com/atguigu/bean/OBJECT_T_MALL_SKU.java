package com.atguigu.bean;

import org.apache.solr.client.solrj.beans.Field;

public class OBJECT_T_MALL_SKU extends T_Mall_Sku{

	@Field
	private String shp_tp;

	public String getShp_tp() {
		return shp_tp;
	}

	public void setShp_tp(String shp_tp) {
		this.shp_tp = shp_tp;
	}

	@Override
	public String toString() {
		return "OBJECT_T_MALL_SKU [shp_tp=" + shp_tp + "]"+super.toString();
	}
	
	
	
}
