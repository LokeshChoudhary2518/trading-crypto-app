package com.trading.crypto.response;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.crypto.models.TwoFactorOTP;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {

	TwoFactorOTP findByUserId(Long userId);
}
