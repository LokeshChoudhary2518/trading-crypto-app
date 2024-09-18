package com.trading.crypto.services;

import com.trading.crypto.models.TwoFactorOTP;
import com.trading.crypto.models.User;

public interface TwoFactorOtpService {

	TwoFactorOTP createTwoFactorOtp(User user, String opt, String jwt);

	TwoFactorOTP findByUser(Long userId);

	TwoFactorOTP findById(String id);

	boolean verifyTwoFcatorOtp(TwoFactorOTP twoFactorOtp, String otp);

	void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp);
}
