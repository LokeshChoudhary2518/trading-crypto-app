package com.trading.crypto.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.crypto.models.TwoFactorOTP;
import com.trading.crypto.models.User;
import com.trading.crypto.response.TwoFactorOtpRepository;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService {

	@Autowired
	private TwoFactorOtpRepository twoFactorOtpRepository;

	@Override
	public TwoFactorOTP createTwoFactorOtp(User user, String opt, String jwt) {

		UUID uuid = UUID.randomUUID();

		String id = uuid.toString();

		TwoFactorOTP twoFactorOTP = new TwoFactorOTP();

		twoFactorOTP.setOtp(opt);
		twoFactorOTP.setJwt(jwt);
		twoFactorOTP.setId(id);
		twoFactorOTP.setUser(user);

		twoFactorOtpRepository.save(twoFactorOTP);

		return twoFactorOtpRepository.save(twoFactorOTP);
	}

	@Override
	public TwoFactorOTP findByUser(Long userId) {

		return twoFactorOtpRepository.findByUserId(userId);
	}

	@Override
	public TwoFactorOTP findById(String id) {

		Optional<TwoFactorOTP> opt = twoFactorOtpRepository.findById(id);

		return opt.orElse(null);
	}

	@Override
	public boolean verifyTwoFcatorOtp(TwoFactorOTP twoFactorOtp, String otp) {

		return twoFactorOtp.getOtp().equals(otp);
	}

	@Override
	public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp) {

		twoFactorOtpRepository.delete(twoFactorOtp);

	}

}
