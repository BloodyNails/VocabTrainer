package com.bloodynails.servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bloodynails.Config;
import com.bloodynails.VocabList;
import com.bloodynails.VocabWord;
import com.bloodynails.database.DBManager;
import com.bloodynails.logging.Logger;
import com.bloodynails.logging.MessageType;

/**
 * Servlet implementation class ListDetailView
 */
@WebServlet(com.bloodynails.Config.internalListsDetailViewPath)
public class ListDetailView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static VocabList list;
	private static String backPath = Config.externalListsPath;
	
    public ListDetailView() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String listIDStr = request.getParameter("listID");
		if(listIDStr == null) {
			Logger.log(MessageType.WARNING, "listID parameter is null");
			response.sendRedirect(backPath);
			return;
		}
		if(listIDStr.isEmpty()) {
			Logger.log(MessageType.WARNING, "listID parameter is empty");
			response.sendRedirect(backPath);
			return;
		}
		
		Long listID = Long.parseLong(listIDStr);
		if(listID < 0) {
			Logger.log(MessageType.WARNING, "listID must be equal to or greater than 0");
			response.sendRedirect(backPath);
			return;
		}
		
		list = DBManager.getListByID(listID);
		if(list == null) {
			Logger.log(MessageType.WARNING, "list #" + listID  + " could not be found");
			response.sendRedirect(backPath);
			return;
		}
		
		LinkedList<VocabWord> words = list.getWords();
		
		request.setAttribute("wordCount", words.size());
		request.setAttribute("words", words);
		request.setAttribute("list", list);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		request.getRequestDispatcher("/ListDetailView.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// DELETE WORD FROM LIST
		if (request.getParameter("wordID") != null) {
			Long wordID = Long.parseLong(request.getParameter("wordID"));
			if (wordID != null && wordID > -1) {
				if (!DBManager.deleteWordByID(wordID)) {
					Logger.log(MessageType.WARNING, "word #" + wordID + " could not be deleted");
				}
			}
		}
		
		// ADD WORD TO LIST
		if (request.getParameter("wordLang1") != null && request.getParameter("wordLang2") != null) {
			String wordLang1 = (String) request.getParameter("wordLang1");
			String wordLang2 = (String) request.getParameter("wordLang2");
			if (wordLang1 != null && wordLang2 != null) {
				if (!wordLang1.isEmpty() && !wordLang2.isEmpty()) {
					if (list != null) {
						VocabWord w = new VocabWord(list.getID(), wordLang1, wordLang2);
						if (w != null) {
							if (!w.save()) {
								Logger.log(MessageType.WARNING, "word: " + w.toString() + " could not be saved");
							}
						}
					}
				}

			}
		}
		
		doGet(request, response);
	}

	public static VocabList getList() {
		return list;
	}
}
