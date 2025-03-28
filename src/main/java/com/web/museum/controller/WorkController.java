package com.web.museum.controller;

import com.web.museum.dto.PagedResponse;
import com.web.museum.dto.WorkInfoDTO;
import com.web.museum.dto.WorkResponseDTO;
import com.web.museum.entity.Work;
import com.web.museum.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/works")
public class WorkController {
    @Autowired
    private WorkService workService;
    
    @GetMapping
    public ResponseEntity<PagedResponse<WorkInfoDTO>> getAllWorks(
            @RequestParam int page,
            @RequestParam int size ){
        Pageable pageable = PageRequest.of(page, size);
        Page<WorkInfoDTO> works = workService.getAllWorks(pageable);

        PagedResponse<WorkInfoDTO> response = new PagedResponse<>(
                works.getContent(),
                works.getNumber(),
                works.getSize(),
                works.getTotalElements(),
                works.getTotalPages()
        );

        return ResponseEntity.ok(response);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkResponseDTO> getWorkById (@PathVariable int id) {
        WorkResponseDTO workResponseDTO = workService.getWorkById(id);
        return ResponseEntity.ok(workResponseDTO);
    }

    @PostMapping
    public ResponseEntity<WorkResponseDTO> createWork ( @RequestBody Work work){
        WorkResponseDTO workResponseDTO = workService.createWork(work);
        return ResponseEntity.ok(workResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkResponseDTO> updatework(@PathVariable int id, @RequestBody Work reqwork){
        WorkResponseDTO workResponseDTO = workService.updateWork(id,reqwork);
        return ResponseEntity.ok(workResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWork(@PathVariable int id ){
        workService.deleteWork(id);
        return ResponseEntity.ok("Success delete work id = " + "id");
    }
}
