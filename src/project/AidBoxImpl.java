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
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.io.HTTPProvider;

import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AidBoxImpl implements AidBox {

    private final int ARRAY_SIZE = 10;

    private String code;
    private String zone;
    private HTTPProvider httpprovider = new HTTPProvider();
    private ContainerManagement containerManagement = new ContainerManagement();

    /**
     * Construtor de AidBox
     */
    public AidBoxImpl() {
        this.code = null;
        this.zone = null;
    }

    /**
     * Construtor de AidBox
     *
     * @param code código da AidBox
     * @param zone Zona da AidBox
     */
    public AidBoxImpl(String code, String zone) {
        this.code = code;
        this.zone = zone;
    }

    /**
     * Método responsável por especificar o código da AidBox
     * @param code o código da AidBox
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Método responsável por especificar a zona da AidBox
     * @param zone a zona da AidBox
     */
    public void setZone(String zone) {
        this.zone = zone;
    }

    /**
     * Método responsável por especificar o array de containers
     * @param containers a coleção de containers
     */
    public void setContainerManagement(Container[] containers) {
        this.containerManagement.setContainers(containers);
    }

    /**
     * Metodo responsável por retornar o código da AidBox
     *
     * @return código da AidBox
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Metodo responsável por retornar a zona da AidBox
     *
     * @return zona da AidBox
     */
    @Override
    public String getZone() {
        return this.zone;
    }

    /**
     * Metodo responsável por retornar a distância entre a aidbox atual e o
     * parâmetro aidbox
     *
     * @param aidbox aidbox para calcular a distância
     * @return a distância entre a aidbox atual e a recebida como parâmetro
     * @throws AidBoxException exceção a ser lançada se a aidbox não existir
     */
    @Override
    public double getDistance(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("AidBox is null");
        }

        JSONArray ja;
        JSONParser parser = new JSONParser();
        String jsonString = this.httpprovider.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/distances");
        double distance = 0.0;
        try {
            ja = (JSONArray) parser.parse(jsonString);
            for (Object obj : ja) {
                JSONObject jsonObject = (JSONObject) obj;
                String originalAidBox = (String) jsonObject.get("from");
                if (originalAidBox.compareTo(this.getCode()) == 0) {
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
            return 0.0;
        }
        return distance;
    }

    /**
     * Método responsável por retornar a duração entre a aidbox atual e a aidbox recebida como parâmetro
     *
     * @param aidbox aidbox a calcular a duração
     * @return a duração entre as aidboxes
     * @throws AidBoxException se a aidbox não existir
     */
    @Override
    public double getDuration(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("AidBox is null");
        }

        JSONArray ja;
        JSONArray aidboxes;
        JSONParser parser = new JSONParser();
        String jsonString = this.httpprovider.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/distances");
        double duration = 0.0;
        try {
            ja = (JSONArray) parser.parse(jsonString);
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jsonObject = (JSONObject) ja.get(i);
                String originalAidBox = (String) jsonObject.get("from");
                if (originalAidBox.compareTo(this.getCode()) == 0) {
                    aidboxes = (JSONArray) jsonObject.get("to");
                    for (int j = 0; j < aidboxes.size(); j++) {
                        JSONObject aidboxObject = (JSONObject) aidboxes.get(j);
                        String aidboxCode = (String) aidboxObject.get("name");
                        if (aidboxCode.compareTo(aidbox.getCode()) == 0) {
                            Object durationObj = aidboxObject.get("duration");
                            if (durationObj instanceof Number) {
                                duration = ((Number) durationObj).doubleValue();
                            }
                        }
                    }
                }
            }
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            return 0.0;
        }
        return duration;
    }

    /**
     * Metodo responsável por adicionar um container à AidBox
     *
     * @param cntnr o contentor a adicionar
     * @return retorna o sucesso ou o insucesso da operação
     * @throws ContainerException exceção a ser lançada caso o container a
     *                            inserir seja null ou se a aidbox já possui um container de um determinado
     *                            tipo
     */
    @Override
    public boolean addContainer(Container cntnr) throws ContainerException {
        if (cntnr == null) {
            throw new ContainerException("Container is null");
        } else if (this.containerManagement.verifyContainerType(cntnr)) {
            throw new ContainerException("The AidBox already have a container from a given type");
        }
        return this.containerManagement.addContainer(cntnr);
    }

    /**
     * Método responsável por retornar um container dado um determinado tipo.
     *
     * @param it tipo do container a retornar
     * @return o container dado o tipo recebido como parâmetro
     */
    @Override
    public Container getContainer(ContainerType it) {
        int index = 0;
        boolean aux = false;
        Container tmpContainer = null;

        for (int i = 0; i < containerManagement.getnContainers(); i++) {
            if (this.containerManagement.getContainers()[i].getType().equals(it)) {
                index = i;
                aux = true;
            }
        }
        if (aux) {
            tmpContainer = this.containerManagement.getContainers()[index];
        }
        return tmpContainer;
    }

    /**
     * Método que retorna uma (deep) copy dos containers na aidbox
     *
     * @return uma (deep) copy dos containers na aidbox
     */
    @Override
    public Container[] getContainers() {
        return deepCopyContainers(this.containerManagement.getContainers());
    }

    /**
     * Método responsável por remover um container da coleção
     *
     * @param cntnr o container a ser removido
     * @throws AidBoxException se o container recebido como parâmetro não existir
     */
    @Override
    public void removeContainer(Container cntnr) throws AidBoxException {
        if (cntnr == null) {
            throw new AidBoxException("Container is null");
        } else if (!this.containerManagement.checkContainerExistence(cntnr)) {
            throw new AidBoxException("Container does not exist");
        }

        this.containerManagement.removeContainer(cntnr);
    }

    /**
     * Método responsável por mostrar em formato String os containers da aidbox.
     *
     * @param containers a coleção de containers a mostrar
     * @return O texto em formato String da coleção de contrainers
     */
    private String showContainers(Container[] containers) {
        if (containers == null) {
            return "null";
        }

        String result = "";
        for (int i = 0; i < this.containerManagement.getContainers().length; i++) {
            if (containers[i] != null) {
                result += containers[i].toString() + "\n";
            }
        }
        return result;
    }


    /**
     * Método que cria uma (deep) copy da coleção de containers
     *
     * @param container a coleção de containers a fazer uma (deep) copy
     * @return a (deep) copy da coleção de containers
     */
    private Container[] deepCopyContainers(Container[] container) {
        if (container == null) {
            return null;
        }
        Container[] newContainer = new Container[container.length];
        try {
            for (int i = 0; i < newContainer.length; i++) {
                if (container[i] != null) {
                    ContainerImpl container1 = (ContainerImpl) container[i];
                    newContainer[i] = (Container) container1.clone();
                }
            }
        } catch (CloneNotSupportedException ex) {
            System.out.println(ex.getMessage());
        }

        return newContainer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AidBoxImpl other = (AidBoxImpl) obj;
        return this.code.compareTo(other.getCode()) == 0;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        AidBoxImpl clone = (AidBoxImpl) super.clone();
        clone.setCode(this.getCode());
        clone.setZone(this.getZone());
        clone.setContainerManagement(this.getContainers());
        return clone;
    }

    @Override
    public String toString() {
        return "Code: " + this.code
                + "\nZone: " + this.zone
                + "\nContentores: " + showContainers(this.containerManagement.getContainers());

    }
}
