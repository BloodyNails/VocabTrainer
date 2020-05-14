package com.bloodynails;

import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;

import com.bloodynails.logging.Logger;
import com.bloodynails.logging.MessageType;


// TODO: timer is not running yet. it only measures time between start and stop
public class VocabTimer {
	// in this class time is used in milliseconds! 
	// so VocabTimer(1000L) creates a timer with 1s
	private boolean playing;
	private Long currTime;
	private Date startDate;
	private Long startTime;
	
	
	public VocabTimer(Long time) {
		if(time < 0) throw new IllegalArgumentException("time of timer must be equal to or greater than 0");
		this.currTime = time;
		this.playing = false;
	}
	
	public float getCurrTime() {
		return this.currTime;
	}

	public boolean isPlaying() {
		return playing;
	}
	
	public void continueTimer() {
		if(playing) return;
		playing = true;
		startDate = new Date(Instant.now().toEpochMilli());
		Long milliseconds = LocalDateTime.now().getNano() / 1000000L;
		startTime = startDate.getTime() + milliseconds;
		if(currTime > 0) Logger.log(MessageType.DEBUG, "continuing timer at: "+startDate.toString());
		else Logger.log(MessageType.DEBUG, "starting timer at: "+startDate.toString());
	}
	
	public void stop() {
		if(!playing) return;
		playing = false;
		Date nowDate = new Date(Instant.now().toEpochMilli());
		Long milliseconds = LocalDateTime.now().getNano() / 1000000L;
		Long nowTime = nowDate.getTime() + milliseconds; 
		currTime += (nowTime - startTime);
		Logger.log(MessageType.DEBUG, "stopping timer at: " + nowDate.toString());
		Logger.log(MessageType.DEBUG, "stopping after: " + currTime + "ms");
	}
}
