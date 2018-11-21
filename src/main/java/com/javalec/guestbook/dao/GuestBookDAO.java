package com.javalec.guestbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cache.TransactionalCacheManager;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.javalec.guestbook.util.SqlSessionFactoryBean;
import com.javalec.guestbook.vo.GuestBookVO;

@Repository
public class GuestBookDAO {

	@Autowired
	private SqlSession mybatis;
	private DataSourceTransactionManager transactionManager;

	public void insert(GuestBookVO vo) {
		System.out.println("-----mybatis inserting");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try {
			mybatis.insert("GuestBookDao.insert", vo); // 회원 입력
			mybatis.insert("GuestBookDao.insert", vo); // 게시판 입력 -- 작성하기 나름
		} catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
		}
		transactionManager.commit(status);
	}

	public void delete(GuestBookVO vo) {
		System.out.println("[dao.delete] " + vo.getNo());
		System.out.println("[dao.delete] " + vo.getPw());

		mybatis.delete("GuestBookDao.delete", vo);
	}

	public List<GuestBookVO> selectList() {
		return mybatis.selectList("GuestBookDao.getList");
	}

	public void update(GuestBookVO vo) {
		mybatis.update("GuestBookDao.update", vo);
	}

	public List<GuestBookVO> getKeywordList(Map<String, Object> map) {
		return mybatis.selectList("GuestBookDao.getKeywordList", map);
	}

	public GuestBookVO getGuestBookOne(GuestBookVO vo) {
		return mybatis.selectOne("GuestBookDao.getBookOneList", vo);
	}

}
