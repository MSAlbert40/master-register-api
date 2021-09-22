package com.evertix.masterregister.config;

import com.evertix.masterregister.model.Role;
import com.evertix.masterregister.model.Schedule;
import com.evertix.masterregister.model.Status;
import com.evertix.masterregister.model.WorkArea;
import com.evertix.masterregister.model.enums.ERole;
import com.evertix.masterregister.model.enums.EStatus;
import com.evertix.masterregister.repository.RoleRepository;
import com.evertix.masterregister.repository.ScheduleRepository;
import com.evertix.masterregister.repository.StatusRepository;
import com.evertix.masterregister.repository.WorkAreaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;

@Component
public class DataLoader {
    private final RoleRepository roleRepository;
    private final ScheduleRepository scheduleRepository;
    private final WorkAreaRepository workAreaRepository;
    private final StatusRepository statusRepository;

    public DataLoader(RoleRepository roleRepository, ScheduleRepository scheduleRepository,
                      WorkAreaRepository workAreaRepository, StatusRepository statusRepository) {
        this.roleRepository = roleRepository;
        this.scheduleRepository = scheduleRepository;
        this.workAreaRepository = workAreaRepository;
        this.statusRepository = statusRepository;
        this.loadData();
    }

    private void loadData() {
        this.addRoles();
        this.addSchedules();
        this.addWorkArea();
        this.addStatus();
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
