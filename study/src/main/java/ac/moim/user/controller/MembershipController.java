package ac.moim.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/membership")
public class MembershipController {
	
//	@Autowired
//	private UserRepository userRepository;
	
	@RequestMapping(value = "/enroll", method = RequestMethod.GET)
	public String Enroll() {
////		return membershipService.Enroll("test");
//		
////		Member newMember = new Member();
//		UserDto newUser = new UserDto();
//		
//		newUser.setUSER_ID("01");
//		newUser.setUSER_NAME("tester");
//		newUser.setUSER_GENDER('M');
//		
//		userRepository.save(newUser);
//		
//		
////		membershipRepository.save(newMember);
		return "success";
	}

}