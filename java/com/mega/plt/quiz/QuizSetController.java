package com.mega.plt.quiz;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.mega.plt.pole.PoleVO;

@Controller
public class QuizSetController implements QuizSetInterface {
	static ArrayList<QuizDetailVO> list = null;//모든 문제 값을 담는 세션의 list
	static ArrayList<PoleVO> poleID = null;//모든 전신주 값을 담는 세션의 list 
	
	@Autowired
	QuizSetService service;
	@Autowired
	QuizDetailService detailService;
//두 전신주 사이의 직선 거리 계산 담당 컨트롤러
@RequestMapping("quiz/distance.do")
public String distance(Model model, @RequestParam("pID") String pID, @RequestParam("ordr") String ordr) {
	System.out.println("poleNum 들어옴");
	ArrayList<PoleVO> poleList = poleID;
	double poleDistance = 0;//거리 계산을 담기 위한 초기화 작업
		if (ordr.equals("2")) {//첫번째 전신주와 두번째 전시주 사이 거리 if문
			System.out.println("------------poleList 1번째 값 들어옴"+poleList);
			poleList.addAll((ArrayList<PoleVO>) service.poleList(pID));
			System.out.println("----------poleList 2번째 값 들어옴"+poleList);
			poleDistance = detailService.distance(Double.parseDouble(poleList.get(0).getLatitude()), 
					Double.parseDouble(poleList.get(0).getLongitude()),
					Double.parseDouble(poleList.get(1).getLatitude()), 
					Double.parseDouble(poleList.get(1).getLongitude()), 
					"meter");
			System.out.println("------------첫번째 poleDistance값:"+poleDistance);
		}else if(ordr.equals("3")) {//poleDistance가 null이 아니라면 두번째 전신주와 첫번째 전신주 사이 거리 계산  
			System.out.println("------------poleList 3번째 값 들어옴"+poleList);
			poleList.addAll((ArrayList<PoleVO>) service.poleList(pID));
			System.out.println("------------poleList 4번쨰 값 들어옴"+poleList);
			poleDistance = detailService.distance(Double.parseDouble(poleList.get(1).getLatitude()), 
					Double.parseDouble(poleList.get(1).getLongitude()),
					Double.parseDouble(poleList.get(2).getLatitude()), 
					Double.parseDouble(poleList.get(2).getLongitude()), 
					"meter");
			System.out.println("-----------두번째 poleDistance값:"+poleDistance);
			}
model.addAttribute("poleDistance", poleDistance); // ajax로 넘기는 값
return "quiz/poleDistance";
}
// 1번째 문제 만들기 담당 컨트롤
@Override
@RequestMapping("quiz/quizSetHint1.do")
public String quizSetHint1(QuizSetVO quizSetVO, QuizDetailVO quizDetailVO, HttpSession session,	HttpServletRequest request) {
	String ordr = request.getParameter("ordr");
	QuizSetVO setVo = quizSetVO;
	QuizDetailVO detailVo = quizDetailVO;
	if (request.getParameter("ordr") != null) {
		detailVo.setOrdr(Integer.parseInt(ordr));
		// session이 null인 경우를 체크하기 위해서
		if (session.getAttribute("QuizSetTitle") != null) {
			list.add(detailVo);
			System.out.println("첫번째 문제 1번째 if문 list 다음 값 확인: "+list);
			} else {
				// null이라는 의미는 세션에 안만들어졌다는 것
				list = new ArrayList<>();
				list.add(detailVo);
				System.out.println("첫번째 문제 2번째 if문 list 다음 값 확인: "+list);
			}
			session.setAttribute("QuizSetTitle", setVo.getTitle());
			session.setAttribute("QuizDetailHintAll", list);
			System.out.println("-------------PoleAll의 poleID 1번째 문제 전!! 값: "+poleID);
			poleID = (ArrayList<PoleVO>) service.poleList(detailVo.getpID());
			session.setAttribute("PoleAll", poleID);
		} else {// title null이라면 list를 ArrayList<QuizSetVO>하여서 만들어 줘라.
			list = (ArrayList<QuizDetailVO>) session.getAttribute("QuizDetailHintAll");
		}
	System.out.println("-------------PoleAll의 poleID 1번째 문제 후 값: "+poleID);
	return "quiz/quizSetHint2";
	}
// 2번째 문제 만들기 담당 컨트롤
@Override
@RequestMapping("quiz/quizSetHint2.do")
	public String quizSetHint2(QuizDetailVO quizDetailVO, HttpSession session, HttpServletRequest request) {
		String ordr = request.getParameter("ordr");
		QuizDetailVO detailVo = quizDetailVO;
		if (request.getParameter("ordr") != null) {
			detailVo.setOrdr(Integer.parseInt(ordr));
			// session이 null인 경우를 체크하기 위해서
			if (session.getAttribute("QuizSetTitle") != null) {
				list.add(detailVo);
				System.out.println("두번째 문제 1번째 if문 list 다음 값 확인: "+list);
			} else {
				list = new ArrayList<>();
				list.add(detailVo);
				System.out.println("두번째 문제 2번째 if문 list 다음 값 확인: "+list);
			}
			System.out.println("-------------PoleAll의 poleID 2번째 문제 전!! 값: "+poleID);
		} else {// hint1 null이라면 list를 ArrayList<BasketVO>하여서 만들어 줘라.
			list = (ArrayList<QuizDetailVO>) session.getAttribute("QuizDetailHintAll");
		}
		System.out.println("-------------PoleAll의 poleID 2번째 문제 후 값: "+poleID);
	return "quiz/quizSetHint3";
	}
// 3번째 문제 만들기 담당 컨트롤
@Override
@RequestMapping("quiz/quizSetHint3.do")
public String quizSetHint3(QuizDetailVO quizDetailVO, HttpSession session, HttpServletRequest request) {
	String ordr = request.getParameter("ordr");
	QuizDetailVO detailVo = quizDetailVO;
	if (request.getParameter("ordr") != null) {
		detailVo.setOrdr(Integer.parseInt(ordr));
		// session이 null인 경우를 체크하기 위해서
			if (session.getAttribute("QuizSetTitle") != null) {
				list.add(detailVo);
				System.out.println("세번째 문제 1번째 if문 list 다음 값 확인: "+list);
			} else {
				list = new ArrayList<>();
				list.add(detailVo);
				System.out.println("세번째 문제 2번째 if문 list 다음 값 확인: "+list);
			}
			System.out.println("-------------PoleAll의 poleID 3번째 문제 전!! 값: "+poleID);
		} else {// hint1 null이라면 list를 ArrayList<BasketVO>하여서 만들어 줘라.
			list = (ArrayList<QuizDetailVO>) session.getAttribute("QuizDetailHintAll");
		}
	System.out.println("-------------PoleAll의 poleID 3번째 문제 후 값: "+poleID);
	return "quiz/quizSetResult";
	}
// 마지막 DB 등록 전 문제 화면 담당 컨트롤
@Override
@RequestMapping("quiz/quizSetResult.do")
public String quizSetResult(QuizSetVO quizSetVO, HttpSession session, HttpServletRequest request) {
	QuizSetVO setVo = quizSetVO;
//	session.setAttribute("uID", "123");
	// 로그인 세션 setVO에 넣기
	setVo.setuID((String) session.getAttribute("uID"));
	// 제목 값 가져오기
	service.quizSetInsert(setVo);
	System.out.println("uID 세션의 setVo 값 확인 -----------------"+setVo.getuID());
	//quizSetResult 페이지에서 첫번째 퀴즈 값 qdvo에 넣기
	QuizDetailVO qdvo = new QuizDetailVO();
	qdvo.setHint1(request.getParameter("hint11"));
	qdvo.setHint2(request.getParameter("hint12"));
	qdvo.setHint3(request.getParameter("hint13"));
	qdvo.setpID(poleID.get(0).getpID());
	try {//스트링으로 가져온값 int로 바꾸어 넣기
		qdvo.setOrdr(Integer.parseInt(request.getParameter("ordr1")));
		} catch (NumberFormatException e) {
		}
	//quizSetResult 페이지에서 두번째 퀴즈 값 qdvo2에 넣기
	QuizDetailVO qdvo2 = new QuizDetailVO();
	qdvo2.setHint1(request.getParameter("hint21"));
	qdvo2.setHint2(request.getParameter("hint22"));
	qdvo2.setHint3(request.getParameter("hint23"));
	qdvo2.setpID(poleID.get(1).getpID());
	try {//스트링으로 가져온값 int로 바꾸어 넣기
		qdvo2.setOrdr(Integer.parseInt(request.getParameter("ordr2")));
		} catch (NumberFormatException e) {
		}
	//quizSetResult 페이지에서 세번째 퀴즈 값 qdvo3에 넣기
	QuizDetailVO qdvo3 = new QuizDetailVO();
		qdvo3.setHint1(request.getParameter("hint31"));
		qdvo3.setHint2(request.getParameter("hint32"));
		qdvo3.setHint3(request.getParameter("hint33"));
		qdvo3.setpID(poleID.get(2).getpID());
		try {//스트링으로 가져온값 int로 바꾸어 넣기
			qdvo3.setOrdr(Integer.parseInt(request.getParameter("ordr3")));
		} catch (NumberFormatException e) {
		}
	detailService.quizDetailInsert(qdvo);
	detailService.quizDetailInsert(qdvo2);
	detailService.quizDetailInsert(qdvo3);
		
	System.out.println("문제가 DB에 잘 등록 되었습니다.");
	return "quiz/quizSetResultOk";
	}
}