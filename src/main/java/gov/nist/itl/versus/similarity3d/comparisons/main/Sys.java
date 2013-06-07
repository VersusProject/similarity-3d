package gov.nist.itl.versus.similarity3d.comparisons.main;

/**
 * This software was developed at the National Institute of Standards and
 * Technology by employees of the Federal Government in the course of
 * their official duties. Pursuant to title 17 Section 105 of the United
 * States Code this software is not subject to copyright protection and is
 * in the public domain. This software is an experimental system. NIST assumes
 * no responsibility whatsoever for its use by other parties, and makes no
 * guarantees, expressed or implied, about its quality, reliability, or
 * any other characteristic. We would appreciate acknowledgment if the
 * software is used.
 * 
 *  @author  B. Long
 *  @version 1.0
 *    
 */

/*
 * A general class for encapsulating all host system-specific functionality or information access.
 */
public class Sys 
{
	private long startTime;
	private long stopTime;
	private boolean timerRunning;
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - -
	// timing methods, for capturing fx execution duration 
	// - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
		public void beginTiming()
		{
			resetTimer();
			startTimer();
		}
		
		public String endTiming() {
			stopTimer();
			return "" + getElapsedTime();
		}
		
		public void resetTimer() {
			startTime = 0;
			stopTime  = 0;
			timerRunning = false;
		}
		
		public void startTimer() {
			startTime = System.currentTimeMillis();
			timerRunning = true;
		}
		
		public void stopTimer() {
			stopTime = System.currentTimeMillis();
			timerRunning = false;
		}
		
		public long getElapsedTime() 
		{
			long elapsedTime = 0;
			if ( timerRunning ) {
				elapsedTime = (System.currentTimeMillis() - startTime);
			}
			else {
				elapsedTime = (stopTime - startTime);
			}
			return elapsedTime;
		}	
	
}
