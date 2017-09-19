package com.atguigu.sqlsessionutils;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.atguigu.bean.OBJECT_T_MALL_SKU;
import com.atguigu.mapper.CommonMapper;
import com.google.gson.Gson;


public class MySqlSessionFactory {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(MySqlSessionFactory.class);

	private static SqlSession sqlSession;
	private static String resource = "mybatis-config.xml";
	private static SqlSessionFactory sqlSessionFactory;
	
	static {
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		sqlSession = sqlSessionFactory.openSession();
	}
	
/*	public SqlSession getSession() throws IOException{
		if(sqlSession == null){
			//InputStream inputStream = MySqlSessionFactory.class.getClassLoader().getResourceAsStream(resource);
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory= new SqlSessionFactoryBuilder().build(inputStream);
			sqlSession = sqlSessionFactory.openSession();
		return sqlSession;
		}else{
			return sqlSession;
		}
	}*/
	

	public static void main(String[] args) throws IOException {
		
		CommonMapper mapper = MySqlSessionFactory.sqlSession.getMapper(CommonMapper.class);
		
		List<OBJECT_T_MALL_SKU> select_sku_list = mapper.select_sku_list();
		
		//Iterator<OBJECT_T_MALL_SKU> iterator = select_sku_list.iterator();
		
		//地址一直到参数所使用的集合
		HttpSolrServer http_solr_server = new HttpSolrServer("http://localhost:8983/solr/t_mall_solr_service_sku");
		
		//设置solr给的一个解析器，（我在查找这个东西的时候他用什么东西读出来）
		http_solr_server.setParser(new XMLResponseParser());
		
		//一次关键词检索不到，再检索多少次
		http_solr_server.setMaxRetries(3);
		
		//三秒内，检索三次，如果三次都检索不到，直接返回空
		http_solr_server.setConnectionTimeout(6000);
		
		//向solr中导数据
		try {
			http_solr_server.addBeans(select_sku_list);
			
			http_solr_server.commit();
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}
	
/*	@Test
	public void test() {
		CommonMapper mapper = MySqlSessionFactory.sqlSession.getMapper(CommonMapper.class);
		List<OBJECT_T_MALL_SKU> select_sku_list = mapper.select_sku_list();
		Iterator<OBJECT_T_MALL_SKU> iterator = select_sku_list.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		
		
	}*/
}
