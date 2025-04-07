package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.TCE002ResultBySubject;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditAverageDto;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditNoteDto;
import com.pajiniweb.oriachad_backend.domains.dtos.AverageDto;
import com.pajiniweb.oriachad_backend.domains.dtos.NoteDto;
import com.pajiniweb.oriachad_backend.domains.dtos.ResultDto;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.exceptions.ResourceNotFoundException;
import com.pajiniweb.oriachad_backend.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {
	private static final Logger LOGGER = LoggerFactory.getLogger(NoteService.class);

	@Autowired
	private ResultRepository resultRepository;

	@Autowired
	private AverageRepository averageRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private TrimestreRepository trimestreRepository;
	@Autowired
	private SubjectLevelRepository subjectLevelRepository;

	public NoteDto findNoteByStudentAndTrimestre(Long idStudent, Long idTrimestre) {
		LOGGER.info("Start find Note By Student And Trimestre");

		try {
			List<Result> results = resultRepository.findByStudentIdAndTrimestreId(idStudent, idTrimestre);
			Average average = averageRepository.findByIdStudentAndIdTrimestre(idStudent, idTrimestre).orElseGet(() -> Average.builder().build());

			List<ResultDto> resultDtos = results.stream().map(result -> ResultDto.builder().id(result.getId()).value(result.getValue()).idSubjectLevel(result.getIdSubjectLevel()).build()).collect(Collectors.toList());

			AverageDto averageDto = AverageDto.builder().id(average.getId()).value(average.getValue()).build();

			return NoteDto.builder().results(resultDtos).average(averageDto).idStudent(idStudent).idTrimestre(idTrimestre).build();
		} catch (Exception e) {
			LOGGER.error("Error finding note by student and trimestre", e);
			throw new RuntimeException("Error finding note by student and trimestre", e);
		}
	}

	@Transactional
	public boolean saveNote(AddEditNoteDto addEditNoteDto) {
		LOGGER.info("Start save Note");

		try {
            Student student = studentRepository.findById(addEditNoteDto.getIdStudent()).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
            Trimestre trimestre = trimestreRepository.findById(addEditNoteDto.getIdTrimestre()).orElseThrow(() -> new ResourceNotFoundException("Trimestre not found"));
            addEditNoteDto.getResults().forEach(resultDto -> {
                SubjectLevel subjectLevel = subjectLevelRepository.findById(resultDto.getIdSubjectLevel()).orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
                Result result = resultRepository.findByStudentIdAndTrimestreIdAndIdSubjectLevel(student.getId(), trimestre.getId(), subjectLevel.getId()).orElseGet(() -> Result.builder().subjectLevel(subjectLevel).student(student).trimestre(trimestre).build());
                result.setValue(resultDto.getValue());
                resultRepository.save(result);
            });
            averageRepository.deleteByStudentIdAndTrimestreId(addEditNoteDto.getIdStudent(), addEditNoteDto.getIdTrimestre());
            AddEditAverageDto averageDto = addEditNoteDto.getAverage();
            Average average = Average.builder().value(averageDto.getValue()).student(student).trimestre(trimestre).build();
            averageRepository.save(average);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error saving note", e);
			throw new RuntimeException("Error saving note", e);
		}
	}

	@Transactional
	public boolean updateNote(AddEditNoteDto addEditNoteDto) {
		LOGGER.info("Start updateNote");

		try {
          /*  Student student = studentRepository.findById(addEditNoteDto.getIdStudent()).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
            Trimestre trimestre = trimestreRepository.findById(addEditNoteDto.getIdTrimestre()).orElseThrow(() -> new ResourceNotFoundException("Trimestre not found"));

            addEditNoteDto.getResults().forEach(resultDto -> {
                Result result = resultRepository.findById(resultDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Result not found"));
                Subject subject = subjectRepository.findById(resultDto.getIdSubject()).orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
                result.setValue(resultDto.getValue());
                result.setSubject(subject);
                resultRepository.save(result);
            });

            AddEditAverageDto averageDto = addEditNoteDto.getAverage();
            Average average = averageRepository.findById(averageDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Average not found"));
            average.setValue(averageDto.getValue());
            averageRepository.save(average);*/

			return true;
		} catch (Exception e) {
			LOGGER.error("Error updating note", e);
			throw new RuntimeException("Error updating note", e);
		}
	}

	@Transactional
	public boolean deleteNoteByStudentAndTrimestre(Long idStudent, Long idTrimestre) {
		LOGGER.info("Start deleteNoteByStudentAndTrimestre");

		try {
			List<Result> results = resultRepository.findByStudentIdAndTrimestreId(idStudent, idTrimestre);
			Average average = averageRepository.findByIdStudentAndIdTrimestre(idStudent, idTrimestre).orElseThrow(() -> new ResourceNotFoundException("Average not found"));

			results.forEach(resultRepository::delete);
			averageRepository.delete(average);

			return true;
		} catch (Exception e) {
			LOGGER.error("Error deleting note by student and trimestre", e);
			throw new RuntimeException("Error deleting note by student and trimestre", e);
		}
	}


	public List<TCE002ResultBySubject> getAllNoteByIdStudentAndIdYear(Long idStudent, Long IdYear) {

		return resultRepository.findByResultOfYear(idStudent, IdYear);
	}

	public Optional<Double> getAllAverageByIdStudentAndIdYear(Long idStudent, Long IdYear) {
		return averageRepository.findByAverageOfYear(idStudent, IdYear);
	}

	public Optional<Double> findSubjectNote(Long idStudent,Long idSubject,Long IdYear,Integer trimesetNumber) {
		return resultRepository.findSubjectNote(idStudent,idSubject, IdYear,trimesetNumber);
	}
}
