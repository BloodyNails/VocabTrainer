package com.bloodynails;

public class Config {
	
	public static final String homePath = "/VocabTrainer";
	
	public static final String internalListsPath = "/Lists";
	public static final String internalListsDetailViewPath = "/Lists/View";
	public static final String internalTrainingPath = "/Training";
	public static final String internalVocabularyPath = "/Training/Vocabulary";
	public static final String internalCreateRoundPath = "/Training/Vocabulary/create";
	public static final String internalDeleteRoundPath = "/Training/Vocabulary/delete";
	public static final String internalContinueRoundPath = "/Training/Vocabulary/continue";
	
	public static final String externalListsPath = homePath + internalListsPath;
	public static final String externalListsDetailViewPath = homePath + internalListsDetailViewPath;
	public static final String externalTrainingPath = homePath + internalTrainingPath;
	public static final String externalVocabularyPath = homePath + internalVocabularyPath;
	public static final String externalCreateRoundPath = homePath + internalCreateRoundPath;
	public static final String externalDeleteRoundPath = homePath + internalDeleteRoundPath;
	public static final String externalContinueRoundPath = homePath + internalContinueRoundPath;
	
	public static final String logsPath = "";
}
