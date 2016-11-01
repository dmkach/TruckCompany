package com.truckcompany.service;

import com.truckcompany.domain.Waybill;
import com.truckcompany.domain.enums.WaybillState;
import com.truckcompany.repository.RouteListRepository;
import com.truckcompany.repository.UserRepository;
import com.truckcompany.repository.WaybillRepository;
import com.truckcompany.repository.WriteOffActRepository;
import com.truckcompany.web.rest.vm.ManagedWaybillVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Service class for managing Waybills
 * Created by Viktor Dobroselsky.
 */

@Service
@Transactional
public class WaybillService {
    private final Logger log = LoggerFactory.getLogger(WaybillService.class);

    @Inject
    private WaybillRepository waybillRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private WriteOffActRepository writeOffActRepository;

    @Inject
    private RouteListRepository routeListRepository;

    public Waybill getWaybillById(Long id) {
        Waybill waybill = waybillRepository.getOne(id);
        log.debug("Get Information about Waybill with id: {}", id);
        return waybill;
    }

    public Waybill createWaybill (ManagedWaybillVM managedWaybillVM) {
        Waybill waybill = new Waybill();
        waybill.setDate(managedWaybillVM.getDate());
        waybill.setDispatcher(userRepository.findOneByLogin(managedWaybillVM.getDispatcherLogin()).get());
        waybill.setDriver(userRepository.findOneByLogin(managedWaybillVM.getDriverLogin()).get());
        waybill.setState(WaybillState.valueOf(managedWaybillVM.getState()));
        waybill.setRouteList(routeListRepository.getOne(managedWaybillVM.getRouteListId()));
        waybill.setWriteOff(writeOffActRepository.getOne(managedWaybillVM.getWriteOffId()));

        waybillRepository.save(waybill);
        log.debug("Created Information for Waybill");
        return waybill;
    }

    public void deleteWaybill(Long id) {
        Waybill waybill = waybillRepository.findOne(id);
        if (waybill != null) {
            waybillRepository.delete(waybill);
            log.debug("Deleted Waybill: {}", id);
        }
    }

    public void updateWaybill (ManagedWaybillVM managedWaybillVM) {
        waybillRepository.findOneById(managedWaybillVM.getId()).ifPresent(w -> {
            w.setDispatcher(userRepository.findOneByLogin(managedWaybillVM.getDispatcherLogin()).get());
            w.setDriver(userRepository.findOneByLogin(managedWaybillVM.getDriverLogin()).get());
            w.setDate(managedWaybillVM.getDate());
            w.setState(WaybillState.valueOf(managedWaybillVM.getState()));
            w.setWriteOff(writeOffActRepository.getOne(managedWaybillVM.getWriteOffId()));
            w.setRouteList(routeListRepository.getOne(managedWaybillVM.getRouteListId()));
            waybillRepository.save(w);
            log.debug("Changed fields for Waybill {}", w);
        });

    }
}
