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
import com.estg.core.exceptions.*;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Vehicle;
import io.DataHandler;
import io.ImporterImpl;
import management.PickingMapImpl;
import management.RouteGeneratorImpl;
import management.VehicleImpl;

import java.io.IOException;
import java.time.LocalDateTime;

public class PP_ER_2324 {

    public static void main(String[] args) throws AidBoxException, ContainerException, IOException, VehicleException{
        DataHandler dh = new DataHandler();
        InstitutionImpl instn = new InstitutionImpl();
        ImporterImpl importer = new ImporterImpl();
        RouteGeneratorImpl routeGen = new RouteGeneratorImpl();
        Menus menu = new Menus(instn);

        dh.apiToAidBoxes();
        dh.apiToContainers();
        dh.apiToDistances();
        dh.apiToTypes();
        dh.apiToReadings();
        dh.apiToVehicles();

        menu.MainMenu();

    }

}
