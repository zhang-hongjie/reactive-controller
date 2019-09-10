package com.example.repository;

import com.example.model.MainEntity;
import com.example.model.SqlDeleteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface SqlDeleteRepository extends JpaRepository<SqlDeleteEntity, String>,
        RevisionRepository<SqlDeleteEntity, String, Integer> {

}