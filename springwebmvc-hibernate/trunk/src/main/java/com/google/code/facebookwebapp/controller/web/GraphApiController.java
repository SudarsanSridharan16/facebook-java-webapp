package com.google.code.facebookwebapp.controller.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.facebookwebapp.util.FacebookConstants;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

/**
 * @author Cesar Arevalo
 * @since 0.4
 */
@Controller("webGraphApiController")
public class GraphApiController {

	private static Log log = LogFactory.getLog(GraphApiController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping("/graph-api")
	public String hanlder(Model model, HttpServletRequest request)
			throws Exception {
		model.addAttribute("selectedNav", "graphApi");

		String facebookUserId = (String) request.getSession().getAttribute(
				FacebookConstants.SESSION_FB_USER);
		if (facebookUserId == null
				&& request.getSession().getAttribute("fb_cookie") != null) {
			Map<String, String> facebookCookie = (Map<String, String>) request
					.getSession().getAttribute("fb_cookie");
			facebookUserId = facebookCookie.get("uid");
			request.getSession().setAttribute(
					FacebookConstants.SESSION_FB_USER, facebookUserId);
		}

		model.addAttribute("facebookUserId", facebookUserId);

		return "web.graph-api";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/graph-api/me")
	public String me(Model model, HttpServletRequest request) throws Exception {
		model.addAttribute("selectedNav", "graphApiMe");
		Map<String, String> facebookCookie = (Map<String, String>) request
				.getSession().getAttribute("fb_cookie");
		String accessToken = facebookCookie.get("access_token");

		String url = "https://graph.facebook.com/me?access_token="
				+ accessToken;
		String response = read(url);

		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
		User user = facebookClient.fetchObject("me", User.class);

		model.addAttribute("response", response);
		model.addAttribute("url", url);
		model.addAttribute("user", user);

		return "web.graph-api.me";
	}
	
	public String read(String inUrl) {
		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3";
		String referer = "http://www.consultek.us/facebook-webapp-dev/graph-api/me";
		if (log.isDebugEnabled()) {
			log.debug("IN: Browser.read()");
		}

		if (null == inUrl) {
			if (log.isDebugEnabled()) {
				log.debug("inUrl refers to null");
			}
			return null;
		}

		URL url = null;
		HttpURLConnection conn = null;
		BufferedReader in = null;
		StringBuilder result = null;

		try {
			url = new URL(inUrl);
			conn = (HttpURLConnection) url.openConnection();

			if ((null != userAgent) && (userAgent.length() > 0)) {
				conn.setRequestProperty("User-Agent", userAgent);
			}

			referer = conn.getURL().getProtocol() + "://"
					+ conn.getURL().getHost();
			if (null != conn.getURL().getHost()) {
				conn.setRequestProperty("Referer", referer);
			}

			conn.connect();

			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));

			String inputLine = null;
			result = new StringBuilder();

			while ((null != (inputLine = in.readLine()))) {
				result.append(inputLine);
			}
		} catch (MalformedURLException mue) {
			log.error("Error creating URL", mue);
		} catch (IOException ioe) {
			log.error("Input/Output error", ioe);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException ioe) {
					log.error("Input/Output error", ioe);
				}
			}

			if (null != conn) {
				conn.disconnect();
			}

			if (log.isTraceEnabled()) {
				log.trace("Result: " + result.toString());
			}
			if (log.isDebugEnabled()) {
				log.debug("OUT: Browser.read()");
			}
		}

		return result.toString();
	}
}
