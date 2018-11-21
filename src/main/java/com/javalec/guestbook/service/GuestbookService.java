package com.javalec.guestbook.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalec.guestbook.dao.GuestBookDAO;
import com.javalec.guestbook.vo.GuestBookVO;

@Service("guestbookService")	// 2. ���� ��ü�� �޸𸮿� �������
public class GuestbookService implements IGuestBookService {
	
	@Autowired
	private GuestBookDAO dao;
	
	@Override
	public void insertGuestBook(GuestBookVO vo) {
		vo.setNo((new Integer(196).intValue()));
		try {
			dao.insert(vo);
		}catch(Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteGuestBook(GuestBookVO vo) {
		dao.delete(vo);
	}

	@Override
	public List<GuestBookVO> getGuestBookList() {
		return dao.selectList();
	}

	@Override
	public void updateGuestBook(GuestBookVO vo) {
		dao.update(vo);
	}
	
	@Override
	public GuestBookVO getGuestBookOne(GuestBookVO vo) {
		return dao.getGuestBookOne(vo);
	}

	@Override
	public List<GuestBookVO> getKeywordList(Map<String, Object> map) {
		return dao.getKeywordList(map);
	}

	@Override
	public List<GuestBookVO> getSearchGuestBook(String keyword, String option) {
		//return dao.searchName(keyword, option);
		return null;
	}
	
	@Override
	public boolean check_pw(GuestBookVO vo) {
		String input_pw = vo.getPw();
		GuestBookVO checkVO = new GuestBookVO();
		checkVO.setNo(vo.getNo());
		System.out.println("[check_pw �Լ�] checkVO.getNO: " + checkVO.getNo());
		GuestBookVO resultFind = dao.getGuestBookOne(checkVO);
		
		//GuestBookVO resultFind = mybatis.selectOne("GuestBookDao.getBookOneList", checkVO);
		System.out.println("[check_pw �Լ�] resultFind : " + resultFind.toString());
		
		String pw = resultFind.getPw();
		System.out.println("[check_pw �Լ�] resultFind : " + resultFind);
		System.out.println("[check_pw �Լ�] db pw: " + pw);
		
		int d_no = vo.getNo();
		System.out.println("[check_pw �Լ�] d_no : " + d_no);
		
		System.out.println("[dao.check_pw] ��ȣ: " + d_no + " / �Էº�й�ȣ: " + input_pw + " / ���˺�й�ȣ" + pw);
		
		if(!input_pw.equals(pw)) {
			return false;
		}
		
		return true;
	}
	
	
	
	


}
