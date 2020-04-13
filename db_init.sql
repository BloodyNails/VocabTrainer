drop database vocabtrainer;
create database vocabtrainer;
use vocabtrainer;

CREATE TABLE lists(
	list_id INT UNSIGNED NOT NULL,
	description VARCHAR(180),
	lang1 VARCHAR(60) NOT NULL,
	lang2 VARCHAR(60) NOT NULL,
	PRIMARY KEY (list_id)
);
	
CREATE TABLE words(
	word_id INT UNSIGNED NOT NULL,
	list_id INT UNSIGNED NOT NULL,
	word_lang1 VARCHAR(100) NOT NULL,
	word_lang2 VARCHAR(100) NOT NULL,
	PRIMARY KEY (word_id),
	FOREIGN KEY (list_id) REFERENCES lists(list_id)
);	
	
CREATE TABLE rounds(
	round_id INT UNSIGNED NOT NULL,
	completed boolean NOT NULL,
	list_ids VARCHAR(100) NOT NULL,
	cycle_ids VARCHAR(100),
	lang1 VARCHAR(100) NOT NULL,
	lang2 VARCHAR(100) NOT NULL,
	prompted_lang VARCHAR(100) NOT NULL,
	time FLOAT NOT NULL,
	true_count INT UNSIGNED NOT NULL,
	false_count INT UNSIGNED NOT NULL,
	tf_ratio FLOAT NOT NULL,
	PRIMARY KEY (round_id)
);

CREATE TABLE cycles (
	cycle_id INT UNSIGNED NOT NULL,
	round_id INT UNSIGNED NOT NULL,
	completed BOOLEAN NOT NULL,
	word_count INT UNSIGNED NOT NULL,
	curr_word_id INT UNSIGNED NOT NULL,
	true_count INT UNSIGNED NOT NULL,
	false_count INT UNSIGNED NOT NULL,
	tf_ratio FLOAT NOT NULL,
	time FLOAT NOT NULL,
	PRIMARY KEY (cycle_id),
	FOREIGN KEY (round_id) REFERENCES rounds(round_id)
);