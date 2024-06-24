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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

import management.PickingMapManagement;
import management.VehicleImpl;
import management.VehicleManagement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InstitutionImpl implements Institution {

    private final int ARRAY_SIZE = 10;

    private String InstitutionName;
    private PickingMapManagement pickingMaps = new PickingMapManagement();
    private AidBoxManagement aidboxes = new AidBoxManagement();
    private MeasurementsManagement measurements = new MeasurementsManagement();
    private VehicleManagement vehiclesManagement = new VehicleManagement();
    private HTTPProvider httpProvider = new HTTPProvider();

    /**
     * Construtor de Institution
     */
    public InstitutionImpl() {
        this.InstitutionName = null;
    }

    /**
     * Construtor de Institution
     *
     * @param name        o nome da instituição
     * @param pm          os mapas de picking associados à instituição
     * @param aidbox      as caixas de ajuda associadas à instituição
     * @param measurement as medições associadas à instituição
     * @param vehicles     os veículos associados à instituição
     */
    public InstitutionImpl(String name, PickingMap[] pm, AidBox[] aidbox, Measurement[] measurement, Vehicle[] vehicles) {
        this.InstitutionName = name;
        this.pickingMaps.setPickingMaps(pm);
        this.aidboxes.setAidBox(aidbox);
        this.measurements.setMeasurements(measurement);
        this.vehiclesManagement.setEnabledVehiclesVehicles(vehicles);
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
     * @param pickingMaps os novos mapas de picking a serem atribuídos à
     *                    instituição
     */
    public void setPickingMaps(PickingMap[] pickingMaps) {
        this.pickingMaps.setPickingMaps(pickingMaps);
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
     * @param vehicle os novos veículos habilitados a serem atribuídos à
     *                instituição
     */
    public void setEnabledVehicles(Vehicle[] vehicle) {
        this.vehiclesManagement.setEnabledVehiclesVehicles(vehicle);
    }

    /**
     * Define os veículos desabilitados associados à instituição.
     *
     * @param vehicle os novos veículos desabilitados a serem atribuídos à instituição
     */
    public void setDisabledVehicles(Vehicle[] vehicle) {
        this.vehiclesManagement.setDisabledVehicles(vehicle);
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
     * @throws AidBoxException se a caixa de ajuda for nula ou contiver
     *                         containers duplicados de um determinado tipo
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
     * @param cntnr  o contêiner ao qual a medição será adicionada
     * @return true se a medição foi adicionada com sucesso, false caso
     * contrário
     * @throws ContainerException   se o contêiner for nulo
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

    /**
     * Obtém uma cópia profunda das caixas de ajuda associadas à instituição.
     *
     * @return uma cópia profunda das caixas de ajuda associadas à instituição
     */
    @Override
    public AidBox[] getAidBoxes() {
        return this.deepCopyAidBoxes(this.aidboxes.getAidBoxes());
    }

    /**
     * Método responsável por retornar uma cópia de um container existente da aibox e do tipo recebidos como parâmetros
     * @param aidbox a aidbox onde está o container
     * @param ct o tipo do container
     * @return uma cópia de um container existente da aibox e do tipo recebidos como parâmetros
     * @throws ContainerException exceção a ser lançada caso a aidbox não exista ou se não existir nenhum container daquele tipo
     */
    @Override
    public Container getContainer(AidBox aidbox, ContainerType ct) throws ContainerException {
        try {
            if (this.aidboxes.verifyAidBoxExistence(aidbox)) {
                throw new ContainerException("AidBox is already exist");
            }
            if (aidbox.getContainer(ct) == null) {
                throw new ContainerException("A container with the given item type doesn't exist");
            }
        } catch (AidBoxException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        return this.deepCopyContainer(aidbox.getContainer(ct));
    }

    /**
     * Método responsável por retornar uma deep copy dos veículos na instituição
     * @return uma deep copy dos veículos da instituição
     */
    @Override
    public Vehicle[] getVehicles() {
        return this.deepCopyVehicles();
    }

    /**
     * Adiciona um veículo habilitado à instituição.
     *
     * @param vhcl o veículo a ser adicionado à instituição
     * @return true se o veículo foi adicionado com sucesso, false caso
     * contrário
     * @throws VehicleException se o veículo for nulo
     */
    @Override
    public boolean addVehicle(Vehicle vhcl) throws VehicleException {
        boolean aux = true;
        if (vhcl == null) {
            throw new VehicleException("Vehicle is null.");
        }
        return this.vehiclesManagement.addVehicle(vhcl);
    }

    /**
     * Método responsável por desativar um veículo
     * @param vhcl veículo a desativar
     * @throws VehicleException exceção a ser lançada caso o veículo não exista ou já esteja desativado
     */
    @Override
    public void disableVehicle(Vehicle vhcl) throws VehicleException {
        this.vehiclesManagement.disableVehicle(vhcl);
    }

    /**
     * Método responsável por ativar um veículo
     * @param vhcl veículo a ser ativado
     * @throws VehicleException exceção a ser lançada caso o veículo não exista ou já esteja ativo
     */
    @Override
    public void enableVehicle(Vehicle vhcl) throws VehicleException {
        this.vehiclesManagement.enableVehicle(vhcl);
    }

    /**
     * Obtém as mapas de picking associadas à instituição.
     *
     * @return os mapas de picking associados à instituição
     */
    @Override
    public PickingMap[] getPickingMaps() {
        return this.pickingMaps.getPickingMaps();
    }

    /**
     * Obtém os mapas de picking associados à instituição dentro de um intervalo
     * de datas especificado.
     *
     * @param ldt  a data de início do intervalo de datas
     * @param ldt1 a data de término do intervalo de datas
     * @return os mapas de picking associados à instituição dentro do intervalo
     * de datas especificado
     */
    @Override
    public PickingMap[] getPickingMaps(LocalDateTime ldt, LocalDateTime ldt1) {
        PickingMap[] tmpPickingMap = new PickingMap[this.pickingMaps.getPickingMaps().length];
        int tmpNPickingMaps = 0;
        for (int i = 0; i < this.pickingMaps.getPickingMaps().length; i++) {
            if (this.pickingMaps.getPickingMaps()[i].getDate().isBefore(ldt1) && this.pickingMaps.getPickingMaps()[i].getDate().isAfter(ldt)) {
                tmpPickingMap[tmpNPickingMaps] = this.pickingMaps.getPickingMaps()[i];
                tmpNPickingMaps++;
            }
        }
        return tmpPickingMap;
    }

    /**
     * Obtém o mapa de picking atual da instituição.
     *
     * @return o mapa de picking atual da instituição
     * @throws PickingMapException se não houver mapas de picking na instituição
     */
    @Override
    public PickingMap getCurrentPickingMap() throws PickingMapException {
        PickingMap pm = null;
        if (this.pickingMaps.getPickingMaps().length == 0) {
            throw new PickingMapException("There are no picking maps in the institution.");
        }
        for (int i = 0; i < pickingMaps.getPickingMaps().length; i++) {
            pm = compareDate(this.pickingMaps.getPickingMaps()[i].getDate());
        }
        return pm;
    }

    /**
     * Adiciona um mapa de coleta à lista de mapas de coleta.
     *
     * @param pm O mapa de coleta a ser adicionado.
     * @return true se o mapa de coleta foi adicionado com sucesso, false caso
     * contrário.
     * @throws PickingMapException Se o mapa de coleta fornecido for nulo.
     */
    @Override
    public boolean addPickingMap(PickingMap pm) throws PickingMapException {
        if (pm == null) {
            throw new PickingMapException("The picking map is null.");
        }
        return this.pickingMaps.addPickingMap(pm);
    }

    /**
     * Metodo responsável por retornar a distância entre a instituição atual e o
     * parâmetro aidbox
     *
     * @param aidbox aidbox para calcular a distância
     * @return a distância entre a instituição atual e a aidbox recebida como
     * parâmetro
     * @throws AidBoxException exceção a ser lançada se a aidbox não existir
     */
    @Override
    public double getDistance(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("AidBox is null");
        }
        JSONArray ja;
        JSONParser parser = new JSONParser();
        double distance = 0.0;
        try {
            FileReader fileReader = new FileReader("JSONFiles\\Distances.json");
            ja = (JSONArray) parser.parse(fileReader);
            for (Object obj : ja) {
                JSONObject jsonObject = (JSONObject) obj;
                String aidboxCode = (String) jsonObject.get("from");
                if (aidboxCode.compareTo(aidbox.getCode()) == 0) {
                    JSONArray aidboxes = (JSONArray) jsonObject.get("to");
                    for (Object aidboxesObj : aidboxes) {
                        JSONObject aidboxObject = (JSONObject) aidboxesObj;
                        String institutionName = (String) aidboxObject.get("name");
                        if (institutionName.compareTo("Base") == 0) {
                            Object distanceObj = aidboxObject.get("distance");
                            if (distanceObj instanceof Number) {
                                distance = ((Number) distanceObj).doubleValue();
                            }
                        }
                    }
                }
            }
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            return 0.0;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return 0.0;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return 0.0;
        }
        return distance;
    }

    /**
     * Método responsável por retornar a posição na coleção da aidbox com o
     * container recebido como parâmetro
     *
     * @param cntnr container a ser verificado
     * @return a posição da aidbox que contem o container
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

    /**
     * Método que cria uma (deep) copy da coleção de aidboxes
     *
     * @param aidbox a coleção de aidboxes a fazer uma (deep) copy
     * @return a (deep) copy da coleção de aidboxes
     */
    public AidBox[] deepCopyAidBoxes(AidBox[] aidbox) {
        if (aidbox == null) {
            return null;
        }
        AidBox[] newAidBox = new AidBox[aidbox.length];
        try {
            for (int i = 0; i < newAidBox.length; i++) {
                if (aidbox[i] != null) {
                    AidBoxImpl aidbox1 = (AidBoxImpl) aidbox[i];
                    newAidBox[i] = (AidBoxImpl) aidbox1.clone();
                }
            }
        } catch (CloneNotSupportedException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        return newAidBox;
    }

    /**
     * Método que cria uma (deep) copy do container recebido como parâmetro
     *
     * @param container o container a fazer uma (deep) copy
     * @return a (deep) copy do container
     */
    private Container deepCopyContainer(Container container) {
        if (container == null) {
            return null;
        }
        ContainerImpl newContainer = new ContainerImpl();
        try {
            ContainerImpl container1 = (ContainerImpl) container;
            newContainer = (ContainerImpl) container1.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        return newContainer;
    }

    /**
     * Compara a data fornecida com as datas dos mapas de coleta e retorna o
     * mapa de coleta mais próximo da data fornecida.
     *
     * @param dt a data a ser comparada
     * @return o mapa de coleta mais próximo da data fornecida
     */
    private PickingMap compareDate(LocalDateTime dt) {
        LocalDateTime aux = this.pickingMaps.getPickingMaps()[0].getDate();
        int index = 0;
        for (int i = 0; i < pickingMaps.getPickingMaps().length; i++) {
            if (this.pickingMaps.getPickingMaps()[i].getDate().isAfter(dt)) {
                index = i;
            }
        }
        return this.pickingMaps.getPickingMaps()[index];
    }

    /**
     * Método responsável por criar uma deep copy dos veículos da instituição
     * @return uma deep copy dos veículos da instituição
     */
    private Vehicle[] deepCopyVehicles() {
        Vehicle[] newVehicle = new Vehicle[this.vehiclesManagement.getVehicles().length];
        for (int i = 0; i < this.vehiclesManagement.getVehicles().length; i++) {
            if (this.vehiclesManagement.getVehicles()[i] != null) {
                VehicleImpl vehicle1 = (VehicleImpl) this.vehiclesManagement.getVehicles()[i];
                newVehicle[i] = (Vehicle) vehicle1.deepCopyVehicle();
            }
        }
        return newVehicle;
    }
}
