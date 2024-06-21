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
    private ContainerType containerType;
    private double capacity;
    private MeasurementsManagement measurements;
    private static int nMeasurements = 0;
    private String code;
    private HTTPProvider httpprovider;

    /**Construtor de Container
     *
     */
    public ContainerImpl() {
        this.containerType = null;
        this.capacity = 0.0;
        this.code = null;
        this.measurements = new MeasurementsManagement();
        this.httpprovider = new HTTPProvider();
    }
    /**Construtor de Container
     *
     * @param it Tipo do container
     * @param capacity capacidade do conteiner
     * @param measurements array de medições
     * @param code Coordenadas da Aidbox
     */
    public ContainerImpl(ContainerType it, double capacity, MeasurementsManagement measurements, String code) {
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
        this.measurements.setMeasurements(measurements);
    }

    /**
     * Método responsável por retornar o código do container
     * @return o código do container
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Método responsável por retornar a capacidade do container
     * @return a capacidade do container
     */
    @Override
    public double getCapacity() {
        return this.capacity;
    }

    /**
     * Método responsável por retornar o tipo do container
     * @return o tipo do container
     */
    @Override
    public ContainerType getType() {
        return this.containerType;
    }

    /**
     * Método responsável por retornar uma (deep) copy das medições existentes
     * @return uma (deep) copy das medições existentes
     */
    @Override
    public Measurement[] getMeasurements() {
        return this.deepCopyMeasurements(this.measurements.getMeasurements());
    }

    /**
     * Método responsável por retornar uma (deep) copy dos measurements para uma data específica
     * @param ld data a procurar pela medição
     * @return uma (deep) copy das medições existentes para a data dada
     */
    @Override
    public Measurement[] getMeasurements(LocalDate ld) {
        Measurement[] measurementByDate = new Measurement[this.measurements.getMeasurements().length];
        int nAdd = 0;
        for (Measurement measurement : this.measurements.getMeasurements()) {
            if (measurement.getDate().toLocalDate().equals(ld)) {
                measurementByDate[nAdd] = measurement;
                nAdd++;
            }
        }
        return deepCopyMeasurements(measurementByDate);
    }

    /**
     * Método responsável por adicionar uma measurement à coleção
     * @param msrmnt a measurement a ser adicionada
     * @return o sucesso ou insucesso da operação
     * @throws MeasurementException exceção a ser lançada se a medição for nula, se valor da medição for menor que 0, se a data da medição for anterior á mais antiga ou se já existir uma medição para essa data mas o valor for diferente
     */
    @Override
    public boolean addMeasurement(Measurement msrmnt) throws MeasurementException {
        if (msrmnt == null) {
            throw new MeasurementException("Measurement is null");
        } else if (this.measurements.verifyMeasurementValue(msrmnt)) {
            throw new MeasurementException("Value is lesser than 0");
        } else if (msrmnt.getDate().isBefore(this.measurements.getMeasurements()[nMeasurements].getDate())) {
            throw new MeasurementException("The date is before the existing last measurement date");
        } else if (this.measurements.compareMeasurements(this.measurements.Searchbydate(msrmnt), msrmnt)) {
            throw new MeasurementException("For the given date the measurement already exists but the values are different");
        }

        return this.measurements.addMeasurement(msrmnt);
    }

    /**
     * Método responsável por verificar se o tipo do container atual é igual ao tipo do container recebido como parâmetro
     * @param obj container a ser verificado o tipo
     * @return o sucesso ou insucesso da operação
     */
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

        if (!this.getType().equals(other.getType())) {
            return false;
        }
        return true;
    }

    /**
     * Método que cria uma (deep) copy da coleção de measurements
     *
     * @param measurements a coleção de measurements a fazer uma (deep) copy
     * @return a (deep) copy da coleção de measurements
     */
    private Measurement[] deepCopyMeasurements(Measurement[] measurements) {
        if (measurements == null) {
            return null;
        }
        Measurement[] newMeasurements = new Measurement[measurements.length];
        try {
            for (int i = 0; i < newMeasurements.length; i++) {
                MeasurementImpl measurement1 = (MeasurementImpl) measurements[i];
                newMeasurements[i] = (Measurement) measurement1.clone();
            }

            } catch (CloneNotSupportedException ex) {
                System.out.println(ex.getMessage());
        }
        return newMeasurements;
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
