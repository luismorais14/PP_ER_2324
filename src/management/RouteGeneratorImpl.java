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
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Institution;
import com.estg.core.exceptions.AidBoxException;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.RouteGenerator;
import com.estg.pickingManagement.Vehicle;
import core.AidBoxImpl;
import core.ContainerImpl;
import core.ContainerTypeImpl;
import core.TypesManagement;

import java.time.LocalDateTime;

public class RouteGeneratorImpl implements RouteGenerator {

    private final int ARRAY_SIZE = 0;

    private int nPickedContainers = 0;
    private int nNonPickedContainers = 0;
    private Route[] routes = new Route[ARRAY_SIZE];
    private static int nRoutes = 0;
    private Container[] pickedContainers;
    int pickedContainersIndex = 0;


    @Override
    public Route[] generateRoutes(Institution instn) {
        try {
            this.getPerishableFoodContainers(instn);
            System.out.println("---------------------------------------------------");
            this.collectHighCapacityContainer(instn);
        } catch (AidBoxException ex) {
            System.out.println(ex.getMessage());
        }
        return routes;
    }

    /**
     * Método responsável por coletar containers com capacidade superior a 80%
     *
     * @param instn a instituição a serem recolhidos os containers.
     */
    private void collectHighCapacityContainer(Institution instn) throws AidBoxException {
        Vehicle[] vehicles = instn.getVehicles();
        int numContainers = getTotalContainers(instn);
        int numHighCapacityContainers = getNumberOfHighCapacityContainers(instn) - pickedContainersIndex;

        for (int i = 0; i < vehicles.length; i++) {
            VehicleImpl vehicle = (VehicleImpl) vehicles[i];
            System.out.println("Veículo utilizado: " + vehicle.getCode());
            int nParagens = 0;
            nNonPickedContainers = 0;
            nPickedContainers = 0;

            AidBox[] currentlyUsedAidboxes = new AidBox[instn.getAidBoxes().length];
            int nCurrentlyUsedAidboxes = 0;
            boolean foundHighCapacityContainer = false;

            for (int j = 0; j < instn.getAidBoxes().length; j++) {
                AidBoxImpl aidBox = (AidBoxImpl) instn.getAidBoxes()[j];
                Container[] containers = aidBox.getContainers();

                if (containsNotPickedContainers(aidBox, pickedContainersIndex)) {
                    if (checkAidBoxContaiersCapacity(aidBox)) {
                        for (int k = 0; k < containers.length; k++) {
                            if (calcCapacityPercentage(containers[k]) > 80) {
                                if (!verifyPickedContainer(containers[k], pickedContainersIndex)) {
                                    if (vehicle.pickContainer(containers[k])) {
                                        System.out.println("" + (nParagens + 1) + "º paragem: " + aidBox.getCode());
                                        System.out.println("Container recolhido: " + containers[k].getCode());
                                        nPickedContainers++;
                                        foundHighCapacityContainer = true;
                                        currentlyUsedAidboxes[nCurrentlyUsedAidboxes] = aidBox;
                                        nCurrentlyUsedAidboxes++;
                                        nParagens++;
                                        if (nPickedContainers == numHighCapacityContainers) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (nPickedContainers == numHighCapacityContainers) {
                    break;
                }
            }

            if (!foundHighCapacityContainer) {
                System.out.println("Nenhuma aidbox encontrada com containers com lotação superior a 80% para o veículo");
            }

            nNonPickedContainers = numContainers - nPickedContainers;

            double totalDistance = calcTotalDistance(currentlyUsedAidboxes, nCurrentlyUsedAidboxes);
            double totalDuration = calcTotalDuration(currentlyUsedAidboxes, nCurrentlyUsedAidboxes);

            System.out.println("\n");
            ReportImpl report = new ReportImpl(1, nPickedContainers, totalDistance, totalDuration, nNonPickedContainers, instn.getVehicles().length - 1, LocalDateTime.now());
            RouteImpl route = new RouteImpl(vehicle, report);
            route.setAidBox(currentlyUsedAidboxes);
            nRoutes++;
            if (nRoutes - 1 == this.routes.length) {
                this.expandRoutesArray();
            }
            this.routes[nRoutes - 1] = route;

            if (nPickedContainers == numHighCapacityContainers) {
                break;
            }
        }
    }



    /**
     * Método responsável por recolher os containers da instituição do tipo
     * 'perishable food'
     *
     * @param instn a instituição a serem recolhidos os containers.
     */
    private void getPerishableFoodContainers(Institution instn) throws AidBoxException {
        Vehicle[] vehicles = instn.getVehicles();
        ContainerType type = TypesManagement.findByType("perishable food");
        int nParagens;
        int numContainers = this.getTotalContainers(instn);
        int nPerishableFoodContainers = this.getPerishableContainers(instn, type);
        AidBox[] aidboxesUsed = new AidBox[instn.getAidBoxes().length];
        int nAidBoxesUsed = 0;
        int pickingContainers = 0;
        this.pickedContainers = new Container[numContainers];

        for (int i = 0; i < vehicles.length; i++) { // for que percorre os veículos
            if (pickingContainers == nPerishableFoodContainers) {
                break;
            }
            VehicleImpl vehicle = (VehicleImpl) vehicles[i];
            System.out.println("Veículo utilizado: " + vehicle.getCode());
            nParagens = 0;
            this.nNonPickedContainers = 0;
            this.nPickedContainers = 0;
            AidBox[] currentlyUsedAidboxes = new AidBox[aidboxesUsed.length];
            int nCurrentlyUsedAidboxes = 0;
            int totalPickedContainers = 0;

            for (int j = 0; j < instn.getAidBoxes().length; j++) { // for que percorre aidboxes
                AidBoxImpl aidBox = (AidBoxImpl) instn.getAidBoxes()[j];
                if (j > 0 && nAidBoxesUsed > 0) {
                    if (this.verifyAidBoxUsage(aidboxesUsed, aidBox, nAidBoxesUsed)) {
                        continue;
                    }
                }
                Container[] containers = aidBox.getContainers();
                if (this.verifyPerishableFoodContainer(aidBox)) {
                    aidboxesUsed[nAidBoxesUsed] = aidBox;
                    currentlyUsedAidboxes[nCurrentlyUsedAidboxes] = aidBox;
                    System.out.println("" + (nParagens + 1) + "º paragem: " + aidBox.getCode());
                    for (int k = 0; k < containers.length && totalPickedContainers < vehicle.getCapacity(type); k++) { // for que percorre os containers
                        if (containers[k].getType().equals(type)) {
                            if (vehicle.pickContainer(containers[k])) {
                                System.out.println("Container recolhido: " + containers[k].getCode());
                                this.pickedContainers[this.pickedContainersIndex] = containers[k];
                                this.nPickedContainers++;
                                this.pickedContainersIndex++;
                            }
                            pickingContainers++;
                        } else if (this.calcCapacityPercentage(containers[k]) > 80) {
                            if (vehicle.pickContainer(containers[k])) {
                                System.out.println("Container recolhido: " + containers[k].getCode());
                                this.pickedContainers[this.pickedContainersIndex] = containers[k];
                                this.nPickedContainers++;
                                this.pickedContainersIndex++;
                            }
                        }
                    }
                    totalPickedContainers++;
                    nCurrentlyUsedAidboxes++;
                    nParagens++;
                    nAidBoxesUsed++;
                }
                if (totalPickedContainers == vehicle.getCapacity(type)) {
                    break;
                }
            }
            this.nNonPickedContainers = numContainers - this.nPickedContainers;
            double totalDistance = this.calcTotalDistance(currentlyUsedAidboxes, nCurrentlyUsedAidboxes);
            double totalDuration = this.calcTotalDuration(currentlyUsedAidboxes, nCurrentlyUsedAidboxes);
            System.out.println("\n");
            ReportImpl report = new ReportImpl(1, this.nPickedContainers, totalDistance, totalDuration, this.nNonPickedContainers, instn.getVehicles().length - 1, LocalDateTime.now());
            RouteImpl route = new RouteImpl(vehicle, report);
            route.setAidBox(currentlyUsedAidboxes);
            nRoutes++;
            if (nRoutes - 1 == this.routes.length) {
                this.expandRoutesArray();
            }
            this.routes[nRoutes - 1] = route;
        }
    }

    /**
     * Método responsável por calcular a quantidade total de containers
     * presentes na instituição
     *
     * @param instn instituição a calcular a quantidade de containers
     * @return a quantidade total de containers
     */
    private int getTotalContainers(Institution instn) {
        int nContainers = 0;
        for (int i = 0; i < instn.getAidBoxes().length; i++) {
            nContainers += instn.getAidBoxes()[i].getContainers().length;
        }
        return nContainers;
    }

    /**
     * Método responsável por retornar a quantidade de containers com lotação superior a 80%
     *
     * @param instn instituição a verificar os containers
     * @return o número de containers
     */
    private int getNumberOfHighCapacityContainers(Institution instn) {
        int count = 0;
        for (AidBox aidBox : instn.getAidBoxes()) {
            for (Container container : aidBox.getContainers()) {
                if (container != null && this.calcCapacityPercentage(container) > 80) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Método responsável por verificar se a aidbox possui containers com lotação superior a 80%
     *
     * @param aidBox aidbox a verificar
     * @return true se possuir, false caso contrário
     */
    private boolean checkAidBoxContaiersCapacity(AidBox aidBox) {
        for (int i = 0; i < aidBox.getContainers().length; i++) {
            if (this.calcCapacityPercentage(aidBox.getContainers()[i]) > 80) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método responsável por verificar se uma aidbox possui containers por recolher
     *
     * @param aidbox aidbox a verificar
     * @return true caso tenha containers a verificar, false caso contrário
     */
    private boolean containsNotPickedContainers(AidBox aidbox, int size) {
        int counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < aidbox.getContainers().length; j++) {
                if (this.pickedContainers[i].equals(aidbox.getContainers()[j])) {
                    counter++;
                }
            }
        }
        if (counter == aidbox.getContainers().length) {
            return false;
        }
        return true;
    }

    /**
     * Método responsável por verificar se um container já foi recolhido ou não
     *
     * @param cntnr o container a verificar
     * @return true caso já tenha sido recolhido, false caso contrário
     */
    private boolean verifyPickedContainer(Container cntnr, int size) {
        for (int i = 0; i < size; i++) {
            if (this.pickedContainers[i].equals(cntnr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método responsável por retornar a quantidade total de containres do tipo perishable food
     *
     * @param instn instituição a procurar
     * @param type  o tipo de container
     * @return o número de containers
     */
    private int getPerishableContainers(Institution instn, ContainerType type) {
        int count = 0;
        for (AidBox aidBox : instn.getAidBoxes()) {
            for (Container container : aidBox.getContainers()) {
                if (container != null && container.getType().equals(type)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Método responsável por calcular a duração total de uma rota
     *
     * @param aidboxes as aidboxes a calcular a duração
     * @return a duração total da rota
     * @throws AidBoxException exceção a ser lançada caso a aidbox a calcular a
     *                         duração seja null
     */
    private double calcTotalDuration(AidBox[] aidboxes, int size) throws AidBoxException {
        double totalDuration = 0.0;
        for (int i = 1; i < size; i++) {
            totalDuration += aidboxes[i - 1].getDuration(aidboxes[i]);
        }
        return totalDuration;
    }

    /**
     * Método responsável por calcular a distância total de uma rota
     *
     * @param aidboxes as aidboxes a calcular a distância
     * @return a distância total da rota
     * @throws AidBoxException exceção a ser lançada caso a aidbox a calcular a
     *                         distância seja null
     */
    private double calcTotalDistance(AidBox[] aidboxes, int size) throws AidBoxException {
        double totalDistance = 0.0;
        for (int i = 1; i < size; i++) {
            totalDistance += aidboxes[i - 1].getDistance(aidboxes[i]);
        }
        return totalDistance;
    }

    /**
     * Método responsável por expandir o array de rotas
     */
    private void expandRoutesArray() {
        Route[] newRoutes = new Route[this.routes.length + 1];
        for (int i = 0; i < this.routes.length; i++) {
            newRoutes[i] = this.routes[i];
        }
        this.routes = newRoutes;
    }

    /**
     * Método responsável por retornar a percentagem de capacidade de um
     * determinado container
     *
     * @param container o container a ser medido
     * @return a percentagem da capacidade
     */
    private double calcCapacityPercentage(Container container) {
        double percentage = 0.0;
        double totalCapacity = container.getCapacity();
        double momentCapacity = container.getMeasurements()[container.getMeasurements().length - 1].getValue();

        percentage = (momentCapacity / totalCapacity) * 100;

        return percentage;
    }

    /**
     * Método responsável por verificar se uma aidbox já foi usada
     *
     * @param aidBoxes a coleção de aidboxes já utilziadas
     * @param aidBox   a aidbox a verificar
     * @return o sucesso ou insucesso da operação
     */
    private boolean verifyAidBoxUsage(AidBox[] aidBoxes, AidBox aidBox, int size) {
        for (int i = 0; i < size; i++) {
            if (aidBoxes[i].equals(aidBox)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método responsável por verificar se uma aidbox possui um container do tipo Perishable Food
     *
     * @param aidbox aidbox a verificar
     * @return true se possuir, false caso contrário
     */
    private boolean verifyPerishableFoodContainer(AidBox aidbox) {
        ContainerType type = TypesManagement.findByType("perishable food");

        for (int i = 0; i < aidbox.getContainers().length; i++) {
            if (aidbox.getContainers()[i].getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

}
