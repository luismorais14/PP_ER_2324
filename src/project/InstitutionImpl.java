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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InstitutionImpl implements Institution {

    private final int ARRAY_SIZE = 10;

    private String InstitutionName;
    private PickingMap[] pickingMaps;
    private AidBoxManagement aidboxes = new AidBoxManagement();
    private static int nAidBox;
    private MeasurementsManagement measurements = new MeasurementsManagement();
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
    public InstitutionImpl(String name, PickingMap[] pm, AidBox[] aidbox, Measurement[] measurement, Vehicle[] vehicle) {
        this.InstitutionName = name;
        this.pickingMaps = pm;
        this.aidboxes.setAidBox(aidbox);
        this.measurements.setMeasurements(measurement);
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
     * @param pickingMaps os novos mapas de picking a serem atribuídos à
     * instituição
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
     * @param vehicle os novos veículos habilitados a serem atribuídos à
     * instituição
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
     * @throws AidBoxException se a caixa de ajuda for nula ou contiver
     * containers duplicados de um determinado tipo
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
     * @return true se a medição foi adicionada com sucesso, false caso
     * contrário
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

    /**
     * Obtém uma cópia profunda das caixas de ajuda associadas à instituição.
     *
     * @return uma cópia profunda das caixas de ajuda associadas à instituição
     */
    @Override
    public AidBox[] getAidBoxes() {
        return deepCopyAidBoxes(this.aidboxes.getAidBoxes()); // Verifica se esta correto
    }

    @Override
    public Container getContainer(AidBox aidbox, ContainerType ct) throws ContainerException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vehicle[] getVehicles() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

        if (enableVehicles == null) {
            enableVehicles = new Vehicle[10];
            nEnableVehicles = 0;
        }

        if (nEnableVehicles >= enableVehicles.length) {
            expandVehicleArray();
        }

        for (Vehicle veh : enableVehicles) {
            if (veh != null && veh.equals(vhcl)) {
                aux = false;
            }
        }

        enableVehicles[nEnableVehicles] = vhcl;
        nEnableVehicles++;
        return aux;
    }

    @Override
    public void disableVehicle(Vehicle vhcl) throws VehicleException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void enableVehicle(Vehicle vhcl) throws VehicleException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Obtém as mapas de picking associadas à instituição.
     *
     * @return os mapas de picking associados à instituição
     */
    @Override
    public PickingMap[] getPickingMaps() {
        return this.pickingMaps;
    }

    /**
     * Obtém os mapas de picking associados à instituição dentro de um intervalo
     * de datas especificado.
     *
     * @param ldt a data de início do intervalo de datas
     * @param ldt1 a data de término do intervalo de datas
     * @return os mapas de picking associados à instituição dentro do intervalo
     * de datas especificado
     */
    @Override
    public PickingMap[] getPickingMaps(LocalDateTime ldt, LocalDateTime ldt1) {
        PickingMap[] tmpPickingMap = new PickingMap[this.pickingMaps.length];
        int tmpNPickingMaps = 0;
        for (int i = 0; i < pickingMaps.length; i++) {
            if (this.pickingMaps[i].getDate().isBefore(ldt1) && this.pickingMaps[i].getDate().isAfter(ldt)) {
                tmpPickingMap[tmpNPickingMaps] = pickingMaps[i];
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
        if (this.pickingMaps.length == 0) {
            throw new PickingMapException("There are no picking maps in the institution.");
        }
        for (int i = 0; i < pickingMaps.length; i++) {
            pm = compareDate(this.pickingMaps[i].getDate());
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
        boolean aux = false;
        if (!verifyPickingMapExistence(pm)) {
            this.pickingMaps[nPickingMaps] = pm;
            aux = true;
            nPickingMaps++;
        }
        return aux;
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
        String jsonString = this.httpProvider.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/distances");
        double distance = 0.0;
        try {
            ja = (JSONArray) parser.parse(jsonString);
            for (Object obj : ja) {
                JSONObject jsonObject = (JSONObject) obj;
                String originalAidBox = (String) jsonObject.get("from");
                if (originalAidBox.compareTo(this.InstitutionName) == 0) { // Troquei o getCode por this.Instituition name para calcular a distancia da instituicao á aidbox
                    JSONArray aidboxes = (JSONArray) jsonObject.get("to");
                    for (Object aidboxesObj : aidboxes) {
                        JSONObject aidboxObject = (JSONObject) aidboxesObj;
                        String aidboxCode = (String) aidboxObject.get("name");
                        if (aidboxCode.compareTo(aidbox.getCode()) == 0) {
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
        }
        return distance;
    }

    /**
     * Método responsável por retornar a posição na coleção da aidbox com o
     * container recebido como parâmetro
     *
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
        }

        return newAidBox;
    }

    /**
     * Compara a data fornecida com as datas dos mapas de coleta e retorna o
     * mapa de coleta mais próximo da data fornecida.
     *
     * @param dt a data a ser comparada
     * @return o mapa de coleta mais próximo da data fornecida
     */
    private PickingMap compareDate(LocalDateTime dt) {
        LocalDateTime aux = this.pickingMaps[0].getDate();
        int index = 0;
        for (int i = 0; i < pickingMaps.length; i++) {
            if (this.pickingMaps[i].getDate().compareTo(dt) > 0) { // Retorna 0 se for igual/ Data anterior retorna número menor que 0/ Data maior retorna valor maior que 0
                aux = this.pickingMaps[i].getDate();
                index = i;
            }
        }
        return this.pickingMaps[index];
    }

    /**
     * Verifica a existência de uma rota de coleta em um mapa de coleta com base
     * nas rotas de caixas de ajuda associadas.
     *
     * @param pm o mapa de coleta a ser verificado
     * @return true se uma rota de coleta com base nas rotas de caixas de ajuda
     * associadas já existir no mapa de coleta, caso contrário, false
     */
    private boolean verifyPickingMapExistence(PickingMap pm) {
        boolean aux = false;
        for (int i = 0; i < this.pickingMaps.length; i++) {
            for (int j = 0; j < this.pickingMaps[i].getRoutes().length; j++) {
                if (this.pickingMaps[i].getRoutes()[j].containsAidBox(pm.getRoutes()[i].getRoute()[j])) {
                    aux = true;
                }
            }
        }
        return aux;
    }

        /**
     * Expande a capacidade do array de veículos habilitados.
     *
     */
    public void expandVehicleArray() {
        Vehicle[] newArray = new Vehicle[ARRAY_SIZE + 5];
        for (int i = 0; i < nEnableVehicles; i++) {
            newArray[i] = enableVehicles[i];
        }
        this.enableVehicles = newArray;
    }

    
    
}
