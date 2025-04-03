package com.polsl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polsl.entity.Competitor;
import com.polsl.repository.CompetitorRepository;

@Controller
@RequestMapping("/competitor")
public class CompetitorController {

	@Autowired
	CompetitorRepository competitorRepo;
	
	@PostMapping("/add")
	public @ResponseBody String addCompetitor(@RequestBody Competitor competitor) {
		competitor = competitorRepo.save(competitor);
		return "Added with id=" + competitor.getCompetitorId();
	}
}
