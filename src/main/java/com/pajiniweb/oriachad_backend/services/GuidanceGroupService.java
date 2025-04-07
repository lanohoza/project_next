package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCO001.conditions.TCO001StudentCondition;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditGuidanceGroupDto;
import com.pajiniweb.oriachad_backend.domains.dtos.GuidanceGroupDto;
import com.pajiniweb.oriachad_backend.domains.dtos.StudentDto;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.repositories.GuidanceGroupRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuidanceGroupService {

    private static final Logger log = LoggerFactory.getLogger(GuidanceGroupService.class);

    final HelperService helperService;
    final GuidanceGroupRepository guidanceGroupRepository;


    public boolean create(AddEditGuidanceGroupDto addEditGuidanceGroupDto) {
        OriachadUser oriachadUser = helperService.getCurrentUser().getOriachadUser();
        Year year = helperService.getCurrentYear();
        GuidanceGroup guidanceGroup = GuidanceGroup.builder().createdBy(oriachadUser).year(year).title(addEditGuidanceGroupDto.getTitle()).build();
        if (addEditGuidanceGroupDto.getIdStudents() != null)
            guidanceGroup.setStudents(addEditGuidanceGroupDto.getIdStudents().stream().map((idStudent) -> Student.builder().id(idStudent).build()).collect(Collectors.toSet()));
        guidanceGroupRepository.save(guidanceGroup);
        return true;
    }

    public boolean update(AddEditGuidanceGroupDto addEditGuidanceGroupDto) {
        GuidanceGroup guidanceGroup = guidanceGroupRepository.findById(addEditGuidanceGroupDto.getId()).orElseThrow();
        guidanceGroup.setTitle(addEditGuidanceGroupDto.getTitle());
        if (addEditGuidanceGroupDto.getIdStudents() != null)
            guidanceGroup.setStudents(addEditGuidanceGroupDto.getIdStudents().stream().map((idStudent) -> Student.builder().id(idStudent).build()).collect(Collectors.toSet()));
        guidanceGroupRepository.save(guidanceGroup);
        return true;
    }

    public Page<GuidanceGroupDto> searchGuidanceGroups(String search, Long idYear, Pageable pageable) {
        OriachadUser oriachadUser = helperService.getCurrentUser().getOriachadUser();
        return guidanceGroupRepository.search(search, idYear, oriachadUser.getId(), pageable);
    }

    public GuidanceGroupDto findById(Long id) {

        return guidanceGroupRepository.findByIdDto(id);
    }

    public boolean deleteById(Long id) {
        return false;
    }

    public GuidanceGroupDto findByIdToEdit(Long id) {
        return guidanceGroupRepository.findById(id).map(guidanceGroup -> GuidanceGroupDto.builder().id(guidanceGroup.getId()).idStudents(guidanceGroup.getStudents().stream().map(Student::getId).toList()).title(guidanceGroup.getTitle()).build()).orElseThrow();
    }

    public List<GuidanceGroupDto> getGuidanceGroupByCurrentYear() {

        Year year = helperService.getCurrentYear();
        OriachadUser oriachadUser = helperService.getCurrentUser().getOriachadUser();
        return guidanceGroupRepository.findByIdYearAndIdCreatedBy(year.getId(), oriachadUser.getId()).stream().map(groupByCurrentYear -> GuidanceGroupDto.builder().title(groupByCurrentYear.getTitle()).id(groupByCurrentYear.getId()).build()).toList();

    }

    @Transactional
    public void saveGroups(String groupName, List<Student> students) {
        OriachadUser currentUser = helperService.getCurrentUser().getOriachadUser();
        Year currentYear = helperService.getCurrentYear();
        GuidanceGroup guidanceGroup = GuidanceGroup.builder().createdBy(currentUser).year(currentYear).title(groupName).build();
        guidanceGroup.setStudents(students.stream().collect(Collectors.toSet()));
        guidanceGroupRepository.save(guidanceGroup);

    }
}
