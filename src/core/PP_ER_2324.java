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
import com.estg.core.Institution;
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.InstitutionException;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Vehicle;
import io.DataHandler;
import io.ImporterImpl;
import management.PickingMapImpl;
import management.VehicleImpl;

import java.io.IOException;
import java.time.LocalDateTime;

public class PP_ER_2324 {

    public static void main(String[] args) throws AidBoxException, ContainerException, IOException, InstitutionException {
        //AidBoxImpl aidbox1 = new AidBoxImpl("CAIXF51", "Lousada");
        //AidBoxImpl aidbox2 = new AidBoxImpl("CAIXF40", "Felgueiras");
        PickingMapImpl pm = new PickingMapImpl();
        PickingMap[] pma = {pm};
        //AidBoxImpl[] originalAidBoxes = {aidbox1, aidbox2};
        MeasurementImpl mi = new MeasurementImpl(LocalDateTime.now(), 232.2);
        Measurement[] ma = {mi};
        VehicleImpl vehicle = new VehicleImpl();
        Vehicle[] va = {vehicle};
        //InstitutionImpl institution = new InstitutionImpl("Cerci", pma, originalAidBoxes, ma, va);
        ContainerTypeImpl type = ContainerTypeImpl.fromString("medicine");
        ContainerType type1 = (ContainerType) type;
        DataHandler dh = new DataHandler();
        Institution instn = new InstitutionImpl();
        ImporterImpl importer = new ImporterImpl();

        dh.apiToAidBoxes();
        dh.apiToContainers();
        dh.apiToDistances();
        dh.apiToTypes();
        dh.apiToReadings();
        dh.apiToVehicles();

        importer.importData(instn);
        type.setContainerTypes();

        //vehicle.setCode("ABC123");
        //vehicle.setCapacities();

        //type.setContainerTypes();

        //System.out.println("Types test");
        //System.out.println(type.toString());

        //System.out.println("\nContnainerType on vehicle Test");
        //System.out.println(vehicle.getCapacity(type));

        //System.out.println(vehicle.toString());
        System.out.println("Importer test");
        for (int i = 0; i < instn.getAidBoxes().length; i++) {
            System.out.println(instn.getAidBoxes()[i].toString());
        }
    }

}
