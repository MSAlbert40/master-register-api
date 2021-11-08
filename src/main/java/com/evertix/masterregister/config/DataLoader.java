package com.evertix.masterregister.config;

import com.evertix.masterregister.model.*;
import com.evertix.masterregister.model.enums.ERole;
import com.evertix.masterregister.model.enums.EStatus;
import com.evertix.masterregister.repository.*;
import com.evertix.masterregister.security.request.SignUpRequest;
import com.evertix.masterregister.service.AuthService;
import com.evertix.masterregister.service.RequestService;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;

import static java.lang.Long.*;

@Component
public class DataLoader {
    private final RoleRepository roleRepository;
    private final ScheduleRepository scheduleRepository;
    private final WorkAreaRepository workAreaRepository;
    private final AuthService authService;
    private final StatusRepository statusRepository;
    private final TypeRequestRepository typeRequestRepository;

    public DataLoader(RoleRepository roleRepository, ScheduleRepository scheduleRepository,
                      WorkAreaRepository workAreaRepository, AuthService authService,
                      StatusRepository statusRepository, TypeRequestRepository typeRequestRepository) {
        this.roleRepository = roleRepository;
        this.scheduleRepository = scheduleRepository;
        this.workAreaRepository = workAreaRepository;
        this.authService = authService;
        this.statusRepository = statusRepository;
        this.typeRequestRepository = typeRequestRepository;
        this.loadData();
    }

    private void loadData() {
        this.addRoles();
        this.addSchedules();
        this.addWorkArea();
        this.addUsers();
        this.addStatus();
        this.addTypeRequest();
    }

    private void addUsers() {
        SignUpRequest firstUser = new SignUpRequest("MSAlbert", "password", "msegovia.albert@gmail.com", "Albert", "Mayta Segovia",
                "75458458", 21, "Masculino", "AV. Santa Angela", "984751458", 1500);
        this.authService.registerUser(firstUser, 1L, 1L, null);

        SignUpRequest secondUser = new SignUpRequest("PBravo", "patrick12", "patrick.bravo@outook.com", "Patrick", "Bravo Ordoñez",
                "75896325", 21, "Masculino", "AV. Los Jaspes", "956887425", 1500);
        this.authService.registerUser(secondUser, 1L, 1L, null);

        // Add Employees
        SignUpRequest firstEmployee = new SignUpRequest("KLFernanda", "password", "kloayza.fernanda@gmail.com", "Karen", "Kuylen Loayza",
                "74856953", 25, "Femenino", "AV. Las Palmeras", "975159458", 1400);
        this.authService.registerUser(firstEmployee, 3L, 2L, 1L);

        SignUpRequest secondEmployee = new SignUpRequest("SHDaniel", "password", "shuaman.daniel@hotmail.com", "Daniel", "Sanchez Huaman",
                "74789925", 24, "Masculino", "AV. Sotomayor crd. 4", "947859458", 1600);
        this.authService.registerUser(secondEmployee, 2L, 1L, 1L);

        SignUpRequest thirdEmployee = new SignUpRequest("ZPMiguel", "password", "zparazzi.miguel@outlook.com", "Miguel", "Zarate Parazzi",
                "75891523", 23, "Maculino", "AV. Aire cdr. 5", "974859658", 1500);
        this.authService.registerUser(thirdEmployee, 4L, 3L, 1L);
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
                new WorkArea("Ventas"),
                new WorkArea("RRHH"),
                new WorkArea("Producción")
        ));
    }

    private void addSchedules() {
        this.scheduleRepository.saveAll(Arrays.asList(
                new Schedule(LocalTime.of(7,0,0), LocalTime.of(16,0,0)),
                new Schedule(LocalTime.of(8,0,0), LocalTime.of(17,0,0)),
                new Schedule(LocalTime.of(9,0,0), LocalTime.of(18,0,0)),
                new Schedule(LocalTime.of(10,0,0), LocalTime.of(19,0,0)),
                new Schedule(LocalTime.of(11, 0, 0), LocalTime.of(20,0,0)),
                new Schedule(LocalTime.of(12, 0, 0), LocalTime.of(21, 0, 0)),
                new Schedule(LocalTime.of(13, 0, 0), LocalTime.of(22, 0, 0))
        ));
    }

    private void addRoles() {
        this.roleRepository.saveAll(Arrays.asList(
           new Role(ERole.ROLE_MANAGER),
           new Role(ERole.ROLE_EMPLOYEE)
        ));
    }
}
