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
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.google.code.facebookwebapp.service.UserService;
import com.google.code.facebookwebapp.web.util.FacebookConstants;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

/**
 * @author Cesar Arevalo
 * @since 0.4
 */
public class GraphController extends MultiActionController {
	
	private static Log log = LogFactory.getLog(GraphController.class);
	
	@Autowired
	private UserService userService;
	
	public ModelAndView defaultMultiFormHandler(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		String facebookUserId = (String) request.getSession().getAttribute(FacebookConstants.SESSION_FB_USER);
		if (facebookUserId == null) {
			request.getSession().setAttribute("redirect_to", request.getRequestURI());
			if (log.isInfoEnabled()) {
				log.info("redirect_to=" + request.getRequestURI());
			}
			return new ModelAndView(new RedirectView("/facebook-webapp/oauth.fbml"));
		}
		
		String accessToken = userService.lookup(facebookUserId).getAccessToken();
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
		User user = facebookClient.fetchObject("me", User.class);
		
		model.put("user", user);
		
		return new ModelAndView(new MappingJacksonJsonView(), model);
	}
	
}
