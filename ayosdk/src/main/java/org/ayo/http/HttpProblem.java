package org.ayo.http;

public enum HttpProblem {
	
	/** ok */
	OK, 
	/** not on the internet */
	OFFLINE, 
	/** http code is 300 400 or 500 */
	SERVER_ERROR, 
	/** http code is 200, but app code is not ok, or local code process failed */
	DATA_ERROR, 
	/** don't know */
	UNKNOWN
}
