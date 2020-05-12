package com.bloodynails;

public class Config {
	
	private static final String homePath = "/VocabTrainer";
	
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
	
	private static final String dbName = "vocabtrainer";
	
	public static final String dbTableLists = dbName + "." + "lists";
	public static final String dbTableWords = dbName + "." + "words";
	public static final String dbTableRounds = dbName + "." + "rounds";
	public static final String dbTableCycles = dbName + "." + "cycles";
	public static final String dbTableTWords = dbName + "." + "twords";
	
	// make this secure in the future
	private static final String dbUrlPrefix = "jdbc:mysql://localhost:3306/" + dbName;
	private static final String dbUrlSuffix = "?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
	public static final String dbUrl = dbUrlPrefix + dbUrlSuffix;

	public static final String dbUser = "root";
	public static final String dbPass = "pass";
	
}
