/**
 * 
 */
package com.google.code.facebookwebapp.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;

import com.google.code.facebookwebapp.service.UserService;
import com.google.code.facebookwebapp.web.util.FacebookConstants;
import com.google.code.facebookwebapp.web.util.FacebookProperty;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

/**
 * @author Cesar Arevalo
 * @since 0.4
 */
public class OauthController extends MultiActionController {

	private static Log log = LogFactory
			.getLog(OauthController.class);

	@Autowired
	private UserService userService;

	public ModelAndView defaultMultiFormHandler(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = null;

		Map<String, Object> model = new HashMap<String, Object>();

		if (request.getParameter("redirect_to") != null) {
			String redirectTo = request.getParameter("redirect_to");
			if (log.isDebugEnabled()) {
				log.debug("redirect_to=" + redirectTo);
			}
			request.getSession().setAttribute("redirect_to", redirectTo);
		}
		
		if (request.getParameter("code") == null) {

			String url = "https://graph.facebook.com/oauth/authorize?"
					+ "scope=publish_stream,offline_access,create_event"
					+ "&client_id="
					+ FacebookProperty
							.getString(FacebookConstants.PROPERTY_API_KEY)
					+ "&redirect_uri="
					+ FacebookProperty
							.getString(FacebookConstants.PROPERTY_CONNECT_URL)
					+ "/oauth";

			mav = new ModelAndView(new RedirectView(url));
		} else {

			String redirectTo = (String) request.getSession().getAttribute("redirect_to");
			if (redirectTo != null) {
				mav = new ModelAndView(new RedirectView(redirectTo));
				request.getSession().removeAttribute("redirect_to");
			}
			else {
				mav = new ModelAndView("oauth");
			}

			String code = request.getParameter("code");

			model.put("code", request.getParameter("code"));

			String url = "https://graph.facebook.com/oauth/access_token?"
					+ "client_id="
					+ FacebookProperty
							.getString(FacebookConstants.PROPERTY_API_KEY)
					+ "&client_secret="
					+ FacebookProperty
							.getString(FacebookConstants.PROPERTY_API_SECRET)
					+ "&code="
					+ code
					+ "&redirect_uri="
					+ FacebookProperty
							.getString(FacebookConstants.PROPERTY_CONNECT_URL)
					+ "/oauth"
					;

			String accessToken = readUrl(url).split("&")[0].replaceFirst(
					"access_token=", "");

			model.put("accessToken", accessToken);

			FacebookClient facebookClient = new DefaultFacebookClient(
					accessToken);

			User user = facebookClient.fetchObject("me", User.class);

			if (log.isInfoEnabled()) {
				log.info("User name: " + user.getName());
			}

			userService.create(user.getId(), user.getName(), user.getLink(),
					accessToken, null);

			request.getSession().setAttribute(FacebookConstants.SESSION_FB_USER,
					user.getId());

			model.put("user", user);
			mav.addAllObjects(model);
		}
		return mav;
	}

	private String readUrl(String urlString) throws IOException {
		URL url = new URL(urlString);
		BufferedReader in = new BufferedReader(new InputStreamReader(url
				.openStream()));

		String response = "";
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			response += inputLine;

		in.close();
		return response;
	}
}
