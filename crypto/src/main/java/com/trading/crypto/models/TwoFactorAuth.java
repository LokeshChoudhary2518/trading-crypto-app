package com.trading.crypto.models;

import com.trading.crypto.domians.VarificationType;

public class TwoFactorAuth {

	private boolean isEnabled = false;

	private VarificationType sendTo;

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public VarificationType getSendTo() {
		return sendTo;
	}

	public void setSendTo(VarificationType sendTo) {
		this.sendTo = sendTo;
	}

}
