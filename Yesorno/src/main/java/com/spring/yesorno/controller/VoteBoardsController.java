package com.spring.yesorno.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.yesorno.command.VoteBoardWriteCmd;
import com.spring.yesorno.service.VoteBoardService;
import com.spring.yesorno.validator.VoteBoardWriteValidator;

@Controller
@RequestMapping("/boards/voteboards")
public class VoteBoardsController {

	@Autowired VoteBoardService voteBoardService; 

	@Autowired VoteBoardWriteValidator voteBoardWriteValidator;
	
	// 투표 게시글 보기
	@RequestMapping(value = "/{boardId}",  method = RequestMethod.GET)
	public String voteBoardRead(@CookieValue("memberToken") String memberToken, HttpServletRequest request, @PathVariable("boardId") int boardId, Model model) {
		final String okURL = "boards/voteboards";
		final String errURL = "boards/voteboards/list";
		String resultURL = errURL;

		if (true/*voteBoardService.voteBoardListing(request, page, model)*/) {
			resultURL = okURL;
		} else {
			resultURL = errURL;
		}
		
		//model.addAttribute("voteBoardRead", class);
		
		return resultURL;
	}
	
	// 투표 게시글 리스트
	@RequestMapping(value = "/list/{page}", method = RequestMethod.GET)
	public String voteBoardListByPage(HttpServletRequest request, @PathVariable("page") int page, Model model) {
		final String okURL = "boards/voteboards/list";
		final String errURL = okURL;
		String resultURL = errURL;

		if (voteBoardService.voteBoardListing(request, page, model)) {
			resultURL = okURL;
		} else {
			resultURL = errURL;
		}
		
		return resultURL;
	}
	
	// 투표 게시글 쓰기
	@RequestMapping(method = RequestMethod.POST)
	public String voteBoardWrite(HttpServletRequest request, @CookieValue("memberToken") String memberToken, @ModelAttribute("voteBoardWriteCmd") VoteBoardWriteCmd cmd, Errors errors/*, @RequestParam("thumbnailImage") MultipartFile img*/) {
		final String okURL = "redirect:/boards/voteboards/list/1";
		final String errURL = "boards/voteboards/write";
		String resultURL = errURL;

		voteBoardWriteValidator.validate(cmd, errors);
		if (errors.hasErrors()) {
			resultURL = errURL;
		} else {
			try {
				if (voteBoardService.voteBoardWrite(memberToken, request, cmd, errors) && !errors.hasErrors()) {
					resultURL = okURL; // Okay
				} else {
					resultURL = errURL; // Error
				}
			} catch (Exception e) {
				// 임시...
				e.printStackTrace();
			}
		}
		
		return resultURL;
	}
	
	//////////	//////////	//////////	//////////	//////////	//////////	//////////	
	
	// 투표 게시글 쓰기 페이지
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String voteBoardWritePage(@ModelAttribute("voteBoardWriteCmd") VoteBoardWriteCmd cmd, Errors errors) {
		return "boards/voteboards/write";
	}
}
