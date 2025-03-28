package com.web.museum.service;

import com.web.museum.dao.AuthorRepository;
import com.web.museum.dao.UserRepository;
import com.web.museum.dao.WorkRepository;
import com.web.museum.dto.AuthorInfoDTO;
import com.web.museum.dto.UserInfoDTO;
import com.web.museum.dto.WorkInfoDTO;
import com.web.museum.dto.WorkResponseDTO;
import com.web.museum.entity.Author;
import com.web.museum.entity.User;
import com.web.museum.entity.Work;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkServiceImpl implements WorkService{
   @Autowired
    private WorkRepository workRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<WorkInfoDTO> getAllWorks(Pageable pageable) {
        Page<Work> works = workRepository.findAll(pageable);
        return works.map(this::convertToInfoDTO);
    }

    @Override
    public WorkResponseDTO getWorkById(int id) {
        Optional<Work> workOptional = workRepository.findById(id);
        if(workOptional.isEmpty()){
            throw new RuntimeException("Work not found");
        }
        Work work = workOptional.get();

        work.setViews(work.getViews()+1);
        workRepository.save(work);

        return convertToDTO(work);
    }

    @Override
    @Transactional
    public WorkResponseDTO updateWork(int id, Work reqWork) {

        Optional<Work> workOptional = workRepository.findById(id);
        if (workOptional.isEmpty()) {
            throw new RuntimeException("Work not found");
        }
        Work work = workOptional.get();

        if (reqWork.getTitle() == null || reqWork.getTitle().isEmpty()) {
            throw new RuntimeException("Title cannot be empty");
        }
        if (reqWork.getContent() == null || reqWork.getContent().isEmpty()) {
            throw new RuntimeException("Content cannot be empty");
        }
        if (reqWork.getAuthor() == null) {
            throw new RuntimeException("Valid Author ID is required");
        }

        // Lấy Author từ database
        Optional<Author> authorOptional = authorRepository.findById(reqWork.getAuthor().getId());
        if (authorOptional.isEmpty()) {
            throw new RuntimeException("Author not found");
        }

        // Kiểm tra và cập nhật User nếu có
        if (reqWork.getUser() != null) {
            Optional<User> userOptional = userRepository.findById(reqWork.getUser().getId());
            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found");
            }
            work.setUser(userOptional.get());
        } else {
            work.setUser(null);
        }

        work.setTitle(reqWork.getTitle());
        work.setContent(reqWork.getContent());
        work.setAuthor(authorOptional.get());

        Work updatedWork = workRepository.save(work);
        return convertToDTO(updatedWork);
    }


    @Override
    @Transactional
    public void deleteWork(int id) {
        Optional<Work> workOptional = workRepository.findById(id);
        if(workOptional.isEmpty()){
            throw new RuntimeException("Work not found");
        }
        Work work = workOptional.get();
        workRepository.delete(work);
    }

    @Override
    @Transactional
    public WorkResponseDTO createWork(Work reqWork) {

        System.out.println("content :" + reqWork.getContent());
        if (reqWork.getTitle() == null || reqWork.getTitle().isEmpty()) {
            throw new RuntimeException("Title cannot be empty");
        }
        if (reqWork.getContent() == null || reqWork.getContent().isEmpty()) {
            throw new RuntimeException("Content cannot be empty");
        }
        if (reqWork.getAuthor() == null || reqWork.getAuthor().getId() == 0) {
            throw new RuntimeException("Valid Author ID is required");
        }

        // Tìm Author trong database trước khi gán vào Work
        Optional<Author> authorOptional = authorRepository.findById(reqWork.getAuthor().getId());
        if (authorOptional.isEmpty()) {
            throw new RuntimeException("Author not found");
        }

        // Kiểm tra và cập nhật User nếu có
        if (reqWork.getUser() != null) {
            Optional<User> userOptional = userRepository.findById(reqWork.getUser().getId());
            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found");
            }
            reqWork.setUser(userOptional.get());
        } else {
            reqWork.setUser(null);
        }
        reqWork.setAuthor(authorOptional.get());
        Work newWork = workRepository.save(reqWork);
        return convertToDTO(newWork);
    }



    private WorkResponseDTO convertToDTO(Work work) {
        WorkResponseDTO WorkResponseDTO = new WorkResponseDTO();
        WorkResponseDTO.setId(work.getId());
        WorkResponseDTO.setTitle(work.getTitle());
        WorkResponseDTO.setContent(work.getContent());
        AuthorInfoDTO authorInfoDTO = new AuthorInfoDTO();
        authorInfoDTO.setId(work.getAuthor().getId());
        authorInfoDTO.setName(work.getAuthor().getName());
        if(work.getUser() != null){
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(work.getUser().getId());
            userInfoDTO.setFullname(work.getUser().getFullname());
            WorkResponseDTO.setUser(userInfoDTO);
        }
        else {
            WorkResponseDTO.setUser(null);
        }

        WorkResponseDTO.setAuthor(authorInfoDTO);

        return WorkResponseDTO;
    }

    private WorkInfoDTO convertToInfoDTO(Work work) {
        WorkInfoDTO WorkInfoDTO = new WorkInfoDTO();
        WorkInfoDTO.setId(work.getId());
        WorkInfoDTO.setTitle(work.getTitle());
        WorkInfoDTO.setViews(work.getViews());
        WorkInfoDTO.setSaves(work.getSaves());

        AuthorInfoDTO authorInfoDTO = new AuthorInfoDTO();
        authorInfoDTO.setId(work.getAuthor().getId());
        authorInfoDTO.setName(work.getAuthor().getName());
        if(work.getUser() != null){
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(work.getUser().getId());
            userInfoDTO.setFullname(work.getUser().getFullname());
            WorkInfoDTO.setUser(userInfoDTO);
        }
        else {
            WorkInfoDTO.setUser(null);
        }

        WorkInfoDTO.setAuthor(authorInfoDTO);

        return WorkInfoDTO;
    }

}
