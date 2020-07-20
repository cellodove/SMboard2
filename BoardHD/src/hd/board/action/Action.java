package hd.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hd.board.command.ActionCommand;

public interface Action {
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response)
		throws Exception;

}
