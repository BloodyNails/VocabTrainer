package com.bloodynails.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bloodynails.Config;
import com.bloodynails.VocabLang;
import com.bloodynails.VocabList;
import com.bloodynails.VocabPair;
import com.bloodynails.VocabRound;
import com.bloodynails.database.DBManager;
import com.bloodynails.logging.Logger;
import com.bloodynails.logging.MessageType;

@WebServlet(com.bloodynails.Config.internalCreateRoundPath)
public class CreateRound extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CreateRound() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(DBManager.getAllLists() != null) {
			LinkedList<VocabList> lists = DBManager.getAllLists();
			if(lists != null && lists.size() > 0) {
				request.setAttribute("listCount", lists.size());		
				request.setAttribute("lists", lists);
				LinkedList<VocabLang> langs = new LinkedList<VocabLang>();
				for(int i = 0; i < lists.size(); i++) {
					if(!langs.contains(lists.get(i).getLang1())) {
						langs.add(lists.get(i).getLang1());
					}
					if(!langs.contains(lists.get(i).getLang2())) {
						langs.add(lists.get(i).getLang2());
					}
				}
				request.setAttribute("langs", langs);
			}
		}
		request.getRequestDispatcher("/CreateRound.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinkedList<Long> selectedIDs = new LinkedList<Long>();
		
		Map<String, String[]> requestMap = request.getParameterMap();
		
		for(Entry<String, String[]> entry : requestMap.entrySet()) {
			if(entry.getKey().toLowerCase().contains("checkbox") && entry.getValue()[0].toLowerCase().equals("on")) {
				String idStr = entry.getKey().toLowerCase().split("-")[1];
				Long id = Long.parseLong(idStr);
				selectedIDs.add(id);
			}
		}
		
		boolean continueCreation = true;
		
		if(selectedIDs.size() > 0) {
			
			VocabLang promptedLang = VocabLang.parseLang(request.getParameter("promptedLang"));
			
			List<VocabList> selectedLists = new LinkedList<VocabList>();
			
			for(int i = 0; i < selectedIDs.size(); i++) {
				selectedLists.add(DBManager.getListByID(selectedIDs.get(i)));
			}
			
			VocabPair usedLangs = selectedLists.get(0).getLangs();
			
			// TODO: implement warnings for user
			
			for(int j = 0; j < selectedLists.size(); j++) {
				if(!selectedLists.get(j).getLangs().compareTo(usedLangs)) {
					Logger.log(MessageType.WARNING, "lists did contain more than 2 languages");
					continueCreation = false;
					break;
				}
			}
			
			if(promptedLang != null) {
				boolean correctLanguages = usedLangs.contains(promptedLang);
				if(!correctLanguages) {
					Logger.log(MessageType.WARNING, "prompted language not in selected lists");
				}
				continueCreation &= correctLanguages;
				
			}
			else {
				Logger.log(MessageType.WARNING, "prompted language could not be parsed");
				continueCreation = false;
			}
			
			if(continueCreation) {
				VocabRound r = new VocabRound(false, selectedIDs, new LinkedList<Long>(), usedLangs, promptedLang, 0f, 0, 0, 1);
				Logger.log("success");
				Logger.log(r.toString());
				DBManager.save(r);
				response.sendRedirect(Config.externalVocabularyPath);
				return;
			}
		}
		else {
			Logger.log(MessageType.WARNING, "no lists were selected");
		}
		
		doGet(request, response);
	}

}
