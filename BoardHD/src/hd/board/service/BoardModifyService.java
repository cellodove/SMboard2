package hd.board.service;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import hd.board.DAO.BoardDAO;
import hd.board.action.Action;
import hd.board.command.ActionCommand;
import hd.board.model.BoardVO;

public class BoardModifyService implements Action {

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionCommand actionCommand = new ActionCommand();
		BoardDAO boardDAO = new BoardDAO();
		BoardVO boardVO = new BoardVO();
		boolean result = false;
		String realFolder = "";
		String saveFolder = "./boardUpload";
		int fileSize = 10 * 1024 * 1024;
		realFolder = request.getSession().getServletContext().getRealPath(saveFolder);
		try {
			MultipartRequest multipartRequest = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
			int num = Integer.parseInt(multipartRequest.getParameter("num"));
			boolean usercheck = boardDAO.isBoardWriter(num, multipartRequest.getParameter("pass"));
			if (usercheck == false) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('수정할 권한이 없습니다.')");
				out.println("location.href='./BoardList.do';");
				out.println("<script>");
				out.close();
				return null;
			}
			boardVO.setNum(num);
			boardVO.setName(multipartRequest.getParameter("name"));
			boardVO.setSubject(multipartRequest.getParameter("subject"));
			boardVO.setContent(multipartRequest.getParameter("content"));
			boardVO.setAttached_file(multipartRequest.getFilesystemName((String)multipartRequest.getFileNames().nextElement()));
			boardVO.setOld_file(multipartRequest.getParameter("old_file"));
			result = boardDAO.boardModify(boardVO);
			if (result == false) {
				System.out.println("게시판 수정 실패");
				return null;
			}
			System.out.println("게시판 수정 완료");
			actionCommand.setRedirect(true);
			actionCommand.setPath("./BoardDetail.do?num=" + boardVO.getNum());
			return actionCommand;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
