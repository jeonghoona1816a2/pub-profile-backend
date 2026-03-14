package com.pubprofile.backend.repository;

import com.pubprofile.backend.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("""
            select t
            from Task t
            where upper(coalesce(t.title, '')) like upper(concat('%', :keyword, '%'))
               or upper(coalesce(t.description, '')) like upper(concat('%', :keyword, '%'))
               or upper(coalesce(t.taskNo, '')) like upper(concat('%', :keyword, '%'))
               or upper(coalesce(t.status, '')) like upper(concat('%', :keyword, '%'))
               or upper(coalesce(t.priority, '')) like upper(concat('%', :keyword, '%'))
            """)
    List<Task> searchByKeyword(@Param("keyword") String keyword);
}
