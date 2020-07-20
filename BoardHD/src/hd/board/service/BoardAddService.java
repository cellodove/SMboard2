package hd.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import hd.board.DAO.BoardDAO;
import hd.board.action.Action;
import hd.board.command.ActionCommand;
import hd.board.model.BoardVO;

public class BoardAddService implements Action{

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardDAO boardDAO = new BoardDAO();
		BoardVO boardVO = new BoardVO();
		ActionCommand actionCommand = new ActionCommand();
		String realFolder = "";
		String saveFolder = "./boardUpload";
		realFolder = request.getSession().getServletContext().getRealPath(saveFolder);
		int fileSize = 10 * 1024 * 1024;
		boolean result = false;
		try {
			MultipartRequest multipartRequest = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
			boardVO.setName(multipartRequest.getParameter("name"));
			boardVO.setPass(multipartRequest.getParameter("pass"));
			boardVO.setSubject(multipartRequest.getParameter("subject"));
			boardVO.setContent(multipartRequest.getParameter("content"));
			boardVO.setAttached_file(multipartRequest.getFilesystemName((String)multipartRequest.getFileNames().nextElement()));
			result = boardDAO.boardInsert(boardVO);
			if (result == false) {
				System.out.println("게시판 등록 실패^^");
				return null;
			}
			System.out.println("게시판 등록 완");
			actionCommand.setRedirect(true);
			actionCommand.setPath("./BoardList.do");
			return actionCommand;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
