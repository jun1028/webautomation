package com.test.fixture;

public interface IActions {

	public boolean check(String item, String expected);

	public void input(String elementkey, String elmentvalue);

	public void click(String element);

	public void doubleClick(String element);

	public void moveOn(String elment);

	public void select(String obj);

	public void open(String url);

	public void close();

}
