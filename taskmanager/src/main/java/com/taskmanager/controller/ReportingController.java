package com.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.service.ReportingService;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {
    
    @Autowired
	private ReportingService reportingService;

    @GetMapping("/burnDown/{sprintId}")
	public ResponseEntity<?> burnDown(@PathVariable Long sprintId){
		return ResponseEntity.ok(reportingService.burndown(sprintId));
	}
	
	@GetMapping("/velocity/{projectId}")
	public ResponseEntity<?> velocity(@RequestParam Long projectId){
		return ResponseEntity.ok(reportingService.velocity(projectId));
	}
	
	@GetMapping("/sprintReport/{sprintId}")
	public ResponseEntity<?> sprintReport(@RequestParam Long sprintId){
		return ResponseEntity.ok(reportingService.sprintRepot(sprintId));
	}
	
	@GetMapping("/epicReport/{epicId}")
	public ResponseEntity<?> epicReport(@RequestParam Long epicId){
		return ResponseEntity.ok(reportingService.epicReport(epicId));
	}
	
	@GetMapping("/workLoadReport/{sprintId}")
	public ResponseEntity<?> workLoadReport(@RequestParam Long sprintId){
		return ResponseEntity.ok(reportingService.workLoad(sprintId));
	}
	
	@GetMapping("/cumultaive-flow/{sprintId}")
	public ResponseEntity<?> cumultaiveFlow(@PathVariable Long sprintId){
		return ResponseEntity.ok(reportingService.cumulativeFlow(sprintId));
	}

}
