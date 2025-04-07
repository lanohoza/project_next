package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.entities.Establishment;
import com.pajiniweb.oriachad_backend.domains.entities.Year;
import com.pajiniweb.oriachad_backend.repositories.EstablishmentRepository;
import com.pajiniweb.oriachad_backend.repositories.YearRepository;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service

public class HelperService {
	@Autowired
	EstablishmentRepository establishmentRepository;
	@Autowired
	YearRepository yearRepository;

	public Establishment getCurrentEstablishment() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
		return establishmentRepository.findById(userDetails.getOriachadUser().getIdEstablishment()).orElseThrow();
	}

	public CustomUserDetails getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
		return userDetails;
	}



	public String getCurrentEstablishmentTypeName(Establishment establishment) {

		switch (establishment.getType()) {
			case primary:
				return "إبتدائي";
			case middle:
				return "متوسط";
			case secondary:
				return "ثانوي";
		}
		return "";
	}
@Cacheable()
	public Year getCurrentYear() {
		return yearRepository.findFirstByCurrentIsTrue().orElseThrow();
	}
}
