package com.bloodynails.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bloodynails.VocabList;
import com.bloodynails.VocabRound;
import com.bloodynails.database.DBManager;
import com.bloodynails.logging.Logger;

/**
 * Servlet implementation class Vocabulary
 */
@WebServlet("/Training/Vocabulary")
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
		
		for(int i = 0; i < rounds.size(); i++) {
			Logger.log("\n"+rounds.get(i).toString());
		}
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doGet(request, response);
	}

}
