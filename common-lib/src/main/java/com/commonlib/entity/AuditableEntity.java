package com.commonlib.entity;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class AuditableEntity {
       private LocalDateTime createdAt;
         private LocalDateTime updatedAt;
}
