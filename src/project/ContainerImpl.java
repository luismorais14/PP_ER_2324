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

import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.MeasurementException;
import com.estg.io.HTTPProvider;

import java.time.LocalDate;
import java.util.Objects;

public class ContainerImpl implements Container {
    private final int ARRAY_SIZE = 10;

    private ContainerType containerType;
    private double capacity;
    private Measurement[] measurements;
    private static int nMeasurements = 0;
    private String code;
    private HTTPProvider httpprovider;

    /**Construtor de Container
     *
     */
    public ContainerImpl() {
        this.containerType = null;
        this.capacity = 0.0;
        this.measurements = new Measurement[ARRAY_SIZE];
        this.code = null;
        this.httpprovider = new HTTPProvider();
    }
    /**Construtor de Container
     *
     * @param it Tipo do container
     * @param capacity capacidade do conteiner
     * @param measurements array de medições
     * @param code Coordenadas da Aidbox
     */
    public ContainerImpl(ContainerType it, double capacity, Measurement[] measurements, String code) {
        this.containerType = it;
        this.capacity = capacity;
        this.measurements = measurements;
        this.code = code;
        this.httpprovider = new HTTPProvider();
    }
    /**Metodo responsável por especificar o código do container
     *
     * @param code código do container
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**Metodo responsável por especificar o tipo do container
     *
     * @param itemType tipo do container
     */
    public void setItemType(ContainerType itemType) {
        this.containerType = itemType;
    }
    /**Metodo responsável por especificar o tipo do container
     *
     * @param capacity tipo do container
     */
    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }
    /**Metodo responsável por especificar o tipo do container
     *
     * @param measurements array de medições
     */
    public void setMeasurements(Measurement[] measurements) {
        this.measurements = measurements;
    }

    @Override
    public String getCode() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getCapacity() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ContainerType getType() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Measurement[] getMeasurements() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Measurement[] getMeasurements(LocalDate ld) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addMeasurement(Measurement msrmnt) throws MeasurementException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //Alterar o .equals dos tipos
    public boolean verifyContainerType(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Container other = (Container) obj;

        if (!Objects.equals(this.getType(), other.getType())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Container other = (Container) obj;

        if (!this.getType().equals(other.getType())) {
            return false;
        }
        if (this.getCode().compareTo(other.getCode()) != 0) {
            return false;
        }

        return true;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        ContainerImpl clone = (ContainerImpl) super.clone();
        clone.setCapacity(this.getCapacity());
        clone.setCode(this.getCode());
        clone.setItemType(this.getType());
        clone.setMeasurements(this.getMeasurements());
        return clone;
    }


}
