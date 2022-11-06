package com.tpe.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpe.domain.AppLog;
import com.tpe.repository.LogRepository;

@Service
public class LogService {
	
	@Autowired
	private LogRepository logRepository;
	
	public void saveLog(AppLog appLog) { 
		logRepository.save(appLog);
	}
	
	public List<AppLog> getAll(LocalDateTime startDateTime, LocalDateTime endDateTime) { 
		return logRepository.findByTimeBetweenOrderByTimeDesc(startDateTime,endDateTime);
	}

}
