package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    List<WorkOrder> findByUserId(Long userId);
}
