package com.google.code.facebookwebapp.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.code.facebookapi.FacebookParam;
import com.google.code.facebookwebapp.service.UserService;
import com.google.code.facebookwebapp.web.util.FacebookConstants;
import com.google.code.facebookwebapp.web.util.FacebookProperty;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

/**
 * @author Cesar Arevalo
 * @since 0.4
 */
public class NotificationsController extends MultiActionController {

	private static Log log = LogFactory.getLog(NotificationsController.class);

	@Autowired
	private UserService userService;

	public ModelAndView defaultMultiFormHandler(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put(FacebookConstants.MODEL_NOTIFICATIONS_SELECTED, true);

		return new ModelAndView(FacebookConstants.VIEW_NOTIFICATIONS, model);
	}

	public ModelAndView sendNotificationToOneUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		String facebookUserId = request.getParameter(FacebookParam.USER
				.toString());

		String accessToken = userService.lookup(facebookUserId)
				.getAccessToken();

		/**
		 * If the user does not have an access token we will authenticate him using oauth.
		 */
		if (accessToken == null) {
			String redirectTo = "redirect_to=" + FacebookProperty.getString(FacebookConstants.PROPERTY_CANVAS_URL) + "/notifications";
			model.put("redirectTo", redirectTo);
			return new ModelAndView(FacebookConstants.VIEW_OAUTH_REDIRECT, model);
		}

		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);

		FacebookType publishMessageResponse = facebookClient
				.publish(
						"me/feed",
						FacebookType.class,
						Parameter
								.with(
										"message",
										"You answered '"
												+ request.getParameter("state")
												+ "' to the question \"Does developing for facebook is easier with facebook-java-webapp?\""));

		if (log.isInfoEnabled()) {
			log.info("Published message ID: " + publishMessageResponse.getId());
		}

		model.put(FacebookConstants.MODEL_NOTIFICATIONS_SELECTED, true);
		model.put("message", "Published message ID: "
				+ publishMessageResponse.getId());
		return new ModelAndView(FacebookConstants.VIEW_NOTIFICATIONS, model);
	}
}
