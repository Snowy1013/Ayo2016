package org.ayo.a_unknown;

public class BasePresenter<T> {
	
	protected T view;
	
	public void bind(T view){
		this.view = view;
	}
	
}
