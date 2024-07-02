/*
* Nome: Francisco Morais de Oliveira
* Número: 8230204
* Turma: T3
*
* Nome: Luís André Nunes Morais
* Número: 8230258
* Turma: T3
 */
package management;

import com.estg.pickingManagement.Report;
import java.time.LocalDateTime;


public class ReportImpl implements Report {

    private int usedVehicles;
    private int pickedContainers;
    private double totalDistance;
    private double totalDuration;
    private int nonPickedContainers;
    private int notUsedVehicles;
    private LocalDateTime reportDate;

    /**
     * Construtor de Route
     */
    public ReportImpl() {
        this.usedVehicles = 0;
        this.pickedContainers = 0;
        this.totalDistance = 0.0;
        this.totalDuration = 0.0;
        this.nonPickedContainers = 0;
        this.notUsedVehicles = 0;
        this.reportDate = null;
    }

    /**
     * Construtor de Vehicle
     * @param uVehicle o número de veículos utilizados
     * @param tDistance a distância total da rota
     * @param tDuration a duração total da rota
     * @param nPickedContainer o numero de contentores usados
     * @param pContainers o numero de containers usados
     * @param nUsedVehicles o numero de veiculos usados
     * @param reportDate a data do report
     */
    public ReportImpl(int uVehicle, int pContainers, double tDistance, double tDuration, int nPickedContainer, int nUsedVehicles, LocalDateTime reportDate) {
        this.usedVehicles = uVehicle;
        this.pickedContainers = pContainers;
        this.totalDistance = tDistance;
        this.totalDuration = tDuration;
        this.nonPickedContainers = nPickedContainer;
        this.notUsedVehicles = nUsedVehicles;
        this.reportDate = reportDate;
    }

    /**
     * Método responsável por especificar o número de veículos usados
     * @param usedVehicles o número de veículos usados
     */
    public void setUsedVehicles(int usedVehicles) {
        this.usedVehicles = usedVehicles;
    }

    /**
     * Método responsável por especificar o número de containers recolhidos
     * @param pickedContainers o número de containers recolhidos
     */
    public void setPickedContainers(int pickedContainers) {
        this.pickedContainers = pickedContainers;
    }

    /**
     * Método responsável por especificar a distância total
     * @param totalDistance a distância total
     */
    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    /**
     * Método responsável por especificar a duração total
     * @param totalDuration a duração total
     */
    public void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;
    }

    /**
     * Método responsável por especificar o número de containers recolhidos
     * @param nonPickedContainers o número de containers não recolhidos
     */
    public void setNonPickedContainers(int nonPickedContainers) {
        this.nonPickedContainers = nonPickedContainers;
    }

    /**
     * Método responsável por especificar o número de veículos não usados
     * @param notUsedVehicles o número de veículos não usados
     */
    public void setNotUsedVehicles(int notUsedVehicles) {
        this.notUsedVehicles = notUsedVehicles;
    }

    /**
     * Método responsável por especificar o número a data do relatório
     * @param reportDate a data do relatório
     */
    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    /**
     * Método responsável por retornar o número de veículos usados
     * @return o número de veículos usados
     */
    @Override
    public int getUsedVehicles() {
        return this.usedVehicles;
    }

    /**
     * Método responsável por retornar o número de containers recolhidos
     * @return o número de containers recolhidos
     */
    @Override
    public int getPickedContainers() {
        return this.pickedContainers;
    }

    /**
     * Método responsável por retornar a distância total
     * @return a distância total
     */
    @Override
    public double getTotalDistance() {
        return this.totalDistance;
    }

    /**
     * Método responsável por retornar a duração total
     * @return a duração total
     */
    @Override
    public double getTotalDuration() {
        return this.totalDuration;
    }

    /**
     * Método responsável por retornar o número de containers não recolhidos
     * @return o número de containers não recolhidos
     */
    @Override
    public int getNonPickedContainers() {
        return this.nonPickedContainers;
    }

    /**
     * Método responsável por retornar o número de veículos não usados
     * @return o número de veículos não usados
     */
    @Override
    public int getNotUsedVehicles() {
        return this.notUsedVehicles;
    }

    /**
     * Método responsável por retornar a data do relatório
     * @return a data do relatório
     */
    @Override
    public LocalDateTime getDate() {
        return this.reportDate;
    }

    @Override
    public String toString() {
        return "ReportImpl{" +
                "usedVehicles=" + this.usedVehicles +
                ", pickedContainers=" + this.pickedContainers +
                ", totalDistance=" + this.totalDistance +
                ", totalDuration=" + this.totalDuration +
                ", nonPickedContainers=" + this.nonPickedContainers +
                ", notUsedVehicles=" + this.notUsedVehicles +
                ", reportDate=" + this.reportDate +
                '}';
    }
}
