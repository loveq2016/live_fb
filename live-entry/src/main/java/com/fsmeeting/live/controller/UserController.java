package com.fsmeeting.live.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fsmeeting.live.common.bean.LiveRoom;
import com.fsmeeting.live.common.bean.ResponseMsg;
import com.fsmeeting.live.common.bean.vo.LoginRequestVO;
import com.fsmeeting.live.common.bean.vo.LoginResponseVO;
import com.fsmeeting.live.common.enums.Response;
import com.fsmeeting.live.common.util.TokenUtils;
import com.fsmeeting.live.service.IUserService;

@Controller
@RequestMapping("/live")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/{liveURI}", method = RequestMethod.GET)
	public ModelAndView getLiveRoomInfo(@PathVariable String liveURI, HttpServletResponse resp, HttpServletRequest req)
			throws IOException {
		ModelAndView result = new ModelAndView();
		ResponseMsg<LiveRoom> response = userService.getLiveRoomInfo(liveURI);
		LiveRoom room = response.getData();
		if (null == room) {
			result.addObject("msg", "直播URL非法授权,未找到可用的直播房间信息");
			result.setViewName("error");
			return result;
		}
		result.addObject("room", room);
		HttpSession session = req.getSession();
		session.setAttribute("ticket", TokenUtils.generate()); // 买票
		// session.setMaxInactiveInterval(30 * 60); //这个会话的时间要多久
		if (null != session.getAttribute("loginMsg")) {
			session.removeAttribute("loginMsg");
		}
		result.setViewName("login");
		return result;
	}

	@RequestMapping(value = "/{liveURI}/home", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseMsg<LoginResponseVO> login(@PathVariable String liveURI, LoginRequestVO bean, HttpServletRequest req,
			BindingResult validateResult) {

		ResponseMsg<LoginResponseVO> loginMsg = new ResponseMsg<>();
		HttpSession session = req.getSession();
		// ticket 校验
		if (!StringUtils.equals(bean.getTicket(), (String) session.getAttribute("ticket"))) {
			loginMsg.setCode(Response.REQUEST_ILLEGAL.getCode());
			loginMsg.setMsg("非法请求");
			return loginMsg;
		}

		loginMsg = userService.login(liveURI, bean);

		if (Response.SUCCESS.getCode() == loginMsg.getCode()) {
			session.setAttribute("loginMsg", loginMsg);
			LOG.info("Proxy Address:" + loginMsg.getData().getProxyAddr());
			LOG.info("Room id:" + loginMsg.getData().getRoom().getId());
			LOG.info("Token:" + loginMsg.getData().getToken());
			LOG.info("User id:" + loginMsg.getData().getUserId());
		}

		return loginMsg;
	}

	@RequestMapping(value = "/{liveURI}/home", method = { RequestMethod.GET })
	public ModelAndView toLivePage(@PathVariable String liveURI, LoginRequestVO bean, HttpServletRequest req,
			BindingResult validateResult) {
		ModelAndView result = new ModelAndView();
		HttpSession session = req.getSession();
		@SuppressWarnings("unchecked")
		ResponseMsg<LoginResponseVO> loginMsg = (ResponseMsg<LoginResponseVO>) session.getAttribute("loginMsg");
		if (null == loginMsg) {
			LOG.info("非法请求");
			result.setViewName("error");
			return result;
		}
		LOG.info("Proxy Address:" + loginMsg.getData().getProxyAddr());
		LOG.info("Room id:" + loginMsg.getData().getRoom().getId());
		LOG.info("Token:" + loginMsg.getData().getToken());
		LOG.info("User id:" + loginMsg.getData().getUserId());
		result.addObject("loginMsg", loginMsg);
		result.setViewName("main");
		return result;
	}
}
