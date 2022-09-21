package com.kapsta.sectorpicker.service;

import com.kapsta.sectorpicker.data.exception.SectorNotFoundException;
import com.kapsta.sectorpicker.data.exception.WorkerNotFoundException;
import com.kapsta.sectorpicker.data.request.UpdateOrCreateWorkerAndAttachSectorsRequest;
import com.kapsta.sectorpicker.model.Sector;
import com.kapsta.sectorpicker.model.Worker;
import com.kapsta.sectorpicker.repository.SectorRepository;
import com.kapsta.sectorpicker.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.*;

@Component("sectorService")
public class WorkerAssignmentService {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private WorkerRepository workerRepository;

    public List<Sector> getSectorsInHierarchicalList() {
        List<Sector> parentSectors = sectorRepository
                .findByParentIdIsNull()
                .stream().sorted(Comparator.comparing(Sector::getName))
                .toList();
        List<Sector> sectorList = new ArrayList<>();
        for (Sector parentSector : parentSectors) {
            sectorList.add(parentSector);
            sectorList.addAll(parentSector.getAllChildrenWithIndents(1));
        }
        return sectorList;
    }

    public Worker handleUpdateOrCreateRequest(UpdateOrCreateWorkerAndAttachSectorsRequest requestData) {
        Worker worker = (requestData.getUUID() != null) ?
                getWorker(requestData.getUUID()) :
                new Worker();
        worker.setName(requestData.getName());
        worker.setTac(requestData.isTac());
        worker.setUserSectors(sectorRepository.findAllById(requestData.getSectors()));
        workerRepository.save(worker);
        return worker;
    }

    public Worker getWorker(UUID id) throws WorkerNotFoundException {
        if (id == null) {
            throw new InvalidParameterException("Worker not found");
        }
        Optional<Worker> worker = workerRepository.findById(id);
        return worker.orElseThrow(() -> new WorkerNotFoundException("Worker not found"));
    }

    public Sector getSectorById(Long id) {
        return sectorRepository.findById(id).orElseThrow(
                () -> new SectorNotFoundException("Sector was not found"));
    }

    public List<Sector> getSectors() {
        return sectorRepository.findAll();
    }
}
