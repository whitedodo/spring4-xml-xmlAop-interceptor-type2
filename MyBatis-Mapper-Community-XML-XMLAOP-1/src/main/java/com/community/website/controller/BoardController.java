/*
 * 작성일자(Create Date): 2020-10-15
 * 프로젝트명(Project Name): Community Project
 * 저자(Author): Dodo / rabbit.white at daum dot net
 * 파일명(FileName): BoardController.java
 * 비고(Description):
 * 
 */

package com.community.website.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.community.website.logic.Paging;
import com.community.website.service.BoardService;
import com.community.website.vo.BoardVO;

@Controller
@RequestMapping(value = "/board/")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private final String UPLOAD_PATH = "c:" + File.separator + "temp" + File.separator;
	
	@Autowired
	@Qualifier("boardService")
    private BoardService boardService;
	
	@RequestMapping(value = "sample/{boardname}", method = RequestMethod.GET)
	public ModelAndView sample(HttpServletRequest req, 
			  				HttpServletResponse res,
			  				@PathVariable("boardname")String boardname) {

		ModelAndView mav = new ModelAndView();
		logger.info("Welcome Board! - Sample DB 입력시키기");

		boardService.insertSample(boardname);
		
		mav.setViewName("board/list");
		return mav;
	}
	
	
	@RequestMapping(value = "list/{boardname}", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest req, 
			  				HttpServletResponse res,
			  				@PathVariable("boardname")String boardname) {

		logger.info("Welcome Board! List(목록)");
		ModelAndView mav = new ModelAndView();

		long currentPage = 1;
		long pageSize = 10;
		long totalCount = -1;
		long startNum = -1, endNum = -1;
		
		String pageUrl = req.getContextPath() + "/board";
        Paging paging = new Paging();
		
		totalCount = boardService.selectTotalCount(boardname);
		
		if ( totalCount == -1) {
			// mav.setViewName("redirect:/myhome/main");
			mav.setViewName("home");
			return mav;
		}
		
		if ( req.getParameter("page") != null )
			currentPage = Long.valueOf( req.getParameter("page") );

        paging.setPageNo(currentPage);
        paging.setPageSize(pageSize);
        paging.setTotalCount(totalCount);

        // totalCount 호출시 생성됨.
        startNum = paging.getDbStartNum();
        endNum = paging.getDbEndNum();
        
		List<BoardVO> listVO = boardService.selectPagingAll(boardname, startNum, endNum);

		mav.addObject("pageUrl", pageUrl);
		mav.addObject("boardname", boardname);
        mav.addObject("paging", paging);
		mav.addObject("pagingUrl", pageUrl + "/list/" + boardname);
		mav.addObject("boardList", listVO);
		
		mav.setViewName("board/list");
		
		return mav;
		
	}
	
	@RequestMapping(value = "view/{boardname}", method = RequestMethod.GET)
	public ModelAndView view(HttpServletRequest req, 
			  				HttpServletResponse res,
			  				@PathVariable("boardname")String boardname) {

		logger.info("Board:: View");
		ModelAndView mav = new ModelAndView();
		long pageId = -1;
		
		if ( req.getParameter("page") != null )	
			pageId = Long.valueOf( req.getParameter("page") );
		else {
			mav.setViewName("home");
			return mav;
		}
		
		String pageUrl = req.getContextPath() + "/board";

		BoardVO view = boardService.select(boardname, pageId);
		
		mav.addObject("boardTitle", "게시판 보기");
		mav.addObject("pageUrl", pageUrl);
		mav.addObject("pageid", pageId);
		mav.addObject("boardname", boardname);
		mav.addObject("boardView", view);
		mav.setViewName("board/view");
			  				
		return mav;
			  			
	}

	@RequestMapping(value = "write/{boardname}", method = RequestMethod.GET)
	public ModelAndView write(HttpServletRequest req, 
			  				HttpServletResponse res,
			  				@PathVariable("boardname")String boardname) {

		logger.info("Board:: Write");
		ModelAndView mav = new ModelAndView();
		
		String pageUrl = req.getContextPath() + "/board";
		System.out.println(req.getParameter("author"));
		
		mav.addObject("boardTitle", "게시판 글쓰기");
		mav.addObject("pageUrl", pageUrl);
		mav.addObject("boardname", boardname);
		mav.setViewName("board/write");
			  				
		return mav;
			  			
	}

	@RequestMapping(value = "write_ok/{boardname}", method = RequestMethod.POST)
	public ModelAndView write_ok(HttpServletRequest req, 
			  						 HttpServletResponse res,
			  				@PathVariable("boardname")String boardname,
			  				@RequestParam("mediaFile") MultipartFile[] files) throws IOException{
		
		logger.info("Board:: MultipartFile - Write");
		ModelAndView mav = new ModelAndView();
		
		String pageUrl = req.getContextPath() + "/board";
		
		// Save mediaFile on system
		for (MultipartFile file : files) {
			
			if ( !file.isEmpty() ) {
				saveFile(file);
			}
		}
		
		mav.addObject("msg", "Multiple files uploaded successfully.");
		mav.addObject("boardTitle", "게시판 글쓰기");
		mav.addObject("pageUrl", pageUrl);
		mav.addObject("boardname", boardname);
		mav.setViewName("redirect:/board/list/" + boardname);
			  				
		return mav;
			  			
	}
	
	private String saveFile(MultipartFile file) throws IOException{
		
		// 파일 이름 변경
	    UUID uuid = UUID.randomUUID();
	    String saveName = uuid + "_" + file.getOriginalFilename();
	
	    logger.info("saveName: {}",saveName);

        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        long filesize = file.getSize();
	    
	    // 저장할 File 객체를 생성
	    File saveFile = new File(UPLOAD_PATH, saveName); // 저장할 폴더 이름, 저장할 파일 이름
	
	    try {
	        file.transferTo(saveFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	
	    return saveName;
	}
	
}
