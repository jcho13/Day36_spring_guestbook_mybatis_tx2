package com.javalec.guestbook.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javalec.guestbook.dao.GuestBookDAO;
import com.javalec.guestbook.service.IGuestBookService;
import com.javalec.guestbook.vo.GuestBookVO;

@Controller
public class GuestbookController {
	@Autowired
	private IGuestBookService guestbookService;
	
	@RequestMapping("/getguestbooklist.do")
	public String getGuestBookList(GuestBookDAO dao, Model model) {
		System.out.println("����Ʈ ��ȸ ��Ʈ�ѷ�");
		//model.addAttribute("list", dao.selectList());
		model.addAttribute("list", guestbookService.getGuestBookList());

		return "index.jsp";
	}
	
	@RequestMapping("/addform.do")
	public String InsertformGuestBook() {
		System.out.println("insert form ��Ʈ�ѷ�");
		
		return "addform.jsp";
	}
	
	@RequestMapping("/add.do")
	public String InsertGuestBook(GuestBookVO vo, GuestBookDAO dao, Model model) throws Exception {
		System.out.println("insert ��Ʈ�ѷ�");
		//dao.insert(vo);
		guestbookService.insertGuestBook(vo);
		
		return "getguestbooklist.do";
	}
	
	@RequestMapping("/deleteform.do")
	public String DeleteformGuestBook() {	
		System.out.println("delete form ��Ʈ�ѷ�");
		
		return "deleteform.jsp";
	}
	
	@RequestMapping("/delete.do")
	public String DeleteGuestBook(GuestBookVO vo, GuestBookDAO dao, Model model) { //�˾Ƽ� ��ü ��������. command ��ü. 
		// ��й�ȣ ��ġ Ȯ��
		System.out.println("[delete.do] no : " + vo.getNo());
		System.out.println("[delete.do] pw : " + vo.getPw());
		
		boolean pwCheck = false;
		pwCheck = guestbookService.check_pw(vo);
		//pwCheck = dao.check_pw(vo);
		
		System.out.println("[delete.do] pwCheck : " + pwCheck);
		
		if(pwCheck == true) {
			guestbookService.deleteGuestBook(vo);
			
			return "getguestbooklist.do";
		}
		
		model.addAttribute("msg", "��й�ȣ�� ��ġ���� ����");
		return "deleteform.do";
	}
	
	@RequestMapping("/updateform.do")
	public String UpdateformGusetBook() {
		System.out.println("update form ��Ʈ�ѷ�");
		
		return "updateform.jsp";
	}
	
	@RequestMapping("/update.do")
	public String UpdateGusetBook(GuestBookVO vo, GuestBookDAO dao, Model model) {
		System.out.println("update ��Ʈ�ѷ�");
		System.out.println("[update.do] pw : " + vo.getPw());
		System.out.println("[update.do] content : " + vo.getContent());
		
		// ��й�ȣ ��ġ Ȯ��
		boolean pwCheck = false;
		pwCheck = guestbookService.check_pw(vo);
		//pwCheck = dao.check_pw(vo);
				
		if(pwCheck == true) {
			guestbookService.updateGuestBook(vo);
					
			return "getguestbooklist.do";
		}
		
		model.addAttribute("msg", "��й�ȣ�� ��ġ���� ����");
		return "updateform.do";
	}
	
	@RequestMapping("/showcontent.do")
	public String ViewOneGuestBook(GuestBookVO vo, Model model, GuestBookDAO dao) {
		System.out.println("�ܵ� ����Ʈ ��Ʈ�ѷ�");
		model.addAttribute("resultList", guestbookService.getGuestBookOne(vo));
		
		return "showcontent.jsp";
	}
	
	@RequestMapping("/searchkeywordlist.do")
	public String SearchGuestBook(GuestBookDAO dao, Model model, 
			@RequestParam(value="searchKeyword", defaultValue="", required=false) String keyword, 
			@RequestParam(value="search_option", defaultValue="", required=false) String search_option) {
		System.out.println("Ű���� �˻� ��� ��Ʈ�ѷ�");
		System.out.println("option : " + search_option);
		System.out.println("keyword : " + keyword);
		
		
		Map<String, Object> map = new HashMap<String, Object>();	//map�� object�� �ָ� �갡 ���������� �˾Ƽ� ���� �°� ã����
		map.put("keyword", "%"+keyword+"%");
		
		
		// ���� �˻���
		model.addAttribute("list", guestbookService.getKeywordList(map));
		model.addAttribute("searchKeyword", keyword);
		
//		// select �˻���
//		model.addAttribute("searchKeyword", keyword);
//		model.addAttribute("searchOption", search_option);
//		//model.addAttribute("list", guestbookService.getSearchGuestBook(keyword, search_option));
//		model.addAttribute("list", guestbookService.getSearchGuestBook(keyword, search_option));
		
		return "index.jsp";
		
	}

}
