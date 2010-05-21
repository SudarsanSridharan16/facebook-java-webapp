/**
 * 
 */
package com.google.code.facebookwebapp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.facebookwebapp.service.UserService;
import com.google.code.facebookwebapp.util.FacebookConstants;
import com.google.code.facebookwebapp.util.FacebookProperty;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

/**
 * @author Cesar Arevalo
 * @since 0.4
 */
@Controller
public class OauthController {

	private static Log log = LogFactory
			.getLog(OauthController.class);

	@Autowired
	private UserService userService;

	@RequestMapping("/oauth")
	public String hanlder(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String view = null;
		
		if (request.getParameter("redirect_to") != null) {
			String redirectTo = request.getParameter("redirect_to");
			if (log.isDebugEnabled()) {
				log.debug("redirect_to=" + redirectTo);
			}
			request.getSession(true).setAttribute("redirect_to", redirectTo);
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
			
			view = "redirect:" + url;
		} else {

			String redirectTo = (String) request.getSession().getAttribute("redirect_to");
			if (redirectTo != null) {
				view = "redirect:" + redirectTo;
				request.getSession().removeAttribute("redirect_to");
			}
			else {
				view = "redirect:oauth";
			}

			String code = request.getParameter("code");

			model.addAttribute("code", request.getParameter("code"));

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

			model.addAttribute("accessToken", accessToken);

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

			model.addAttribute("user", user);
		}
		return view;
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
