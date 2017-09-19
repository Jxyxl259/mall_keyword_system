package com.atguigu.sqlsessionutils;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;

public class MySolrServerutil {

	public static HttpSolrServer get_httpSolrServer(String collection) {
		//地址一直到参数所使用的集合
		HttpSolrServer http_solr_server = new HttpSolrServer("http://localhost:8983/solr/"+collection);
		
		//设置solr给的一个解析器，（我在查找这个东西的时候他用什么东西读出来）
		http_solr_server.setParser(new XMLResponseParser());
		
		//一次关键词检索不到，再检索多少次
		http_solr_server.setMaxRetries(3);
		
		//三秒内，检索三次，如果三次都检索不到，直接返回空
		http_solr_server.setConnectionTimeout(6000);
		
		return http_solr_server;
	}
	
}
