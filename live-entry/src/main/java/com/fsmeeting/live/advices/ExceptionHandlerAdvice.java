package com.fsmeeting.live.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSONException;
import com.fsmeeting.live.common.bean.ResponseMsg;

@ControllerAdvice
public class ExceptionHandlerAdvice {
	public ExceptionHandlerAdvice() {
	}

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseMsg<Object> exceptionResponse(Exception ex) {
		ResponseMsg<Object> result = new ResponseMsg<Object>();
		result.setCode(500);
		result.setMsg(ex.getMessage());
		ex.printStackTrace();
		return result;
	}

	@ExceptionHandler({ JSONException.class })
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseMsg<Object> httpMessageNotReadableResponse(JSONException je) {
		ResponseMsg<Object> result = new ResponseMsg<Object>();
		result.setCode(400);
		result.setMsg(je.getMessage());
		je.printStackTrace();
		return result;
	}
}
