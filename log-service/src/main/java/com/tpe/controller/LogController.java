package com.tpe.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tpe.domain.AppLog;
import com.tpe.dto.AppLogDTO;
import com.tpe.enums.AppLogLevel;
import com.tpe.service.LogService;

@RestController
@RequestMapping("/log")
public class LogController {

	@Autowired
	private LogService logService;
	
	@PostMapping
	public ResponseEntity<String> createLog(@RequestBody AppLogDTO appLogDTO){
		AppLog appLog=new AppLog();
		appLog.setLevel(AppLogLevel.fromString(appLogDTO.getLevel()));
		appLog.setDescription(appLogDTO.getDescription());
		appLog.setTime(appLogDTO.getTime());
		
		logService.saveLog(appLog);
		
		return new ResponseEntity<>(appLog.getId(), HttpStatus.CREATED);
	}
	
	
	@GetMapping
	public ResponseEntity<List<AppLogDTO>> getAll(@RequestParam("startDateTime") @DateTimeFormat(pattern="MM/dd/yyyy HH:mm:ss") LocalDateTime startTime,
	@RequestParam("endDateTime") @DateTimeFormat(pattern="MM/dd/yyyy HH:mm:ss") LocalDateTime endTime){
		List<AppLog> list= logService.getAll(startTime, endTime);
		
		List<AppLogDTO> listDTO= list.stream().map(new Function<AppLog, AppLogDTO>() {
			
			@Override
			public AppLogDTO apply(AppLog appLog) {
				AppLogDTO dto=new AppLogDTO();
				dto.setId(appLog.getId());
				dto.setLevel(appLog.getLevel().toString());
				dto.setDescription(appLog.getDescription());
				dto.setTime(appLog.getTime());
				return dto;
			}
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok(listDTO);
	}
	
}
