package com.pajiniweb.oriachad_backend.actions.services;

import com.pajiniweb.oriachad_backend.actions.repositories.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionService {

	final ActionRepository actionRepository;

	@Autowired
	public ActionService(ActionRepository actionRepository) {
		this.actionRepository = actionRepository;
	}



}
