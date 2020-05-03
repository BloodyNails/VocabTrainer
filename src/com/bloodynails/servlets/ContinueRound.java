package com.bloodynails.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bloodynails.VocabRound;
import com.bloodynails.database.DBManager;
import com.bloodynails.logging.Logger;

@WebServlet(com.bloodynails.Config.internalContinueRoundPath)
public class ContinueRound extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ContinueRound() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final Long roundID = Long.parseLong(request.getParameter("roundID"));
		final VocabRound round = DBManager.getRoundByID(roundID);
		
		if(round.isCompleted()) {
			// TODO: redirect to previous page
			Logger.log("round #" + roundID.toString() + " is already completed");
		}
		else {
			Logger.log("continuing round: " + roundID.toString());
			// TODO: continue round
		}
		
		doGet(request, response);
	}

}
