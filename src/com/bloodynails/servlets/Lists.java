package com.bloodynails.servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bloodynails.VocabList;
import com.bloodynails.VocabPair;
import com.bloodynails.database.DBManager;

/**
 * Servlet implementation class Lists
 */
@WebServlet("/Lists")
public class Lists extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Lists() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(DBManager.getAllLists() != null) {
			LinkedList<VocabList> lists = DBManager.getAllLists();
			if(lists != null && lists.size() > 0) {
				request.setAttribute("listCount", lists.size());		
				request.setAttribute("lists", lists);
			}
		}
		// TODO: add available languages here instead of hardcoding them into html
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		request.getRequestDispatcher("/Lists.jsp").forward(request, response);	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// DELETE LIST
		if(request.getParameter("listID") != null) {
			Long listID = Long.parseLong(request.getParameter("listID"));
			if(listID != null && listID > -1) {
				DBManager.deleteListByID(listID);
			}
		}
		
		// ADD NEW LIST
		if(request.getParameter("description") != null && request.getParameter("lang1") != null && request.getParameter("lang2") != null) {
			String description = (String) request.getParameter("description");
			String lang1 = (String) request.getParameter("lang1");
			String lang2 = (String) request.getParameter("lang2");
			if(description != null) {
				if(!description.isEmpty()) {
					VocabPair langs = VocabPair.parseLangs(lang1, lang2);
					if(langs.isValid()) {
						DBManager.save(new VocabList(description, langs));
					}
					else {
						// TODO: html warning (langs invalid either because lang1 == lang2 or because of other reasons)
						// add this to logger
					}
				}
				else {
					// TODO: html warning (description empty)
					// add this to logger
				}
			}
			else {
				throw new NullPointerException("description should not be null when posting new list form");
			}
		}
		
		
		doGet(request, response);
	}

}
