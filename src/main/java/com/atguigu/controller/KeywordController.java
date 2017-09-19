package com.atguigu.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.bean.OBJECT_T_MALL_SKU;
import com.atguigu.mapper.CommonMapper;
import com.atguigu.sqlsessionutils.MySolrServerutil;
import com.atguigu.sqlsessionutils.MySqlSessionUtil;

@Controller
public class KeywordController {

	@RequestMapping("solrservice/{keyworld}")
	@ResponseBody
	public List<OBJECT_T_MALL_SKU> get_keyword_searchList_by_sku_mch(@PathVariable String keyworld){
		
		List<OBJECT_T_MALL_SKU> obj_t_mall_sku_list = new ArrayList<OBJECT_T_MALL_SKU>();
		
		//通过SqlSession获取到反射创建具体的代理
		CommonMapper mapper = MySqlSessionUtil.getSqlSession(CommonMapper.class);
		
		obj_t_mall_sku_list = mapper.select_sku_list();
		
		HttpSolrServer httpSolrServer = MySolrServerutil.get_httpSolrServer("t_mall_solr_service_sku");
		
		//创建Solr的查询对象solrQuery————new 出查询对象，用以放置solr的查询语句
		SolrQuery sq = new SolrQuery();
		
		//为solr的查询对象设置查询语句语法为————设置查询语句（字段名称：用户传入的关键词）
		sq.setQuery("sku_mch:"+keyworld);
		
		//查询响应（通过用户关键词，返回查询到的结果）：（需要抓抛异常）
		QueryResponse qr;
		
		try {
			
			//调用HttpSolrServer的查询方法，获取查询响应对象，传入Solr的查询对象solrQuery
			qr = httpSolrServer.query(sq);
			
			//调用查询对象的getBeans注意是Beans,sssss(不管结果是一个对象还是多个对象，都将返回一个集合)
			obj_t_mall_sku_list = qr.getBeans(OBJECT_T_MALL_SKU.class);
			
		} catch (SolrServerException e) {
			
			e.printStackTrace();
		}
		
		//添加了@ResponseBody注解，将返回的数据自动转换成json字符串
		return obj_t_mall_sku_list;
	}
	
}
