package com.bloodynails.servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bloodynails.VocabLang;
import com.bloodynails.VocabList;
import com.bloodynails.VocabPair;
import com.bloodynails.database.DBManager;
import com.bloodynails.logging.Logger;
import com.bloodynails.logging.MessageType;

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
		request.setAttribute("langs", VocabLang.values());
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
					VocabPair langs = new VocabPair(VocabLang.parseLang(lang1), VocabLang.parseLang(lang2));
					if(langs.isValid()) {
						DBManager.save(new VocabList(description, langs));
					}
					else {
						// TODO: html warning (langs invalid either because lang1 == lang2 or because of other reasons)
						Logger.log(MessageType.WARNING, "Languages invalid on creating a new list");
					}
				}
				else {
					// TODO: html warning (description empty)
					Logger.log(MessageType.WARNING, "Description is empty on creating a new list");
				}
			}
			else {
				String e = "Description should not be null on creating a new list";
				Logger.log(MessageType.WARNING, e);
				throw new NullPointerException(e);
			}
		}
		
		
		doGet(request, response);
	}

}
