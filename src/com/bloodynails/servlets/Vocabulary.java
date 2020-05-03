package com.bloodynails.servlets;

import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bloodynails.VocabRound;
import com.bloodynails.database.DBManager;

/**
 * Servlet implementation class Vocabulary
 */
@WebServlet(com.bloodynails.Config.internalVocabularyPath)
public class Vocabulary extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Vocabulary() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// TODO:
		// list all rounds
		// options:
		//  create new round
		//  delete round
		//  view stats
		LinkedList<VocabRound> rounds = DBManager.getAllRounds();
		
		// TODO IMPORTANT Don't forget to call removeWrongLangs() after creating a round
		
		request.setAttribute("rounds", rounds);
		request.setAttribute("roundCount", rounds.size());
		
		request.getRequestDispatcher("/Vocabulary.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doGet(request, response);
	}

}
