//package com.pajiniweb.oriachad_backend.datainitializers;//package com.pajiniweb.oriachad_backend.datainitializers;
//
//
//import com.pajiniweb.oriachad_backend.actions.domains.entities.Action;
//import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionResultType;
//import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionType;
//import com.pajiniweb.oriachad_backend.actions.repositories.ActionRepository;
//import com.pajiniweb.oriachad_backend.domains.entities.GuidanceSpeciality;
//import com.pajiniweb.oriachad_backend.domains.entities.TechnicalCard;
//import com.pajiniweb.oriachad_backend.domains.enums.GuidanceSpecialityType;
//import com.pajiniweb.oriachad_backend.repositories.*;
//import com.pajiniweb.oriachad_backend.settings.configuration.AppSettings;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component()
//@Order(2)
//@AllArgsConstructor
//@Transactional
//public class DataInitializer implements ApplicationRunner {
//
//
//	private final AppSettings appSettings;
//
//private SpecialityRepository specialityRepository;
//	private GuidanceSpecialityRepository guidanceSpecialityRepository;
//
//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//
//		specialityRepository.findAll().forEach(speciality -> {
//			guidanceSpecialityRepository.save(GuidanceSpeciality.builder()
//							.type(GuidanceSpecialityType.speciality)
//							.title(speciality.getTitle())
//							.speciality(speciality)
//							.establishmentType(speciality.getType())
//
//
//					.build());
//
//
//		});
//
//	}
//
//
//}
