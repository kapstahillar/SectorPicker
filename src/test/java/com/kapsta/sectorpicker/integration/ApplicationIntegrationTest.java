package com.kapsta.sectorpicker.integration;


import com.kapsta.sectorpicker.data.request.UpdateOrCreateWorkerAndAttachSectorsRequest;
import com.kapsta.sectorpicker.model.Sector;
import com.kapsta.sectorpicker.model.Worker;
import com.kapsta.sectorpicker.service.WorkerAssignmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationIntegrationTest {

    @Autowired
    WorkerAssignmentService workerAssignmentService;

    @Test
    public void Check_datasource_has_more_than_0_migrated_sectors() {
        assertTrue(workerAssignmentService.getSectors().size() > 0);
    }

    @Test
    public void Update_or_create_worker_with_empty_name() {
        //Create request and add worker to database and attach sectors to it
        UpdateOrCreateWorkerAndAttachSectorsRequest request =
                new UpdateOrCreateWorkerAndAttachSectorsRequest();
        request.setWorkerId(null);
        request.setName(null);
        try {
            workerAssignmentService.handleUpdateOrCreateRequest(request);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void Update_or_create_worker_and_attach_to_2sectors() {
        // Get two sectors from database
        List<Sector> addedSectors = new ArrayList<>();
        addedSectors.add(workerAssignmentService.getSectorById(1L));
        addedSectors.add(workerAssignmentService.getSectorById(19L));

        //Create request and add worker to database and attach sectors to it
        UpdateOrCreateWorkerAndAttachSectorsRequest request =
                new UpdateOrCreateWorkerAndAttachSectorsRequest();
        request.setWorkerId(null);
        request.setName("Test Worker");
        request.setSectors(addedSectors.stream().map(Sector::getId).toList());
        Worker worker = workerAssignmentService.handleUpdateOrCreateRequest(request);
        List<Sector> userSectors = worker.getUserSectors();
        assertNotNull(worker.getId());
        assertEquals("Test Worker", worker.getName());
        assertEquals(2, userSectors.size());
    }
}
