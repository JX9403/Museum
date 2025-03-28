package com.web.museum.service;

import com.web.museum.dto.WorkInfoDTO;
import com.web.museum.dto.WorkResponseDTO;
import com.web.museum.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkService {
    Page<WorkInfoDTO> getAllWorks(Pageable pageable);
    WorkResponseDTO getWorkById ( int id ) ;
    WorkResponseDTO updateWork ( int id, Work work );
    void deleteWork ( int id);
    WorkResponseDTO createWork ( Work work );

}
