package org.ayo.app.tmpl;

/**
 * 查询列表的条件，可能需要传入一堆参数，而不仅仅是page
 * @author cowthan
 *
 */
public abstract class Condition {

	public abstract void onPullDown();
	public abstract void onPullUp();
	public abstract void reset();
}
