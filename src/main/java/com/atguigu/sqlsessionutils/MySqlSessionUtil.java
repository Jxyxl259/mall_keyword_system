package com.atguigu.sqlsessionutils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.atguigu.mapper.CommonMapper;

public class MySqlSessionUtil {

	private SqlSession sqlSession;
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
		
	}
	
	public static<T> T getSqlSession(Class<T> clazz) {
		
		SqlSession openSession = sqlSessionFactory.openSession();
		
		T mapper = (T)openSession.getMapper(clazz);
		
		return mapper;
	}
}
