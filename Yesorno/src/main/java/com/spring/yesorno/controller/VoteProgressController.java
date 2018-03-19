package com.spring.yesorno.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.yesorno.service.VoteProgressService;

@Controller
@RequestMapping("/voteprogresses")
public class VoteProgressController {

	@Autowired VoteProgressService voteProgressService;
	
	// [C]POST: voteprogresses/{boardId}/{isAgree}
	@RequestMapping(value = "/{boardId}/{agreeOrDisagree}", method = RequestMethod.POST)
	public @ResponseBody String vote(@CookieValue(value = "memberToken", required = false) String memberToken, @PathVariable("boardId") int boardId, @PathVariable("agreeOrDisagree") String agreeOrDisagree) {
		ArrayList<String> result = new ArrayList<String>();

		voteProgressService.vote(memberToken, boardId, agreeOrDisagree, result);

		return result.get(0);
	}
}
