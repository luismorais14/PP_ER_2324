/*
* Nome: Francisco Morais de Oliveira
* Número: 8230204
* Turma: T3
*
* Nome: Luís André Nunes Morais
* Número: 8230258
* Turma: T3
 */
package pp_er_2324;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Institution;
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.MeasurementException;
import com.estg.core.exceptions.PickingMapException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Vehicle;
import java.time.LocalDateTime;

/**
 *
 * @author Luís Morais
 */
public class InstitutionImpl implements Institution {

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addAidBox(AidBox aidbox) throws AidBoxException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addMeasurement(Measurement msrmnt, Container cntnr) throws ContainerException, MeasurementException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AidBox[] getAidBoxes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Container getContainer(AidBox aidbox, ContainerType ct) throws ContainerException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vehicle[] getVehicles() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addVehicle(Vehicle vhcl) throws VehicleException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void disableVehicle(Vehicle vhcl) throws VehicleException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void enableVehicle(Vehicle vhcl) throws VehicleException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PickingMap[] getPickingMaps() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PickingMap[] getPickingMaps(LocalDateTime ldt, LocalDateTime ldt1) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PickingMap getCurrentPickingMap() throws PickingMapException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addPickingMap(PickingMap pm) throws PickingMapException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getDistance(AidBox aidbox) throws AidBoxException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
