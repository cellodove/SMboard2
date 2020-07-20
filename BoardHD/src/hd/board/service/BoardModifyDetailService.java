package hd.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hd.board.DAO.BoardDAO;
import hd.board.action.Action;
import hd.board.command.ActionCommand;
import hd.board.model.BoardVO;

public class BoardModifyDetailService implements Action {

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionCommand actionCommand = new ActionCommand();
		BoardDAO boardDAO = new BoardDAO();
		BoardVO boardVO = new BoardVO();
		int num = Integer.parseInt(request.getParameter("num"));
		boardVO = boardDAO.getDetail(num);
		if (boardVO == null) {
			System.out.println("(수정)상세보기 실패");
			return null;
		}
		System.out.println("(수정)상세보기 성공");
		request.setAttribute("boardVO", boardVO);
		actionCommand.setRedirect(false);
		actionCommand.setPath("./board/boardModify.jsp");
		return actionCommand;
	}

}
