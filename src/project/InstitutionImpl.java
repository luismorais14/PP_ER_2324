/*
 * Nome: Francisco Morais de Oliveira
 * Número: 8230204
 * Turma: T3
 *
 * Nome: Luís André Nunes Morais
 * Número: 8230258
 * Turma: T3
 */
package project;

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
import com.estg.io.HTTPProvider;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Vehicle;

import java.time.LocalDateTime;

public class InstitutionImpl implements Institution {

    private final int ARRAY_SIZE = 10;

    private String InstitutionName;
    private PickingMap[] pickingMaps;
    private AidBoxManagement aidboxes;
    private static int nAidBox;
    private MeasurementsManagement measurements;
    private static int nMeasurement;
    private Vehicle[] enableVehicles;
    private Vehicle[] disableVehicles;
    private static int nPickingMaps;
    private HTTPProvider httpProvider;
    private static int nDisableVehicles;
    private static int nEnableVehicles;

    /**
     * Construtor de Institution
     *
     */
    public InstitutionImpl() {
        this.InstitutionName = null;
        this.pickingMaps = new PickingMap[ARRAY_SIZE];
        this.aidboxes = new AidBoxManagement();
        this.measurements = new MeasurementsManagement();
        this.enableVehicles = new Vehicle[ARRAY_SIZE];
        this.disableVehicles = new Vehicle[ARRAY_SIZE];
        this.enableVehicles = new Vehicle[ARRAY_SIZE];
        this.httpProvider = new HTTPProvider();
        nAidBox = 0;
        nMeasurement = 0;
        nPickingMaps = 0;
        nDisableVehicles = 0;
        nEnableVehicles = 0;
    }

    /**
     * Construtor de Institution
     *
     * @param name o nome da instituição
     * @param pm os mapas de picking associados à instituição
     * @param aidbox as caixas de ajuda associadas à instituição
     * @param measurement as medições associadas à instituição
     * @param vehicle os veículos associados à instituição
     */
    public InstitutionImpl(String name, PickingMap[] pm, AidBoxManagement aidbox, MeasurementsManagement measurement, Vehicle[] vehicle) {
        this.InstitutionName = name;
        this.pickingMaps = pm;
        this.aidboxes = aidbox;
        this.measurements = measurement;
        this.enableVehicles = vehicle;
        this.httpProvider = new HTTPProvider();
        this.disableVehicles = new Vehicle[ARRAY_SIZE];
        this.enableVehicles = new Vehicle[ARRAY_SIZE];
        nAidBox = 0;
        nMeasurement = 0;
        nPickingMaps = 0;
        nDisableVehicles = 0;
        nEnableVehicles = 0;
    }

    /**
     * Define o nome da instituição.
     *
     * @param InstitutionName o novo nome da instituição
     */
    public void setInstitutionName(String InstitutionName) {
        this.InstitutionName = InstitutionName;
    }

    /**
     * Define os picking maps da instituição.
     *
     * @param pickingMaps os novos mapas de picking a serem atribuídos à instituição
     */
    public void setPickingMaps(PickingMap[] pickingMaps) {
        this.pickingMaps = pickingMaps;
    }

    /**
     * Define as aidboxes da instituição.
     *
     * @param aidBox as novas aidboxes a serem atribuídas à instituição
     */
    public void setAidBox(AidBox[] aidBox) {
        this.aidboxes.setAidBox(aidBox);
    }

    /**
     * Define as medições associadas à instituição.
     *
     * @param measurement as novas medições a serem atribuídas à instituição
     */
    public void setMeasurement(Measurement[] measurement) {
        this.measurements.setMeasurements(measurement);
    }

    /**
     * Define os veículos habilitados associados à instituição.
     *
     * @param vehicle os novos veículos habilitados a serem atribuídos à instituição
     */
    public void setVehicle(Vehicle[] vehicle) {
        this.enableVehicles = vehicle;
    }

    /**
     * Obtém o nome da instituição.
     *
     * @return o nome da instituição
     */
    @Override
    public String getName() {
        return this.InstitutionName;
    }

    /**
     * Adiciona uma aidbox à instituição.
     *
     * @param aidbox a aidbox a ser adicionada à instituição
     * @return true se a aidbox foi adicionada com sucesso, false caso contrário
     * @throws AidBoxException se a caixa de ajuda for nula ou contiver containers duplicados de um determinado tipo
     */
    @Override
    public boolean addAidBox(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("AidBox is null");
        } else if (this.aidboxes.verifyDuplicateContainers(aidbox)) {
            throw new AidBoxException("AidBox is invalid");
        }
        return this.aidboxes.addAidBox(aidbox);
    }

    /**
     * Adiciona uma medição a um contêiner.
     *
     * @param msrmnt a medição a ser adicionada
     * @param cntnr o contêiner ao qual a medição será adicionada
     * @return true se a medição foi adicionada com sucesso, false caso contrário
     * @throws ContainerException se o contêiner for nulo
     * @throws MeasurementException se o valor da medição for inválido
     */
    @Override
    public boolean addMeasurement(Measurement msrmnt, Container cntnr) throws ContainerException, MeasurementException {
        if (cntnr == null) {
            throw new ContainerException("This container does not exist!");
        } else if (msrmnt.getValue() < 0.0 || msrmnt.getValue() > cntnr.getCapacity()) {
            throw new MeasurementException("The value is lesser than 0 or higher to the capacity!");
        } else if (this.checkAidBoxFromContainer(cntnr) == -1) {
            throw new MeasurementException("The container does not exist.");
        }
        return this.measurements.addMeasurement(msrmnt);
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

    /**
     * Método responsável por retornar a posição na coleção da aidbox com o container recebido como parâmetro
     * @param cntnr
     * @return
     */
    private int checkAidBoxFromContainer(Container cntnr) {
        int index = -1;
        if (cntnr == null) {
            return -1;
        }
        for (int i = 0; i < this.aidboxes.getAidBoxes().length; i++) {
            for (int j = 0; j < this.aidboxes.getAidBoxes()[i].getContainers().length; j++) {
                if (this.aidboxes.getAidBoxes()[i].getContainers()[j].equals(cntnr)) {
                    index = i;
                }
            }
        }
        return index;
    }

}
