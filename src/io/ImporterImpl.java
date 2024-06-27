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

import com.estg.core.AidBox;
import com.estg.core.ContainerType;
import com.estg.core.Institution;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.InstitutionException;
import com.estg.core.exceptions.VehicleException;
import com.estg.io.Importer;
import core.AidBoxImpl;
import core.ContainerImpl;
import core.TypesManagement;
import management.VehicleImpl;
import management.VehicleManagement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ImporterImpl implements Importer {


    @Override
    public void importData(Institution instn) throws FileNotFoundException, IOException, InstitutionException {
        if (instn == null) {
            throw new InstitutionException("Institution object is null");
        }
        try {
            this.readContainerTypes();
            this.readVehicles(instn);
            //todo ler containers
            //todo ler measurements
            this.readAidBoxes(instn);
        } catch (ContainerException ex) {
            System.out.println(ex.getMessage());
        } catch (VehicleException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void readVehicles(Institution instn) throws VehicleException {
        JSONParser parser = new JSONParser();
        JSONArray ja;
        String vehicleCode;
        VehicleManagement vehicleManagement = new VehicleManagement();

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

    private void readAidBoxes(Institution instn) throws ContainerException {
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
                if (instn.addAidBox(AidBox)) {
                    System.out.println("AidBox added successfully");
                } else {
                    System.out.println("AidBox not added successfully");
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

    private void getContainers(AidBox aidBox) throws ContainerException {
        JSONParser parser = new JSONParser();
        JSONArray containerArray;
        double containerCapacity;
        ContainerType containerType;
        String typeStr;

        try {
            containerArray = (JSONArray) parser.parse("JSONFiles\\Containers.json");
            for (int i = 0; i < aidBox.getContainers().length; i++) {
                for (int j = 0; j < containerArray.size(); j++) {
                    JSONObject obj = (JSONObject) containerArray.get(i);
                    if (aidBox.getContainers()[i].getCode().compareTo(obj.get("code").toString()) == 0) {
                        containerCapacity = (Double) obj.get("capacity");
                        typeStr = obj.get("type").toString();
                        containerType = TypesManagement.findByType(typeStr);

                        ContainerImpl tmpContainer = (ContainerImpl) aidBox.getContainers()[i];
                        tmpContainer.setCapacity(containerCapacity);
                        tmpContainer.setItemType(containerType);
                        tmpContainer.setCode(aidBox.getContainers()[i].getCode());
                        aidBox.addContainer(tmpContainer);
                    }
                }
            }
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método responsável carregar os tipos de containers existentes
     */
    private void readContainerTypes() {
        TypesManagement.setContainerTypes();
    }

}
