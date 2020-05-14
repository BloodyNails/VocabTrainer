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
import com.bloodynails.VocabTWord;
import com.bloodynails.VocabTimer;
import com.bloodynails.VocabWord;
import com.bloodynails.database.DBManager;
import com.bloodynails.logging.Logger;
import com.bloodynails.logging.MessageType;

@WebServlet(com.bloodynails.Config.internalContinueRoundPath)
public class ContinueRound extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static VocabCycle currentCycle;
	private static VocabRound currentRound;
	private static VocabTWord currentTWord;
	private static String backPath = Config.externalVocabularyPath;
       
    public ContinueRound() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(currentRound == null) {
			Logger.log(MessageType.WARNING, "round not specified");
			response.sendRedirect(Config.externalVocabularyPath);
			return;
		}
		LinkedList<VocabCycle> cycles = currentRound.getCycles();
		if(cycles == null) {
			Logger.log(MessageType.ERROR, "round"+currentRound.getID()+".getCycles() returned null");
			response.sendRedirect(Config.externalVocabularyPath);
			return;
		}
		
		// get the last cycle or create a new one
		if(cycles.size() < 1) {
			Long cycleID = DBManager.getNextCycleID();
			Long roundID = currentRound.getID();
			boolean completed = false;
			int wordCount = 0;
			Long currTWordID = -1L;
			int trueCount = 0;
			int falseCount = 0;
			float tfRatio = 1f;
			Long time = 0L;
			
			// wordCount: 
			// - the count of words which are prompted in this cycle
			// - can be different from the last cycle
			// - correct answered words will be marked as correct and will not be in the next cycle
			// - in this case there are no other cycles yet so we can iterate over the lists and count the words
			LinkedList<VocabList> lists = DBManager.getListsByRoundID(currentRound.getID());
			if(lists == null) {
				Logger.log(MessageType.ERROR, "getListsByRoundID("+currentRound.getID()+") returned null");
				response.sendRedirect(Config.externalVocabularyPath);
				return;
			}
			if(lists.size() < 0) {
				Logger.log(MessageType.ERROR, "getListsByRoundID("+currentRound.getID()+") returned an empty list of lists");
				response.sendRedirect(Config.externalVocabularyPath);
				return;
			}
			for(int i = 0; i < lists.size(); i++) {
				if(lists.get(i).getWords() == null) {
					Logger.log(MessageType.ERROR, "list"+lists.get(i).getID()+".getWords() returned null");
					response.sendRedirect(Config.externalVocabularyPath);
					return;
				}
				wordCount += lists.get(i).getWords().size();
			}
			if(wordCount < 1) {
				// wordCount must be >= 1
				Logger.log(MessageType.ERROR, "wordCount of cycle #"+currentCycle.getID()+" is 0 after counting words");
				response.sendRedirect(Config.externalVocabularyPath);
				return;
			}
			
			VocabWord word = lists.getFirst().getWords().getFirst();
			if(word == null) {
				Logger.log(MessageType.ERROR, "list"+lists.getFirst().getID()+".getWords().getFirst() returned null");
				response.sendRedirect(Config.externalVocabularyPath);
				return;
			}
			VocabTWord tWord = new VocabTWord(word, cycleID, false, false);
			currTWordID = tWord.getID();
			if(currTWordID < 0) {
				Logger.log(MessageType.ERROR, "currTWordID of cycle #"+currentCycle.getID()+" must be equal to or greater than 0");
				response.sendRedirect(Config.externalVocabularyPath);
				return;
			}
			
			currentCycle = new VocabCycle(cycleID, roundID, completed, wordCount, currTWordID, trueCount, falseCount, tfRatio, time);
			Logger.log(MessageType.DEBUG, "new cycle:\n"+currentCycle.toString());
			
			// save cycle AND tWord
			if(!DBManager.save(currentCycle)) {
				Logger.log(MessageType.ERROR, "cycle #"+currentCycle.getID()+" could not be saved");
				response.sendRedirect(Config.externalVocabularyPath);
				return;
			} else if(!DBManager.save(tWord)) {
				Logger.log(MessageType.ERROR, "tWord #"+tWord.getID()+" could not be saved");
				response.sendRedirect(Config.externalVocabularyPath);
				return;
			}
		}
		else {
			currentCycle = currentRound.getCycles().getLast();
			if(currentCycle.isCompleted()) {
				// TODO: currentCycle = new Cycle();
			}
			Logger.log(MessageType.DEBUG, "last cycle of round #"+currentRound.getID()+" cycle:\n"+currentCycle.toString());
		}
		
		LinkedList<VocabTWord> tWords = currentCycle.getTWords();
		if(tWords == null) {
			Logger.log(MessageType.ERROR, "cycle"+currentCycle.getID()+".getTWords() returned null");
			response.sendRedirect(Config.externalVocabularyPath);
			return;
		}
		if(tWords.size() < 1) {
			Logger.log(MessageType.ERROR, "cycle"+currentCycle.getID()+".getTWords() has no tWords in it");
			response.sendRedirect(Config.externalVocabularyPath);
			return;
		}
		
		// starting / continuing the round
		VocabTimer timer = currentCycle.getTimer();
		timer.continueTimer();
		for(int i = 0; i < tWords.size(); i++) {
			currentTWord = tWords.get(i);
			VocabWord word = currentTWord.getWord();
			if(word == null) {
				Logger.log(MessageType.ERROR, "tWord"+currentTWord.getID()+".getWord() returned null");
				response.sendRedirect(Config.externalVocabularyPath);
				return;
			}
			
			String lang1Str = word.getWordLang1();
			String lang2Str = word.getWordLang2();
			
			Logger.log(MessageType.DEBUG, "lang1: "+lang1Str+", lang2: "+lang2Str);
		}
		
		timer.stop();
		response.sendRedirect(backPath);
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
