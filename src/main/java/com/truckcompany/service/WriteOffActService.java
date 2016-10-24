package com.truckcompany.service;

import com.truckcompany.domain.WriteOffAct;
import com.truckcompany.repository.WriteOffActRepository;
import com.truckcompany.web.rest.vm.ManagedWriteOffVM;
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
public class WriteOffActService {
    private final Logger log = LoggerFactory.getLogger(WriteOffActService.class);

    @Inject
    private WriteOffActRepository writeOffActRepository;

    public WriteOffAct getWriteOffActById (Integer id) {
        WriteOffAct writeOffAct = writeOffActRepository.getOne(id);
        log.debug("Get Information about WriteOffAct with id: {}", id);
        return writeOffAct;
    }

    public WriteOffAct createWriteOffAct (ManagedWriteOffVM managedWriteOffVM) {
        WriteOffAct writeOffAct = new WriteOffAct();
        writeOffAct.setCount(managedWriteOffVM.getCount());
        writeOffAct.setDate(managedWriteOffVM.getDate());

        writeOffActRepository.save(writeOffAct);
        log.debug("Created Information for WriteOffAct");
        return writeOffAct;
    }

    public void updateWriteOffAct (ManagedWriteOffVM managedWriteOffVM) {
        writeOffActRepository.findOneById(managedWriteOffVM.getId()).ifPresent(writeOffAct -> {
            writeOffAct.setDate(managedWriteOffVM.getDate());
            writeOffAct.setCount(managedWriteOffVM.getCount());

            writeOffActRepository.save(writeOffAct);
            log.debug("Changed fields for Waybill {}", writeOffAct);
        });
    }

    public void deleteWriteOffAct (Integer id) {
        WriteOffAct writeOffAct = writeOffActRepository.findOne(id);
        if (writeOffAct != null) {
            writeOffActRepository.delete(writeOffAct);
            log.debug("Deleted WriteOffAct {}", id);
        }
    }
}