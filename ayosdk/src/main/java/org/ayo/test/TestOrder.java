package org.ayo.test;



/**
 *
 */
public class TestOrder{
	public String title = "";
	public String cover_url;

	public String getUITitle(){
		if(title.length() <= 50){
			return title;
		}else{
			return title.substring(0, 49)+"...";
		}
	}
	
}
