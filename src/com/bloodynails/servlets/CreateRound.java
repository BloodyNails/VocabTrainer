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
	
	private static String backPath = Config.externalVocabularyPath;

    public CreateRound() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinkedList<VocabLang> langs = new LinkedList<VocabLang>();
		LinkedList<VocabList> lists = DBManager.getAllLists();
		
		if(lists == null) {
			Logger.log(MessageType.WARNING, "lists must not be null");
			response.sendRedirect(backPath);
			return;
		}
		
		if(lists.size() < 1) {
			Logger.log(MessageType.WARNING, "DBManager didn't find any lists in DB");
			response.sendRedirect(backPath);
			return;
		}
		
		// iterating over all selected lists and adding the langs to the LinkedList
		// this is used for the dropdown
		for(int i = 0; i < lists.size(); i++) {
			if(!langs.contains(lists.get(i).getLang1())) {
				langs.add(lists.get(i).getLang1());
			}
			if(!langs.contains(lists.get(i).getLang2())) {
				langs.add(lists.get(i).getLang2());
			}
		}
		
		request.setAttribute("listCount", lists.size());		
		request.setAttribute("lists", lists);
		request.setAttribute("langs", langs);
			
		request.getRequestDispatcher("/CreateRound.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinkedList<Long> selectedListIDs = new LinkedList<Long>();
		Map<String, String[]> requestMap = request.getParameterMap();
		List<VocabList> selectedLists = new LinkedList<VocabList>();
		
		VocabLang promptedLang = VocabLang.parseLang(request.getParameter("promptedLang"));
		if(promptedLang == null) {
			Logger.log(MessageType.WARNING, "promptedLang is null after parsing POST parameter upon creating a new round");
			response.sendRedirect(backPath);
			return;
		}
		
		for(Entry<String, String[]> entry : requestMap.entrySet()) {
			if(entry.getKey().toLowerCase().contains("checkbox") && entry.getValue()[0].toLowerCase().equals("on")) {
				String idStr = entry.getKey().toLowerCase().split("-")[1];
				Long id = Long.parseLong(idStr);
				selectedListIDs.add(id);
			}
		}
		
		if(selectedListIDs.size() < 1) {
			Logger.log(MessageType.WARNING, "no lists were selected upon creating a new round");
			response.sendRedirect(backPath);
			return;
		}
		
		for(int i = 0; i < selectedListIDs.size(); i++) {
			selectedLists.add(DBManager.getListByID(selectedListIDs.get(i)));
		}
		
		VocabPair usedLangs = selectedLists.get(0).getLangs();
		
		// check whether the selected lists have the same languages
		for(int j = 0; j < selectedLists.size(); j++) {
			if(!selectedLists.get(j).getLangs().compareTo(usedLangs)) {
				Logger.log(MessageType.WARNING, "lists did contain more than 2 languages");
				response.sendRedirect(backPath);
				return;
			}
		}
		
		
		boolean promptedLangIsCorrect = usedLangs.contains(promptedLang);
		if(!promptedLangIsCorrect) {
			Logger.log(MessageType.WARNING, "prompted language not in selected lists");
			response.sendRedirect(backPath);
			return;
		}
		
		VocabRound r = new VocabRound(false, selectedListIDs, new LinkedList<Long>(), usedLangs, promptedLang, 0f, 0, 0, 1);
		Logger.log("success");
		Logger.log(r.toString());
		if(!DBManager.save(r)) {
			Logger.log(MessageType.WARNING, "round " + r.toString() + " could not be saved or updated");
			response.sendRedirect(backPath);
			return;
		}
		
		response.sendRedirect(backPath);
	}

}
