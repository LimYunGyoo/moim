package ac.moim.user.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ac.moim.common.dto.CityDto;
import ac.moim.common.dto.StateDto;
import ac.moim.common.dto.SubjectDto;
import ac.moim.common.entity.City;
import ac.moim.common.entity.State;
import ac.moim.common.service.CityService;
import ac.moim.common.service.StateService;
import ac.moim.common.service.SubjectService;
import ac.moim.study.entity.Study;
import ac.moim.study.entity.StudyMember;
import ac.moim.study.service.StudyMemberService;
import ac.moim.user.entity.User;
import ac.moim.user.repository.UserRepository;
import ac.moim.user.service.UserService;

/**
 * Created by SONG_HOHOON on 2016-12-29.
 * Updated by LEE_DOYOUNG on 2017-01-07.
 */
@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userService;	
	@Autowired
	private CityService cityService;
	@Autowired 
	private StateService stateService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private StudyMemberService studyMemberService;
	
	
	@RequestMapping(value = "{userId}", method = RequestMethod.GET)
	
	public String getUser(@PathVariable(value = "userId")String userId) {

		User user = userService.getUser(userId);
		return "views/mypage/myPage";
	}	

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchMyInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
			
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		
		User user = userService.getUser(userId);
		List<CityDto.Response> cityList = cityService.getAllCity();
		List<StateDto.Response> stateList = stateService.getAllState();
		List<SubjectDto.Response> subjectList = subjectService.getAllSubject();
		//StateDto.Response userState = stateService.getState(user.getCityId().getStateId())
		
				
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String userBirthday ;
		
		if (user.getBirthday() != null){
		System.out.println(user.getBirthday());
			userBirthday= sdf.format(user.getBirthday());
			model.addAttribute("userBirthdate",userBirthday);
		}
		if(user.getCity() != null){
		City userCity= new City();
		userCity = user.getCity();
		model.addAttribute("userCity",userCity);
			
			if(user.getCity().getStateId()!=null){
			State userState = new State();
			 userState = user.getCity().getStateId();
			model.addAttribute("userState",userState);
			}
		}
				

		model.addAttribute("cityList",cityList);
		model.addAttribute("stateList",stateList);
		model.addAttribute("subjectList",subjectList);
		model.addAttribute("user",user);
		
		
	
		return "views/mypage/update";
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateMyInfo(HttpSession session,HttpServletResponse response, User user) throws IOException {
		String userId = (String)session.getAttribute("userId");
		user.setId(userId);
		userService.UserCreateOrUpdate(user);
		
		response.sendRedirect("/user/search");
		return "views/mypage/update";
	}
	
	@RequestMapping(value="/made_study", method = RequestMethod.GET)
	public String searchMadeStudy(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, Model model, HttpSession session,HttpServletResponse response, User user) {

		String userName = (String)session.getAttribute("userName");
		String userId = (String)session.getAttribute("userId");
		user.setId(userId);
		user.setName(userName);
		if(userName == null){
			model.addAttribute("userName", "로그인 후 사용하세요");
		}
		else{
			model.addAttribute("userName", userName);	
		}
		
		List<Study> myStudyList = null;
		
		myStudyList = studyMemberService.findByUserIdAndClassifier(user.getId(), "leader");
		
		model.addAttribute("myStudyList", myStudyList);
		
		
		return "views/mypage/myPage";
	}
	
	@RequestMapping(value="/joined_study", method = RequestMethod.GET)
	public String searchJoinedStudy(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, Model model, HttpSession session,HttpServletResponse response, User user) {

		String userName = (String)session.getAttribute("userName");
		String userId = (String)session.getAttribute("userId");
		user.setId(userId);
		user.setName(userName);
		if(userName == null){
			model.addAttribute("userName", "로그인 후 사용하세요");
		}
		else{
			model.addAttribute("userName", userName);	
		}
		
		List<Study> myStudyList = null;
		
		myStudyList = studyMemberService.findByUserIdAndClassifier(user.getId(), "teamone");
		
		model.addAttribute("myStudyList", myStudyList);
		
		
		return "views/mypage/myPage";
	}
}
