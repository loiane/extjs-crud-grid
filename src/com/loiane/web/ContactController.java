package com.loiane.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.loiane.model.Contact;
import com.loiane.service.ContactService;

/**
 * Controller - Spring
 * 
 * @author Loiane Groner
 * http://loianegroner.com (English)
 * http://loiane.com (Portuguese)
 */
public class ContactController extends MultiActionController  {

	private ContactService contactService;
	
	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try{

			List<Contact> contacts = contactService.getContactList();

			return getModelMap(contacts);

		} catch (Exception e) {

			return getModelMapError("Error trying to retrieve contacts.");
		}
	}
	
	public ModelAndView create(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try{

			Object data = request.getParameter("data");

			List<Contact> contacts = contactService.create(data);

			return getModelMap(contacts);

		} catch (Exception e) {

			return getModelMapError("Error trying to create contact.");
		}
	}
	
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{

			Object data = request.getParameter("data");

			List<Contact> contacts = contactService.update(data);

			return getModelMap(contacts);

		} catch (Exception e) {

			return getModelMapError("Error trying to update contact.");
		}
	}
	
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		try{

			String data = request.getParameter("data");

			contactService.delete(data);

			Map<String,Object> modelMap = new HashMap<String,Object>(3);
			modelMap.put("success", true);

			return new ModelAndView("jsonView", modelMap);

		} catch (Exception e) {

			return getModelMapError("Error trying to delete contact.");
		}
	}
	
	/**
	 * Generates modelMap to return in the modelAndView
	 * @param contacts
	 * @return
	 */
	private ModelAndView getModelMap(List<Contact> contacts){
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("total", contacts.size());
		modelMap.put("data", contacts);
		modelMap.put("success", true);
		
		return new ModelAndView("jsonView", modelMap);
	}
	
	/**
	 * Generates modelMap to return in the modelAndView in case
	 * of exception
	 * @param msg message
	 * @return
	 */
	private ModelAndView getModelMapError(String msg){

		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", false);

		return new ModelAndView("jsonView",modelMap);
	} 


	/**
	 * Spring use - DI
	 * @param dadoService
	 */
	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

}
