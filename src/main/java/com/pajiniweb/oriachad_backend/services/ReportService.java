package com.pajiniweb.oriachad_backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);

	final HelperService helperService;

	@Autowired
	public ReportService(HelperService helperService) {
		this.helperService = helperService;
	}


}
