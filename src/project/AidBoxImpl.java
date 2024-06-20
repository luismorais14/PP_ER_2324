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
    private String refLocal;
    private Container[] container;
    private static int nContainers;
    private HTTPProvider httpprovider;

    /**
     * Construtor de AidBox
     *
     */
    public AidBoxImpl() {
        this.code = null;
        this.zone = null;
        this.refLocal = null;
        this.httpprovider = new HTTPProvider();
        this.container = new Container[ARRAY_SIZE];
        nContainers = 0;
    }

    /**
     * Construtor de AidBox
     *
     * @param code código da AidBox
     * @param zone Zona da AidBox
     * @param refLocal referência do local da AidBox
     *
     */
    public AidBoxImpl(String code, String zone, String refLocal) {
        this.code = code;
        this.zone = zone;
        this.httpprovider = new HTTPProvider();
        this.container = new Container[ARRAY_SIZE];
        nContainers = 0;
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
     * @throws AidBoxException exceção a ser lançada se a aidbox não existir
     * @return a distância entre a aidbox atual e a recebida como parâmetro
     */
    @Override
    public double getDistance(AidBox aidbox) throws AidBoxException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }

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
     * inserir seja null ou se a aidbox já possui um container de um determinado
     * tipo
     */
    @Override
    public boolean addContainer(Container cntnr) throws ContainerException {
        boolean aux = true;
        if (cntnr == null) {
            throw new ContainerException("Container is null");
        } else if (verifyContainerType(cntnr)) {
            throw new ContainerException("The AidBox already have a container from a given type");
        }
        if (nContainers == this.container.length - 1) {
            expandContainerArray();
        }

        if (checkContainerExistence(cntnr)) {
            aux = false;
        } else {
            this.container[nContainers] = cntnr;
            nContainers++;
        }

        return aux;
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

        for (int i = 0; i < nContainers; i++) {
            if (this.container[i].getType().equals(it)) {
                index = i;
                aux = true;
            }
        }
        if (aux) {
            tmpContainer = this.container[index];
        }
        return tmpContainer;
    }

    @Override
    public Container[] getContainers() {
        return deepCopyContainers(this.container);
    }

    @Override
    public void removeContainer(Container cntnr) throws AidBoxException {

        int index = findContainer(cntnr);
        if (cntnr == null) {
            throw new AidBoxException("Container is null");
        } else if (!this.checkContainerExistence(cntnr)) {
            throw new AidBoxException("Container does not exist");
        }
        if (index == nContainers) {
            this.container[index] = null;
            nContainers--;
        } else {
            for (int i = index; i < this.container.length - index - 1; i++) {
                this.container[i] = this.container[i + 1];
            }
            nContainers--;
        }

    }

    /**
     * método responsável por aumentar o tamanho da coleção de containers
     *
     */
    public void expandContainerArray() {
        Container[] newArray = new Container[this.container.length + 5];
        for (int i = 0; i < nContainers; i++) {
            newArray[i] = container[i];
        }
        this.container = newArray;
    }

    /**
     * Método responsável por verificar se um container já existe na coleção
     *
     * @param cntnr container a verificar
     * @return o sucesso ou insucesso da operação
     */
    private boolean checkContainerExistence(Container cntnr) {
        boolean aux = false;
        for (Container container1 : container) {
            if (container1 != null && cntnr != null) {
                if (container1.getCode().compareTo(cntnr.getCode()) == 0) {
                    aux = true;
                }
            }
        }
        return aux;
    }

    public boolean verifyContainerType(Container container) {
        if (container == null) {
            return false;
        }

        for (int i = 0; i < this.container.length; i++) {
            ContainerImpl tempContainer = (ContainerImpl) this.container[i];
            if (tempContainer != null) {
                if (tempContainer.verifyContainerType(container)) {
                    return true;
                }
            }
        }

        return false;
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
        for (int i = 0; i < this.container.length; i++) {
            if (containers[i] != null) {
                result += containers[i].toString() + "\n";
            }
        }
        return result;
    }

    private int findContainer(Container container) {
        int index = -1;
        for (int i = 0; i < this.container.length; i++) {
            if (this.container[i].equals(container)) {
                index = i;
            }
        }
        return index;
    }

    /**Método que cria uma (deep) copy da coleção de containers
     *
     * @param container a coleção de containers a fazer uma (deep) copy
     * @return a (deep) copy da coleção de containers
     */
    private Container[] deepCopyContainers(Container[] container) {
        if (container == null) {
            return null;
        }

        Container cntnr = (Container) super.clone();

        Container[] newContainer = new Container[this.container.length];

        for (int i = 0; i < this.container.length; i++) {
            Container original = container[i];
            if (original != null) {
                ContainerImpl copy = new ContainerImpl();

                copy.setCapacity(original.getCapacity());
                copy.setCode(original.getCode());
                copy.setItemType(original.getType());

                Measurement[] newMeasurement = new Measurement[original.getMeasurements().length];
                Measurement[] originalMeasurement = original.getMeasurements();
                for (int j = 0; j < originalMeasurement.length; j++) {
                    Measurement originalMeasurements = originalMeasurement[j];
                    MeasurementImpl copyMeasurement = new MeasurementImpl();

                    if (originalMeasurements != null) {
                        copyMeasurement.setDate(originalMeasurements.getDate());
                        copyMeasurement.setMeasurementValue(originalMeasurements.getValue());

                        newMeasurement[j] = copyMeasurement;
                    }
                }
                copy.setMeasurements(newMeasurement);

                newContainer[i] = copy;
            } else {
                newContainer[i] = null;
            }
        }

        return newContainer;
    }

    @Override
    public String toString() {

        return "Code: " + this.code
                + "\nZone: " + this.zone
                + "\nContentores: " + showContainers(this.container);

    }
}
