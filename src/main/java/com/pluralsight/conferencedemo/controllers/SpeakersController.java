package com.pluralsight.conferencedemo.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {

	@Autowired
	private SpeakerRepository speakerRepository;
	
	@GetMapping
	public List<Speaker> list(){
		return speakerRepository.findAll();
	}
	
	@GetMapping
	@RequestMapping("{id}")
	public Speaker get(@PathVariable Long id){
		return speakerRepository.getOne(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Speaker create(@RequestBody final Speaker Speaker){
		return speakerRepository.saveAndFlush(Speaker);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		speakerRepository.deleteById(id);
	}
	
	@RequestMapping(value =  "{id}", method = RequestMethod.PUT)
	public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
		
		//TODO: Add validation that all attributes are passed in, otherwise return 400 bad payload
		Speaker existingspeaker = speakerRepository.getOne(id);
		BeanUtils.copyProperties(speaker, existingspeaker, "speaker_id");
		return speakerRepository.saveAndFlush(existingspeaker);
	}
	
}
