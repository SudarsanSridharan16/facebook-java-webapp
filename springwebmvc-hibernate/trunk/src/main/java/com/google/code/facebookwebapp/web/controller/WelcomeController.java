package com.google.code.facebookwebapp.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.spring.web.servlet.view.JsonView;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.w3c.dom.Document;

import com.google.code.facebookapi.FacebookWebappHelper;
import com.google.code.facebookwebapp.web.util.FacebookConstants;
import com.google.code.facebookwebapp.web.util.FacebookProperty;

/**
 * @author Cesar Arevalo
 * @since 0.1
 */
public class WelcomeController extends MultiActionController {

	public ModelAndView defaultMultiFormHandler(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put(FacebookConstants.MODEL_WELCOME_SELECTED, true);

		return new ModelAndView(FacebookConstants.VIEW_WELCOME, model);
	}

	public ModelAndView publishToMiniFeed(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// replace this with a valid template bundle for your application
		String templateBundleId = FacebookProperty.getString(FacebookConstants.PROPERTY_FEED_TEMPLATE_BUNDLE_1);

		// get a facebook webapp helper
		FacebookWebappHelper<Document> facebookWebappHelper = FacebookWebappHelper.newInstanceXml(request, response, FacebookProperty.getString(FacebookConstants.PROPERTY_API_KEY), FacebookProperty.getString(FacebookConstants.PROPERTY_API_SECRET));

		facebookWebappHelper.get_api_client().feed_publishUserAction(new Long(templateBundleId));

		return defaultMultiFormHandler(request, response);
	}

	public ModelAndView publishToMiniFeedFromFeedForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String templateBundleId = FacebookProperty.getString(FacebookConstants.PROPERTY_FEED_TEMPLATE_BUNDLE_1);

		Map<String, Object> templateData = new HashMap<String, Object>();
		templateData.put("key1", "val1");

		Map<String, Object> feed = new HashMap<String, Object>();
		feed.put("template_id", templateBundleId);
		feed.put("template_data", templateData);

		Map<String, Object> content = new HashMap<String, Object>();
		content.put("feed", feed);
		content.put("next", FacebookProperty.getString(FacebookConstants.PROPERTY_CANVAS_URL));

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("method", "feedStory");
		model.put("content", content);

		return new ModelAndView(new JsonView(), model);
	}
}
