/*
 * Nome: Francisco Morais de Oliveira
 * Número: 8230204
 * Turma: T3
 *
 * Nome: Luís André Nunes Morais
 * Número: 8230258
 * Turma: T3
 */
package core;

import com.estg.core.Measurement;

import java.time.LocalDateTime;
import java.util.Objects;


public class MeasurementImpl implements Measurement{

    private LocalDateTime date;
    private double measurementValue;

    /**
     * Construtor de Measurement
     */
    public MeasurementImpl() {
        this.date = null;
        this.measurementValue = 0.0;
    }

    /**
     * Construtor de Measurement
     * @param measurement medição
     */
    public MeasurementImpl(MeasurementImpl measurement) {
        this.date = measurement.getDate();
        this.measurementValue = measurement.getValue();
    }



    /**
     * Construtor de Measurement
     *
     * @param date hórario de medição
     * @param mv   valor da medição
     */
    public MeasurementImpl(LocalDateTime date, double mv) {
        this.date = date;
        this.measurementValue = mv;
    }

    /**
     * Metodo responsável por especificar a hórario de medição do objeto
     *
     * @param date hórario de medição
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Metodo responsável por especificar a hórario de medição do objeto
     *
     * @param measurementValue valor da medição
     */
    public void setMeasurementValue(double measurementValue) {
        this.measurementValue = measurementValue;
    }

    /**
     * Metodo responsável por retornar a data da medição
     *
     * @return a data da medição
     */
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Metodo responsável por retornar o valor medição
     *
     * @return o valor da medição
     */
    @Override
    public double getValue() {
        return this.measurementValue;
    }

    @Override
    public String toString() {
        return "Date: " + this.getDate()
                + "\nMeasurement Value: " + this.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MeasurementImpl other = (MeasurementImpl) o;
        if (this.measurementValue != other.measurementValue || !Objects.equals(this.date, other.date)) {
            return false;
        }

        return true;
    }

    protected Object getClone() {
        return new MeasurementImpl(this);
    }
}
