package com.bloodynails.servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bloodynails.Config;
import com.bloodynails.VocabCycle;
import com.bloodynails.VocabList;
import com.bloodynails.VocabRound;
import com.bloodynails.VocabTimer;
import com.bloodynails.VocabWord;
import com.bloodynails.VocabTWord;
import com.bloodynails.database.DBManager;
import com.bloodynails.logging.Logger;
import com.bloodynails.logging.MessageType;

@WebServlet(com.bloodynails.Config.internalContinueRoundPath)
public class ContinueRound extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static VocabCycle currentCycle;
	private static VocabRound currentRound;
       
    public ContinueRound() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(currentRound != null) {
			currentRound = DBManager.getRoundByID(currentRound.getID());
			currentCycle = DBManager.getCycleByID(currentRound.getCycleIDs().getFirst());
			
			Logger.log("currentRound: "+currentRound.toString());
			Logger.log("currentCycle: "+currentCycle.toString());
			
			
			VocabTimer timer = currentCycle.getTimer();
			VocabWord word = DBManager.getWordByID(currentCycle.getCurrTWordID());
			if(word != null) {
				VocabTWord currTWord = new VocabTWord(word, currentCycle.getID(), false, false);	
				if(DBManager.save(currTWord)) {
					VocabTWord wt = DBManager.getTWordByID(currTWord.getID());
					Logger.log(wt.toString());
				}
			}
			else {
				Logger.log(MessageType.WARNING, "word #"+currentCycle.getCurrTWordID()+ " of current cycle could not be found");
				response.sendRedirect(Config.externalVocabularyPath);
				return;
			}
		}
		else {
			Logger.log(MessageType.WARNING, "cycle to be continued not specified");
			response.sendRedirect(Config.externalVocabularyPath);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final Long roundID = Long.parseLong(request.getParameter("roundID"));
		final VocabRound round = DBManager.getRoundByID(roundID);
		
		if(round != null) {
			
			LinkedList<Long> listIDs = round.getListIDs();
			if(listIDs != null) {
				if(listIDs.size() > 0) {
					for(int i = 0; i < listIDs.size(); i++) {
						Long lID = listIDs.get(i);
						VocabList list = DBManager.getListByID(lID);
						if(list == null) {
							Logger.log(MessageType.WARNING, "List #"+lID+" specified in round #"+roundID+" could not be found");
							response.sendRedirect(Config.externalVocabularyPath);
							return;
						}
					}
				}
				else {
					Logger.log(MessageType.WARNING, "There are no lists inside round #"+roundID);
					response.sendRedirect(Config.externalVocabularyPath);
					return;
				}
			}
			else {
				Logger.log(MessageType.WARNING, "ListIDs inside round #"+roundID+" could not be found");
				response.sendRedirect(Config.externalVocabularyPath);
				return;
			}
			
			currentRound = round;
			if(round.isCompleted()) {
				// TODO: implement warning for user
				Logger.log(MessageType.WARNING, "round #" + roundID.toString() + " is already completed");
				response.sendRedirect(Config.externalVocabularyPath);
				return;
			}
			else {	
				LinkedList<VocabList> lists = round.getLists();
				
				if(round.getCycleIDs().size() < 1) {
					Logger.log("starting a new round");
					
					int wordCount = 0;
					
					for(int i = 0; i < round.getListIDs().size(); i++) {
						VocabList l = DBManager.getListByID(round.getListIDs().get(i));
						if(l == null) {
							Logger.log(MessageType.WARNING, "list #" + round.getListIDs().get(i).toString() + " could not be found");
							response.sendRedirect(Config.externalVocabularyPath);
							// TODO: add user warning
							return;
						}
						wordCount += l.getWords().size();
					}
					
					if(lists != null) {
						if(lists.size() > 0) {
							VocabList currList = lists.getFirst();
							LinkedList<VocabWord> currWords = currList.getWords();
							if(currWords != null) {
								if(currWords.size() > 0) {
									if(currWords.getFirst() != null) {
										Long currWordID = currWords.getFirst().getID();
										VocabCycle currCycle = new VocabCycle(DBManager.getNextCycleID(), roundID, false, wordCount, currWordID, 0, 0, 1, 0f);
										if(DBManager.save(currCycle)) {
											currentCycle = currCycle;
											round.addCycleID(currCycle.getID());
										}
										else {
											Logger.log(MessageType.WARNING, "cycle could not be saved to DB");
											response.sendRedirect(Config.externalVocabularyPath);
											return;	
										}	
									}
								}
								else {
									Logger.log(MessageType.WARNING, "list #"+currList.getID() +" has an empty list of VocabWords. Add words first to the list");
									response.sendRedirect(Config.externalVocabularyPath);
									return;									
								}
							}
							else {
								Logger.log(MessageType.WARNING, "list #"+currList.getID() +" has words equal to null");
								response.sendRedirect(Config.externalVocabularyPath);
								return;								
							}
						}
						else {
							Logger.log(MessageType.WARNING, "round #"+roundID +" has an empty list of VocabLists");
							response.sendRedirect(Config.externalVocabularyPath);
							return;
						}
					}
					else {
						Logger.log(MessageType.WARNING, "round #"+roundID +" has lists equal to null");
						response.sendRedirect(Config.externalVocabularyPath);
						return;
					}
				}
				else {
					Logger.log("continuing round #"+roundID.toString());
					VocabCycle lastCycle = DBManager.getCycleByID(round.getCycleIDs().getLast());
					
					if(lastCycle != null) {
						if(lastCycle.isCompleted()) {
							int wordCount = 0;
							
							for(int i = 0; i < round.getListIDs().size(); i++) {
								// need to check which words were right in the last cycle and eliminate the false ones
								wordCount += DBManager.getListByID(round.getListIDs().get(i)).getWords().size();
							}
							Long currWordID = null;
							VocabCycle currCycle; // ... 
						}
						else {
							Logger.log("continuing cycle #"+lastCycle.getID());
						}
					}
					else {
						Logger.log(MessageType.WARNING, "last cycle of round #"+roundID.toString()+" could not be found");
					}
				}
			}
		}
		else {
			Logger.log(MessageType.WARNING, "round #"+roundID.toString()+" could not be found");
		}
		
		
		doGet(request, response);
	}

}
