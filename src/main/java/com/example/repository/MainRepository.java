package com.example.repository;

import com.example.model.MainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MainRepository extends JpaRepository<MainEntity, String>,
        RevisionRepository<MainEntity, String, Integer> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM public.main WHERE id=?")
    void deleteByIdNativeQuery(String id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO public.main(id) VALUES (?)")
    void insertNativeQuery(String id);
}