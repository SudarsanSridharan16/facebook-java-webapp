package com.consulttek.facebook.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.w3c.dom.Document;

import com.consulttek.facebook.service.UserService;
import com.consulttek.facebook.web.util.FacebookConstants;
import com.consulttek.facebook.web.util.FacebookProperty;
import com.google.code.facebookapi.FacebookParam;
import com.google.code.facebookapi.FacebookWebappHelper;

/**
 * Handle authorization requests made from facebook.
 *
 * @author Cesar Arevalo
 * @since 0.1
 */
public class AuthorizeController extends MultiActionController {

	/**
	 * The User service
	 */
	private UserService userService;

	public ModelAndView defaultMultiFormHandler(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		FacebookWebappHelper<Document> facebookWebappHelper = FacebookWebappHelper.newInstanceXml(request, response, FacebookProperty.getString(FacebookConstants.PROPERTY_API_KEY), FacebookProperty.getString(FacebookConstants.PROPERTY_API_SECRET));

		if (facebookWebappHelper.isLogin()) {
			userService.synchronize(request.getParameter(FacebookParam.USER.toString()), request.getParameter(FacebookParam.SESSION_KEY.toString()));
		}

		return new ModelAndView(FacebookConstants.VIEW_BLANK);
	}

	/**
	 * @param userService
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
