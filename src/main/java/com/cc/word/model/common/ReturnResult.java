package com.cc.word.model.common;

import java.util.HashMap;

@SuppressWarnings("serial")
public class ReturnResult extends HashMap<String, String> {
	public String getReturnCode() {
		return get("return_code");
	}

	public void setReturnCode(String returnCode) {
		put("return_code", returnCode);
	}

	public String getReturnMsg() {
		return get("return_msg");
	}

	public void setReturnMsg(String returnMsg) {
		put("return_msg", returnMsg);
	}

	public String getOpCode() {
		return get("op_code");
	}

	public void setOpCode(String opCode) {
		put("op_code", opCode);
	}

	public String getOpMsg() {
		return get("op_msg");
	}

	public void setOpMsg(String opMsg) {
		put("op_msg", opMsg);
	}

}
