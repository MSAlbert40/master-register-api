package com.evertix.masterregister.config;

import com.evertix.masterregister.model.*;
import com.evertix.masterregister.model.enums.ERole;
import com.evertix.masterregister.model.enums.EStatus;
import com.evertix.masterregister.repository.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;

@Component
public class DataLoader {
    private final RoleRepository roleRepository;
    private final ScheduleRepository scheduleRepository;
    private final WorkAreaRepository workAreaRepository;
    private final StatusRepository statusRepository;
    private final TypeRequestRepository typeRequestRepository;

    public DataLoader(RoleRepository roleRepository, ScheduleRepository scheduleRepository,
                      WorkAreaRepository workAreaRepository, StatusRepository statusRepository,
                      TypeRequestRepository typeRequestRepository) {
        this.roleRepository = roleRepository;
        this.scheduleRepository = scheduleRepository;
        this.workAreaRepository = workAreaRepository;
        this.statusRepository = statusRepository;
        this.typeRequestRepository = typeRequestRepository;
        this.loadData();
    }

    private void loadData() {
        this.addRoles();
        this.addSchedules();
        this.addWorkArea();
        this.addStatus();
        this.addTypeRequest();
    }

    private void addTypeRequest() {
        this.typeRequestRepository.saveAll(Arrays.asList(
                new TypeRequest("Adelantar Vacaciones"),
                new TypeRequest("Adelantar Sueldo"),
                new TypeRequest("Adelantar Bono"),
                new TypeRequest("Justificar Falta"),
                new TypeRequest("Actualizar Datos"),
                new TypeRequest("Aumento de Sueldo"),
                new TypeRequest("Retiro por Enfermedad")
        ));
    }

    private void addStatus() {
        this.statusRepository.saveAll(Arrays.asList(
           new Status(EStatus.STATUS_ABSENT),
           new Status(EStatus.STATUS_LATE),
           new Status(EStatus.STATUS_ATTENDANCE)
        ));
    }

    private void addWorkArea() {
        this.workAreaRepository.saveAll(Arrays.asList(
                new WorkArea("Develop"),
                new WorkArea("RRHH")
        ));
    }

    private void addSchedules() {
        this.scheduleRepository.saveAll(Arrays.asList(
                new Schedule(LocalTime.of(7,0,0), LocalTime.of(16,0,0)),
                new Schedule(LocalTime.of(8,0,0), LocalTime.of(17,0,0)),
                new Schedule(LocalTime.of(9,0,0), LocalTime.of(18,0,0)),
                new Schedule(LocalTime.of(10,0,0), LocalTime.of(19,0,0))
        ));
    }

    private void addRoles() {
        this.roleRepository.saveAll(Arrays.asList(
           new Role(ERole.ROLE_MANAGER),
           new Role(ERole.ROLE_EMPLOYEE)
        ));
    }
}
