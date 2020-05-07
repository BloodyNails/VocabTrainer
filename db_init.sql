			
	drop database vocabtrainer;
	create database vocabtrainer;
	use vocabtrainer;

	CREATE TABLE lists(
		list_id INT UNSIGNED NOT NULL,
		description CHAR(100) CHARACTER SET utf8mb4 NOT NULL,
		lang1 VARCHAR(60) NOT NULL,
		lang2 VARCHAR(60) NOT NULL,
		PRIMARY KEY (list_id)
	);
		
	CREATE TABLE words(
		word_id INT UNSIGNED NOT NULL,
		list_id INT UNSIGNED NOT NULL,
		word_lang1 CHAR(100) CHARACTER SET utf8mb4 NOT NULL,
		word_lang2 CHAR(100) CHARACTER SET utf8mb4 NOT NULL,
		PRIMARY KEY (word_id),
		FOREIGN KEY (list_id) REFERENCES lists(list_id)
	);	
		
	CREATE TABLE rounds(
		round_id INT UNSIGNED NOT NULL,
		completed boolean NOT NULL,
		list_ids VARCHAR(100) NOT NULL,
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
		curr_tword_id INT UNSIGNED NOT NULL,
		true_count INT UNSIGNED NOT NULL,
		false_count INT UNSIGNED NOT NULL,
		tf_ratio FLOAT NOT NULL,
		time FLOAT NOT NULL,
		PRIMARY KEY (cycle_id),
		FOREIGN KEY (round_id) REFERENCES rounds(round_id)
	);
	
	CREATE TABLE twords (
		tword_id INT UNSIGNED NOT NULL,
		word_id INT UNSIGNED NOT NULL,
		cycle_id INT UNSIGNED NOT NULL,
		is_prompted BOOLEAN NOT NULL,
		is_correct BOOLEAN,
        PRIMARY KEY (tword_id),
		FOREIGN KEY (word_id) REFERENCES words(word_id),
        FOREIGN KEY (cycle_id) REFERENCES cycles(cycle_id)
	);
	
    -- insert test entries:
	
	INSERT INTO lists (list_id, description, lang1, lang2) VALUES ('999', 'Familie', 'German', 'Russian');
	
	INSERT INTO words (word_id, list_id, word_lang1, word_lang2) VALUES ('0', '999', N'Großvater', N'де́душка');
	INSERT INTO words (word_id, list_id, word_lang1, word_lang2) VALUES ('1', '999', N'Großmutter', N'ба́бушка');
	INSERT INTO words (word_id, list_id, word_lang1, word_lang2) VALUES ('2', '999', N'Mutter', N'мать');
	INSERT INTO words (word_id, list_id, word_lang1, word_lang2) VALUES ('3', '999', N'Vater', N'оте́ц');
	INSERT INTO words (word_id, list_id, word_lang1, word_lang2) VALUES ('4', '999', N'Bruder', N'брат');
	INSERT INTO words (word_id, list_id, word_lang1, word_lang2) VALUES ('5', '999', N'Schwester', N'сестра́');
	
	INSERT INTO rounds (round_id, completed, list_ids, lang1, lang2, prompted_lang, time, true_count, false_count, tf_ratio) VALUES ('0', '0', '999', 'German', 'Russian', 'German', '0.0', '0', '0', '1.0');