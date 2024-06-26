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
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Institution;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.InstitutionException;
import com.estg.io.Importer;
import core.AidBoxImpl;
import core.ContainerImpl;
import core.ContainerTypeImpl;
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
        this.getAidBoxes(instn);
    }

    private void getAidBoxes(Institution instn)  {
        JSONParser parser = new JSONParser();
        JSONArray aidboxesArray;
        JSONArray containerArray;
        String aidboxCode;
        String aidboxZone;
        String containerCode;
        Container[] containers;

        try {
            aidboxesArray = (JSONArray) parser.parse(new FileReader("JSONFiles\\AidBoxes.json"));
            for (int i = 0; i < aidboxesArray.size(); i++) {
                JSONObject obj = (JSONObject) aidboxesArray.get(i);
                aidboxCode = obj.get("code").toString();
                aidboxZone = obj.get("Zona").toString();
                AidBoxImpl tmpAidBox = new AidBoxImpl(aidboxCode, aidboxZone);

                containerArray = (JSONArray) obj.get("containers");
                containers = new Container[containerArray.size()];
                for (int j = 0; j < containerArray.size(); j++) {
                    containerCode = containerArray.get(j).toString();
                    ContainerImpl tmpContainer = new ContainerImpl();
                    tmpContainer.setCode(containerCode);
                    containers[j] = tmpContainer;
                }
                tmpAidBox.setContainerManagement(containers);
                AidBox aidBox = (AidBox) tmpAidBox;
                this.getContainers(aidBox);
                if (instn.addAidBox(tmpAidBox)) {
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

    private void getContainers(AidBox aidBox)  {
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
                        containerCapacity = Double.parseDouble(obj.get("capacity").toString());
                        typeStr = obj.get("type").toString();
                        containerType = ContainerTypeImpl.fromString(typeStr);

                        ContainerImpl tmpContainer = (ContainerImpl) aidBox.getContainers()[i];
                        tmpContainer.setCapacity(containerCapacity);
                        tmpContainer.setItemType(containerType);
                    }

                }

            }

        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
