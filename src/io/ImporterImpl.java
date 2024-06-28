/*
* Nome: Francisco Morais de Oliveira
* Número: 8230204
* Turma: T3
*
* Nome: Luís André Nunes Morais
* Número: 8230258
* Turma: T3
 */
package io;

import com.estg.core.ContainerType;
import com.estg.core.Institution;
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.InstitutionException;
import com.estg.core.exceptions.MeasurementException;
import com.estg.core.exceptions.VehicleException;
import com.estg.io.Importer;
import core.*;
import management.VehicleImpl;
import management.VehicleManagement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;


public class ImporterImpl implements Importer {

    ContainerManagement containerManagement = new ContainerManagement();
    VehicleManagement vehicleManagement = new VehicleManagement();
    MeasurementsManagement measurementsManagement = new MeasurementsManagement();



    @Override
    public void importData(Institution instn) throws FileNotFoundException, IOException, InstitutionException {
        if (instn == null) {
            throw new InstitutionException("Institution object is null");
        }
        try {
            this.readContainerTypes();
            this.readVehicles();
            this.readContrainers();
            this.readMeasurements();
            //todo ler aidboxes
        } catch (VehicleException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * Método responsável por ler os veículos da WEBAPI
     * @throws VehicleException exceção a ser lançada caso o veículo a ser adicionado à coleção seja null
     */
    private void readVehicles() throws VehicleException {
        JSONParser parser = new JSONParser();
        JSONArray ja;
        String vehicleCode;

        try {
            ja = (JSONArray) parser.parse(new FileReader("JSONFiles\\Vehicles.json"));
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jsonObject = (JSONObject) ja.get(i);
                vehicleCode = jsonObject.get("code").toString();
                VehicleImpl vehicle = new VehicleImpl(vehicleCode);
                vehicle.setCapacities();
                if (vehicleManagement.addVehicle(vehicle)) {
                    System.out.println("Veículo adicionado com sucesso");
                } else {
                    System.out.println("Veículo não adicionado");
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método responsável por ler as aidboxes da WEBAPI
     * @param instn
     */
    private void readAidBoxes(Institution instn) {
        JSONParser parser = new JSONParser();
        JSONArray aidboxesArray;
        JSONArray containerArray;
        String aidboxCode;
        String aidboxZone;
        String containerCode;
        ContainerImpl[] containers;

        try {
            aidboxesArray = (JSONArray) parser.parse(new FileReader("JSONFiles\\AidBoxes.json"));
            for (int i = 0; i < aidboxesArray.size(); i++) {
                JSONObject obj = (JSONObject) aidboxesArray.get(i);
                aidboxCode = obj.get("code").toString();
                aidboxZone = obj.get("Zona").toString();
                AidBoxImpl AidBox = new AidBoxImpl(aidboxCode, aidboxZone);

                containerArray = (JSONArray) obj.get("containers");
                containers = new ContainerImpl[containerArray.size()];
                for (int j = 0; j < containerArray.size(); j++) {
                    containerCode = containerArray.get(j).toString();
                    //todo procurar container por instância já criada
                    containers[j] = new ContainerImpl();
                    containers[j].setCode(containerCode);
                }
                AidBox.setContainerManagement(containers);
                if (!instn.addAidBox(AidBox)) {
                    System.out.println("Erro ao adicionar AidBox");
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } catch (AidBoxException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método responsável por ler os containers da WEBAPI
     */
    private void readContrainers() {
        JSONParser parser = new JSONParser();
        JSONArray containerArray;
        double containerCapacity;
        ContainerType containerType;
        String typeStr;
        String containerCode;

        try {
            containerArray = (JSONArray) parser.parse(new FileReader("JSONFiles\\Containers.json"));
            for (int i = 0; i < containerArray.size(); i++) {
                JSONObject obj = (JSONObject) containerArray.get(i);
                containerCode = obj.get("code").toString();
                typeStr = obj.get("type").toString();
                containerType = TypesManagement.findByType(typeStr);
                containerCapacity = ((Number) obj.get("capacity")).doubleValue();
                ContainerImpl container = new ContainerImpl(containerType, containerCapacity, containerCode);
                if (!containerManagement.addContainer(container)) {
                    System.out.println("Erro ao adicionar container");
                }
            }
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método responsável carregar os tipos de containers existentes
     */
    private void readContainerTypes() {
        TypesManagement.setContainerTypes();
    }

    /**
     * Método responsável por ler as measurements da WEBAPI
     */
    private void readMeasurements() {
        JSONParser parser = new JSONParser();
        JSONArray measurementsArray;
        double measurementValue;
        String date;
        LocalDateTime measurementDate = null;

        try {
            measurementsArray = (JSONArray) parser.parse(new FileReader("JSONFiles\\Readings.json"));
            for (int i = 0; i < measurementsArray.size(); i++) {
                JSONObject obj = (JSONObject) measurementsArray.get(i);
                for (int j = 0; j < containerManagement.getContainers().length; j++) {
                    if (this.containerManagement.getContainers()[j].getCode().compareTo(obj.get("contentor").toString()) == 0) {
                        date = obj.get("data").toString();
                        try {
                            Instant instant = Instant.parse(date);
                            measurementDate = LocalDateTime.ofInstant(instant, java.time.ZoneOffset.UTC);
                        } catch (DateTimeParseException ex) {
                            System.out.println(ex.getMessage());
                        }
                        measurementValue = ((Number) obj.get("valor")).doubleValue();
                        MeasurementImpl measurement = new MeasurementImpl(measurementDate, measurementValue);
                        if (!measurementsManagement.addMeasurement(measurement)) {
                            System.out.println("Measurement não adicionado à coleção");
                            break;
                        }
                        //todo resolver problema ao adicionar a measurement ao container, na instância de container, o array de measurements é null, daí dar throw à exceção ao adicionar
                        if (!this.containerManagement.getContainers()[j].addMeasurement(measurement)) {
                            System.out.println("Erro ao adicionar measurement ao container");
                        }
                    }
                }
            }
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (MeasurementException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
