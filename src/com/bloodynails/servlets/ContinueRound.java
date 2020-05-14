package com.bloodynails.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bloodynails.Config;
import com.bloodynails.VocabCycle;
import com.bloodynails.VocabRound;
import com.bloodynails.database.DBManager;
import com.bloodynails.logging.Logger;
import com.bloodynails.logging.MessageType;

@WebServlet(com.bloodynails.Config.internalContinueRoundPath)
public class ContinueRound extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static VocabCycle currentCycle;
	private static VocabRound currentRound;
	private static String backPath = Config.externalVocabularyPath;
       
    public ContinueRound() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(Config.externalVocabularyPath);
		Logger.log(MessageType.WARNING, "Continue Round not possible yet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final Long roundID = Long.parseLong(request.getParameter("roundID"));
		final VocabRound round = DBManager.getRoundByID(roundID);
		
		if(round == null) {
			Logger.log(MessageType.WARNING, "round #" + roundID + " could not be found");
			response.sendRedirect(backPath);
			return;
		}
		
		currentRound = round;
		if (currentRound.isCompleted()) {
			Logger.log(MessageType.WARNING, "round #" + roundID + " is already finished");
			response.sendRedirect(backPath);
			return;
		}
		
		if (currentRound.getCycleIDs() == null) {
			Logger.log(MessageType.WARNING, "round #" + roundID + " has cycles == null");
			response.sendRedirect(backPath);
			return;
		}
		
		doGet(request, response);
	}
}
