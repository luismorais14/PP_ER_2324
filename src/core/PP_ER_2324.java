/*
 * Nome: Francisco Morais de Oliveira
 * Número: 8230204
 * Turma: T3
 *
 * Nome: Luís André Nunes Morais
 * Número: 8230258
 * Turma: T3
 */
package core;

import com.estg.core.ContainerType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Vehicle;
import io.DataHandler;
import management.PickingMapImpl;
import management.VehicleImpl;

import java.io.IOException;
import java.time.LocalDateTime;

public class PP_ER_2324 {

    public static void main(String[] args) throws AidBoxException, ContainerException, IOException {
        AidBoxImpl aidbox1 = new AidBoxImpl("CAIXF51", "Lousada");
        AidBoxImpl aidbox2 = new AidBoxImpl("CAIXF40", "Felgueiras");
        PickingMapImpl pm = new PickingMapImpl();
        PickingMap[] pma = {pm};
        AidBoxImpl[] originalAidBoxes = {aidbox1, aidbox2};
        MeasurementImpl mi = new MeasurementImpl(LocalDateTime.now(), 232.2);
        Measurement[] ma = {mi};
        VehicleImpl vehicle = new VehicleImpl();
        Vehicle[] va = {vehicle};
        InstitutionImpl institution = new InstitutionImpl("Cerci", pma, originalAidBoxes, ma, va);
        ContainerTypeImpl type = new ContainerTypeImpl();
        ContainerType type1 = (ContainerType) type;
        DataHandler dh = new DataHandler();

        dh.apiToAidBoxes();
        dh.apiToContainers();
        dh.apiToDistances();
        dh.apiToTypes();
        dh.apiToReadings();
        dh.apiToVehicles();

        vehicle.setCode("JKL567");

        type.setContainerTypes();



        System.out.println("ContnainerType on vehicle Test");



    }

}
