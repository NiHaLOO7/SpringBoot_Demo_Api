package com.fs.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fs.demo.dao.AlienRepo;
import com.fs.demo.model.Alien;

@RestController
public class AlienController {
	
	@Autowired
	AlienRepo repo;
	
	@RequestMapping("home")
	public String home(){
		return "home.jsp";
	}
	
	@DeleteMapping("/aliens/{aid}")
	public String deleteAlien(@PathVariable int aid) {
		Alien a = repo.getById(aid);
		repo.delete(a);
		return "deleted";
	}
	
	@RequestMapping("/addAlien")
	public String addAlienToDb(Alien alien)
	{
		repo.save(alien);
		return "home.jsp";
	}
	
	
	@RequestMapping(path="/aliens" /*,produces = {"application/xml"}, methodType*/ )
	//@ResponseBody //or use restController
	public List<Alien> getAllAlien()
	{
		return repo.findAll();
	}
	
	@PostMapping(path="/aliens", consumes= {"application/json"})
	public Alien addAlien(@RequestBody Alien alien)
	{
		repo.save(alien);
		return alien;
	}
	
	@PutMapping(path="/aliens", consumes= {"application/json"})
	public Alien upsertAlien(@RequestBody Alien alien)
	{
		repo.save(alien);
		return alien;
	}
	
	@RequestMapping("/aliens/{aid}")
	//@ResponseBody
	public Optional<Alien> getAlienById(@PathVariable("aid") int aid)
	{
		return repo.findById(aid);
	}
	
	
	@RequestMapping("/getAlien")
	public ModelAndView getAlien(@RequestParam int aid)
	{
		ModelAndView mv = new ModelAndView("showAliens.jsp");
		Alien alien = repo.findById(aid).orElse(new Alien());
		mv.addObject(alien);
		System.out.println(repo.findByTech("java"));
		System.out.println(repo.findByAidGreaterThan(100));
		System.out.println(repo.findByTechSorted("java"));
		return mv;
	}

}
