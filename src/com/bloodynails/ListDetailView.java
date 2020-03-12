package com.bloodynails;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListDetailView
 */
@WebServlet("/Lists/View")
public class ListDetailView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private VocabList list;
	
    public ListDetailView() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		list = DBManager.getListByID(id);
		list.fillWordsFromDB();
		LinkedList<Word> words = list.getWords();
		
		request.setAttribute("words", words);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/ListDetailView.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String wordLang1 = (String) request.getParameter("wordLang1");
		String wordLang2 = (String) request.getParameter("wordLang2");
		if(list != null) {
			list.addWord(new Word(list.getID(), wordLang1, wordLang2));
		}
		doGet(request, response);
	}

}
