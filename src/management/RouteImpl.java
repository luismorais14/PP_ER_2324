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

import com.estg.core.AidBox;
import com.estg.core.exceptions.AidBoxException;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;
import core.AidBoxImpl;
import core.AidBoxManagement;


public class RouteImpl implements Route {

    private AidBoxManagement aidBoxManagement = new AidBoxManagement();
    private Vehicle vehicle;
    private Report report;

    /**
     * Construtor de RouteImpl
     */
    public RouteImpl() {
        this.vehicle = null;
        this.report = null;
    }

    /**
     * Construtor de RouteImpl
     * @param vehicle o veículo da rota
     * @param report o report da rota
     * @param aidBoxes a coleção de aidboxes da rota
     */
    public RouteImpl(Vehicle vehicle, Report report, AidBox[] aidBoxes) {
        this.vehicle = vehicle;
        this.report = report;
        this.aidBoxManagement.setAidBox(aidBoxes);
    }

    /**
     * Método responsável por especificar o veículo da rota
     * @param vehicle o veículo da rota
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Método responsável por especificar o report da rota
     * @param report o report da rota
     */
    public void setReport(Report report) {
        this.report = report;
    }

    /**
     * Método responsável por especificar a coleção de aidboxes da rota
     * @param aidbox a coleção de aidboxes
     */
    public void setAidBox(AidBox[] aidbox) {
        this.aidBoxManagement.setAidBox(aidbox);
    }

    /**
     * Método responsável por adicionar uma aidbox à rota
     *
     * @param aidbox aidbox a ser adicionada
     * @throws RouteException exceção a ser lançada caso a aidbox seja null, caso a aidbox já esteja na rota ou caso a aidbox não seja compatível
     */
    @Override
    public void addAidBox(AidBox aidbox) throws RouteException {
        if (aidbox == null) {
            throw new RouteException("AidBox is null");
        }
        if (this.aidBoxManagement.verifyAidBoxExistence(aidbox)) {
            throw new RouteException("AidBox already exists");
        }
        if (!this.aidBoxManagement.verifyCompatibility(aidbox, this.vehicle)) {
            throw new RouteException("AidBox is not compatible");
        }
        this.aidBoxManagement.addAidBox(aidbox);
    }

    /**
     * Método responsável por remover uma AidBox da rota.
     *
     * @param aidbox a AidBox a ser removida da rota
     * @return a `AidBox` removida da rota
     * @throws RouteException exceção a ser lançada se a `AidBox` for nula ou não estiver na rota
     */
    @Override
    public AidBox removeAidBox(AidBox aidbox) throws RouteException {
        if (aidbox == null) {
            throw new RouteException("AidBox is null");
        }
        if (this.aidBoxManagement.verifyAidBoxExistence(aidbox)) {
            throw new RouteException("AidBox already exists");
        }
        try {
            this.aidBoxManagement.removeAidBox(aidbox);
        } catch (AidBoxException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return aidbox;
    }

    /**
     * Verifica se a rota contém a AidBox especificada.
     *
     * @param aidbox a AidBox a ser verificada na rota
     * @return true se a rota contiver a `AidBox` especificada, caso contrário, false
     */
    @Override
    public boolean containsAidBox(AidBox aidbox) {
        boolean aux = false;
        if (aidbox == null) {
            aux = false;
        }
        for (int i = 0; i < this.aidBoxManagement.getAidBoxes().length; i++) {
            if (this.aidBoxManagement.getAidBoxes()[i].equals(aidbox)) {
                aux = true;
            }
        }
        return aux;
    }

    /**
     * Substitui uma AidBox existente por uma nova `AidBox` na rota.
     *
     * @param aidbox a AidBox a ser substituída na rota
     * @param aidbox1 a nova AidBox a ser inserida na rota
     * @throws RouteException exceção a ser lançada se qualquer `AidBox` for nula, se a nova AidBox
     *                        já estiver na rota, se a `AidBox` a ser substituída não estiver na rota,
     *                        ou se a nova `AidBox` não for compatível com o veículo da rota
     */
    @Override
    public void replaceAidBox(AidBox aidbox, AidBox aidbox1) throws RouteException {
        if (aidbox == null || aidbox1 == null) {
            throw new RouteException("The AidBox is null");
        } else if (this.aidBoxManagement.verifyAidBoxExistence(aidbox1)) {
            throw new RouteException("The Aid Box to insert is already in the route");
        } else if (!this.aidBoxManagement.verifyAidBoxExistence(aidbox)) {
            throw new RouteException("The Aid Box to replace is already in the route");
        } else if (!this.aidBoxManagement.verifyCompatibility(aidbox1, this.vehicle)) {
            throw new RouteException("The Aid Box to insert is not compatible with the Vehicle of the route.");
        }
        int index = this.aidBoxManagement.indexFounder(aidbox);
        this.aidBoxManagement.getAidBoxes()[index] = aidbox1;
    }

    /**
     * Insere uma nova `AidBox` na rota após uma `AidBox` existente.
     *
     * @param aidbox a `AidBox` existente após a qual a nova `AidBox` será inserida
     * @param aidbox1 a nova `AidBox` a ser inserida na rota
     * @throws RouteException exceção a ser lançada se qualquer `AidBox` for nula, se a nova `AidBox`
     *                        já estiver na rota, se a `AidBox` existente não estiver na rota,
     *                        ou se a nova `AidBox` não for compatível com o veículo da rota
     */
    @Override
    public void insertAfter(AidBox aidbox, AidBox aidbox1) throws RouteException {
        if (aidbox == null || aidbox1 == null) {
            throw new RouteException("The AidBox is null");
        } else if (this.aidBoxManagement.verifyAidBoxExistence(aidbox1)) {
            throw new RouteException("The Aid Box to insert is already in the route");
        } else if (!this.aidBoxManagement.verifyAidBoxExistence(aidbox)) {
            throw new RouteException("The Aid Box to replace is already in the route");
        } else if (!this.aidBoxManagement.verifyCompatibility(aidbox1, this.vehicle)) {
            throw new RouteException("The Aid Box to insert is not compatible with the Vehicle of the route.");
        }
        int index = this.aidBoxManagement.indexFounder(aidbox);
        this.aidBoxManagement.getAidBoxes()[index - 1] = aidbox1;
    }

    /**
     * Retorna uma deep copy do array de rotas.
     *
     * @return uma deep copy do array de `AidBox` representando a rota atual
     */
    @Override
    public AidBox[] getRoute() {
        return this.deepCopyAidBoxes(this.aidBoxManagement.getAidBoxes());
    }

    /**
     * Retorna o veículo da rota
     *
     * @return o veículo da rota
     */
    @Override
    public Vehicle getVehicle() {
        return this.vehicle;
    }

    /**
     * Retorna a distância total da rota.
     *
     * @return a distância total da rota
     */
    @Override
    public double getTotalDistance() {
        double totalDistance = 0.0;
        for (int i = 1; i < this.aidBoxManagement.getAidBoxes().length; i++) {
            try {
                if (this.aidBoxManagement.getAidBoxes()[i - 1] != null && this.aidBoxManagement.getAidBoxes()[i] != null) {
                    totalDistance += this.aidBoxManagement.getAidBoxes()[i - 1].getDistance(this.aidBoxManagement.getAidBoxes()[i]);
                }
            } catch (AidBoxException ex) {
                System.out.println(ex.getMessage());
                return 0.0;
            }
        }
        return totalDistance;
    }

    /**
     * Retorna a duração total da rota.
     *
     * @return a duração total da rota
     */
    @Override
    public double getTotalDuration() {
        double totalDuration = 0.0;
        for (int i = 1; i < this.aidBoxManagement.getAidBoxes().length; i++) {
            try {
                if (this.aidBoxManagement.getAidBoxes()[i - 1] != null && this.aidBoxManagement.getAidBoxes()[i] != null) {
                    totalDuration += this.aidBoxManagement.getAidBoxes()[i - 1].getDuration(this.aidBoxManagement.getAidBoxes()[i]);
                }
            } catch (AidBoxException ex) {
                System.out.println(ex.getMessage());
                return 0.0;
            }
        }
        return totalDuration;
    }

    /**
     * Getter for route report
     * @return the generated route report
     */
    @Override
    public Report getReport() {
        return this.report;
    }

    /**
     * Método responsável por especificar o report da rota
     */
    public void setReport() { //não faço ideia do que fiz para aqui, verificar depois
        ReportImpl newReport = (ReportImpl) this.report;
        newReport.setTotalDistance(this.getTotalDistance());
        newReport.setTotalDuration(this.getTotalDuration());
        this.report = newReport;
    }

    /**
     * Método que cria uma (deep) copy da coleção de routes
     *
     * @param aidbox a coleção de aidboxes a fazer uma (deep) copy
     * @return a (deep) copy da coleção de aidboxes
     */
    private AidBox[] deepCopyAidBoxes(AidBox[] aidbox) {
        if (aidbox == null) {
            return null;
        }
        AidBox[] newAidBox = new AidBox[aidbox.length];
        try {
            for (int i = 0; i < newAidBox.length; i++) {
                if (aidbox[i] != null) {
                    AidBoxImpl aidbox1 = (AidBoxImpl) aidbox[i];
                    newAidBox[i] = (AidBoxImpl) aidbox1.clone();
                }
            }
        } catch (CloneNotSupportedException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        return newAidBox;
    }

    @Override
    public String toString() {
        return "RouteImpl{" +
                "aidBoxManagement=" + this.aidBoxManagement.toString() +
                ", vehicle=" + this.vehicle.toString() +
                ", report=" + this.report.toString() +
                '}';
    }
}
