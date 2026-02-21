package com.veriprotocol.aiinfra.repo;


import com.veriprotocol.aiinfra.domain.JobStatus;
import com.veriprotocol.aiinfra.domain.JobEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.List;


public interface JobRepository extends JpaRepository<JobEntity, String> {
    
	Optional<JobEntity> findByRequestId(String requestId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        select j from JobEntity j
        where j.status = :status
        order by j.createdAt asc
        """)
    List<JobEntity> pickNextPendingForUpdate(@Param("status") JobStatus status, Pageable pageable);

    //Optional<JobEntity> pickNextPendingForUpdate(@Param("status") JobStatus status);
    /*@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        select j from JobEntity j
        where j.status = :status
        order by j.createdAt asc
        """)
    Optional<JobEntity> pickNextPendingForUpdate(@Param("status") JobStatus status);*/

}
