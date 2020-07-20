package hd.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import hd.board.DAO.BoardDAO;
import hd.board.action.Action;
import hd.board.command.ActionCommand;
import hd.board.model.BoardVO;

public class BoardReplyService implements Action {

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionCommand actionCommand = new ActionCommand();
		BoardDAO boardDAO = new BoardDAO();
		BoardVO boardVO = new BoardVO();
		int result = 0;
		String realFolder = "";
		String saveFolder = "./boardUpload";
		int fileSize = 10 * 1024 * 1024;
		realFolder = request.getSession().getServletContext().getRealPath(saveFolder);
		try {
			MultipartRequest multipartRequest = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
			boardVO.setNum(Integer.parseInt(multipartRequest.getParameter("num")));
			boardVO.setName(multipartRequest.getParameter("name"));
			boardVO.setPass(multipartRequest.getParameter("pass"));
			boardVO.setSubject(multipartRequest.getParameter("subject"));
			boardVO.setContent(multipartRequest.getParameter("content"));
			boardVO.setAnswer_num(Integer.parseInt(multipartRequest.getParameter("answer_num")));
			boardVO.setAnswer_lev(Integer.parseInt(multipartRequest.getParameter("answer_lev")));
			boardVO.setAnswer_seq(Integer.parseInt(multipartRequest.getParameter("answer_seq")));
			boardVO.setAttached_file(multipartRequest.getFilesystemName((String)multipartRequest.getFileNames().nextElement()));
			result = boardDAO.boardReply(boardVO);
			if (result == 0) {
				System.out.println("답변 실패");
				return null;
			}
			System.out.println("답변 성공");
			actionCommand.setRedirect(true);
			actionCommand.setPath("./BoardDetail.do?num=" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionCommand;
	}

}
