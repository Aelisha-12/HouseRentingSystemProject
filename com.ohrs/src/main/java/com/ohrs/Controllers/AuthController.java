package com.ohrs.Controllers;

import java.lang.ProcessBuilder.Redirect;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ohrs.models.ERole;
import com.ohrs.models.Role;
import com.ohrs.models.User;
import com.ohrs.payload.request.LoginRequest;
import com.ohrs.payload.request.SignupRequest;
import com.ohrs.payload.response.MessageResponse;
import com.ohrs.payload.response.UserInfoResponse;
import com.ohrs.repositories.RoleRepository;
import com.ohrs.repositories.UserRepository;
import com.ohrs.security.jwt.JwtUtils;
import com.ohrs.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;

	// after login 
	@PostMapping("/signin")
	public ModelAndView authenticateUser(@Valid @ModelAttribute LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
//		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//				// .body(new UserInfoResponse(useDetails., null, null, null, roles))
//				.body(new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(),
//						userDetails.getContactno(), roles));
		
		
		if(roles.contains("ADMIN")) {
			ModelAndView adminview = new ModelAndView("redirect:/AdminHome");
		//adminview.setViewName("AdminHome.html");
		return adminview;
		}
		
		else if(roles.contains("CUSTOMER")) {
			ModelAndView custview = new ModelAndView("redirect:/CustomerHome");
			//mv.setViewName("CustomerHome.html");
			return custview;	
		}
		else if(roles.contains("OWNER")) {
			ModelAndView ownview = new ModelAndView("redirect:/OwnerHome");
			//mv.setViewName("OwnerHome.html");
			return ownview;
		}
		else {
			//mv.setViewName("login.html");
			//mv.setView(Redirect.to(login));
			ModelAndView mv = new ModelAndView("redirect:/login.html");
					return mv;
		}
		
	}

	//after registration
	@PostMapping("/signup")
	public ModelAndView registerUser(@Valid @ModelAttribute SignupRequest signUpRequest) {
		System.out.println("signup");
		System.out.println(signUpRequest.getContactno());
		ModelAndView mv = new ModelAndView();
		/*
		 * if(result.hasErrors()) { System.out.println(result); }
		 */

	/*	if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			//return "redirect:/registration?failusername";

			mv.setViewName("registration.html?failusername");
			return mv;
			
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			
			mv.setViewName("registration.html?failemail");
			return mv;
			//return "redirect:/registration?failemail";
		}
		*/
		/*
		 * if (userRepository.existsByUsername(signUpRequest.getUsername())) { return
		 * ResponseEntity.badRequest().body(new
		 * MessageResponse("Error: Username is already taken!")); } if
		 * (userRepository.existsByEmail(signUpRequest.getEmail())) { return
		 * ResponseEntity.badRequest().body(new
		 * MessageResponse("Error: Email is already in use!")); }
		 */
		// Create new user's account

		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getContactno(),
				encoder.encode(signUpRequest.getPassword()));

		// User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
		// signUpRequest.getContactno(), encoder.encode(signUpRequest.getPassword()));
		/*
		 * User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
		 * signUpRequest.getContactno(),encoder.encode(signUpRequest.getPassword()));
		 */
		Set<String> strRoles = signUpRequest.getRole();
		System.out.println(strRoles);
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.CUSTOMER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "owner":
					Role modRole = roleRepository.findByName(ERole.OWNER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);
					break;
				default:
					Role userRole = roleRepository.findByName(ERole.CUSTOMER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		
		user.setRoles(roles);
		userRepository.save(user);
		return new ModelAndView("welcome");


//		return "redirect:/api/test/registration";
//		// return ResponseEntity.ok(new MessageResponse("User registered
//		// successfully!"));
		
		
		
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new MessageResponse("You've been signed out!"));
	}
}
