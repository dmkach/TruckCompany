package com.truckcompany.service;

import com.truckcompany.domain.Truck;
import com.truckcompany.repository.TruckRepository;
import com.truckcompany.web.rest.vm.ManagedTruckVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by Viktor Dobroselsky.
 */
@Service
@Transactional
public class TruckService {
    private final Logger log = LoggerFactory.getLogger(TruckService.class);

    @Inject
    private TruckRepository truckRepository;

    public Truck getTruckById (Long id) {
        Truck truck = truckRepository.getOne(id);
        log.debug("Get Information about Truck with id: {}", id);
        return truck;
    }

    public Truck createTruck (ManagedTruckVM managedTruckVM) {
        Truck truck = new Truck();
        truck.setRegNumber(managedTruckVM.getRegNumber());
        truck.setConsumption(managedTruckVM.getConsumption());

        truckRepository.save(truck);
        log.debug("Created Information for Truck");
        return truck;
    }

    public void updateTruck (ManagedTruckVM managedTruckVM) {
        Truck truck = truckRepository.findOne(managedTruckVM.getId());

        truck.setConsumption(managedTruckVM.getConsumption());
        truck.setRegNumber(managedTruckVM.getRegNumber());

        log.debug("Changed fields for Truck {}", truck);
    }

    public void deleteTruck (Long id) {
        Truck truck = truckRepository.findOne(id);
        if (truck != null) {
            truckRepository.delete(truck);
            log.debug("Deleted truck {}", id);
        }
    }
}
