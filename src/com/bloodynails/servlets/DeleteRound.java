package com.bloodynails.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bloodynails.Config;
import com.bloodynails.database.DBManager;
import com.bloodynails.logging.Logger;

@WebServlet(com.bloodynails.Config.internalDeleteRoundPath)
public class DeleteRound extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteRound() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(Config.externalVocabularyPath);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final Long roundID = Long.parseLong(request.getParameter("roundID"));
		if(DBManager.deleteRoundByID(roundID)) {
			Logger.log("success");
		}
		else {
			Logger.log("round could not be deleted");
		}
		doGet(request, response);
	}

}
