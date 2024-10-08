package com.trading.crypto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trading.crypto.config.JwtProvider;
import com.trading.crypto.models.TwoFactorOTP;
import com.trading.crypto.models.User;
import com.trading.crypto.repositories.UserRepo;
import com.trading.crypto.response.AuthResponse;
import com.trading.crypto.response.TwoFactorOtpRepository;
import com.trading.crypto.services.CustomeUserDetailsService;
import com.trading.crypto.services.TwoFactorOtpService;
import com.trading.crypto.utils.OtpUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CustomeUserDetailsService customeUserDetailsService;

	@Autowired
	private TwoFactorOtpService twoFactorOtpService;

	@PostMapping("/singup")
	public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

		User isEmailExist = userRepo.findByEmail(user.getEmail());

		if (isEmailExist != null) {
			throw new Exception("email is already used with another account");
		}

		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		newUser.setMobile(user.getMobile());
		newUser.setName(user.getName());

		User savedUser = userRepo.save(newUser);

		Authentication auth = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());

		SecurityContextHolder.getContext().setAuthentication(auth);

		String jwt = JwtProvider.generateToken(auth);

		AuthResponse res = new AuthResponse();
		res.setJwt(jwt);
		res.setStatus(true);
		res.setMessage("Register Success");

		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	@PostMapping("/singin")
	public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

		String userName = user.getEmail();

		String password = user.getPassword();

		Authentication auth = authenticate(userName, password);

		SecurityContextHolder.getContext().setAuthentication(auth);

		String jwt = JwtProvider.generateToken(auth);

		User authUser = userRepository.findByEmail(userName);

		if (user.getTwoFactorAuth().isEnabled()) {
			AuthResponse res = new AuthResponse();

			res.setMessage("Two Factor auth is enabled");
			res.setTwoFactorAuthEnabled(true);

			String otp = OtpUtils.generateOtp();

			TwoFactorOTP oldTwoFactorOTP = twoFactorOtpService.findByUser(authUser.getId());
			if (oldTwoFactorOTP != null) {
				twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);

			}

			TwoFactorOTP newTwoFacctorOTP = twoFactorOtpService.createTwoFactorOtp(authUser, otp, jwt);

			res.setSession(newTwoFacctorOTP.getId());

			return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

		}

		AuthResponse res = new AuthResponse();
		res.setJwt(jwt);
		res.setStatus(true);
		res.setMessage("login Success");

		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	private Authentication authenticate(String userName, String password) {

		UserDetails userDetails = customeUserDetailsService.loadUserByUsername(userName);

		if (userDetails == null) {
			throw new BadCredentialsException("invalid username");

		}

		if (!password.equals(userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password");
		}

		return new UsernamePasswordAuthenticationToken(userDetails, password);
	}

}
