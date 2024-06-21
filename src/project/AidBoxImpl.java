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
    private HTTPProvider httpprovider;
    private ContainerManagement containerManagement;

    /**
     * Construtor de AidBox
     */
    public AidBoxImpl() {
        this.code = null;
        this.zone = null;
        this.httpprovider = new HTTPProvider();
        this.containerManagement = new ContainerManagement();
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
        this.httpprovider = new HTTPProvider();
        this.containerManagement = new ContainerManagement();
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

        for (int i = 0; i < ContainerManagement.getnContainers(); i++) {
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

    @Override
    public Container[] getContainers() {
        return deepCopyContainers(this.containerManagement.getContainers());
    }

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
                ContainerImpl container1 = (ContainerImpl) container[i];
                newContainer[i] = (Container) container1.clone();
            }
        } catch (CloneNotSupportedException ex) {
            System.out.println(ex.getMessage());
        }

        return newContainer;
    }

    @Override
    public String toString() {

        return "Code: " + this.code
                + "\nZone: " + this.zone
                + "\nContentores: " + showContainers(this.containerManagement.getContainers());

    }
}
