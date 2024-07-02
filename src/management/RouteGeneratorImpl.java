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
import core.TypesManagement;

import java.time.LocalDateTime;

public class RouteGeneratorImpl implements RouteGenerator {

    private final int ARRAY_SIZE = 0;

    private int nPickedContainers = 0;
    private int nNonPickedContainers = 0;
    private Route[] routes = new Route[ARRAY_SIZE];
    private static int nRoutes = 0;

    @Override
    public Route[] generateRoutes(Institution instn) {
        try {
            this.getPerishableFoodContainers(instn);
            this.getNonPerishableFoodContainers(instn); // Ver se esta certo
            this.getMedicineContainers(instn); // Ver se esta certo
            this.getClothingContainers(instn); // Ver se esta certo
        } catch (AidBoxException ex) {
            System.out.println(ex.getMessage());
        }

        collectHighCapacityContainer(instn); // Ver se esta certo, ta a dar erro ao chamar
        //todo recolher o resto dos containers com capacidade > 80%
        //todo identificar veículo a utilizar em cada rota
        //todo identificar containers a transportar
        //todo indentificar containers que não estão a ser utilizados em aidboxes
        return routes;
    }

    /**
     * Método responsável por coletar containers com capacidade superior a 80%
     * @param instn  a instituição a serem recolhidos os containers.
     */
    
    private void collectHighCapacityContainer(Institution instn) throws AidBoxException { // Não sei se está certo
        Vehicle[] vehicles = instn.getVehicles();
        int numContainers = getTotalContainers(instn);
        AidBox[] aidboxesUsed = new AidBox[instn.getAidBoxes().length];
        int numAidBoxesUsed = 0;
        int nParagens;

        for (Vehicle vehicle : vehicles) {
            VehicleImpl vehicleImpl = (VehicleImpl) vehicle;
            System.out.println("Veículo utilizado: " + vehicle.getCode());
            nParagens = 0;
            int numNonPickedContainers = 0;
            int numPickedContainers = 0;
            numAidBoxesUsed = 0;
            for (AidBox aidbox : instn.getAidBoxes()) {
                for (Container container : aidbox.getContainers()) {
                    double capacityPercentage = calcCapacityPercentage(container);
                    if (capacityPercentage > 80) {
                        if (vehicleImpl.pickContainer(container)) {
                            aidboxesUsed[numAidBoxesUsed] = aidbox;
                            numAidBoxesUsed++;
                            System.out.println(" º paragem: " + (nParagens + 1) + aidbox.getCode());
                            System.out.println("Container apanhado:" + container.getCode());
                            aidbox.removeContainer(container);
                            nParagens++;
                            numPickedContainers++;
                        }
                    }

                }

            }
            numNonPickedContainers = numContainers - numPickedContainers;
            double totalDistance = this.calcTotalDistance(aidboxesUsed, numAidBoxesUsed);
            double totalDuration = this.calcTotalDuration(aidboxesUsed, numAidBoxesUsed);
            System.out.println("");

            ReportImpl report = new ReportImpl(1, this.nPickedContainers, totalDistance, totalDuration, this.nNonPickedContainers, instn.getVehicles().length - 1, LocalDateTime.now());
            RouteImpl route = new RouteImpl(vehicle, report);
            route.setAidBox(aidboxesUsed);
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
        AidBox[] aidboxesUsed = new AidBox[instn.getAidBoxes().length];
        int nAidBoxesUsed = 0;

        for (int i = 0; i < vehicles.length; i++) { //for que percorre os veículos
            VehicleImpl vehicle = (VehicleImpl) vehicles[i];
            System.out.println("Veículo utilizado: " + vehicle.getCode());
            nParagens = 0;
            this.nNonPickedContainers = 0;
            this.nPickedContainers = 0;
            nAidBoxesUsed = 0;
            for (int j = 0; j < instn.getAidBoxes().length; j++) { //for que percorre aidboxes
                for (int k = 0; k < instn.getAidBoxes()[j].getContainers().length; k++) { //for que percorre os containers
                    if (instn.getAidBoxes()[j].getContainers()[k].getType().equals(type)) {
                        if (vehicle.pickContainer(instn.getAidBoxes()[j].getContainers()[k])) {
                            aidboxesUsed[nAidBoxesUsed] = instn.getAidBoxes()[j];
                            nAidBoxesUsed++;
                            System.out.println("" + (nParagens + 1) + "º paragem: " + instn.getAidBoxes()[j].getCode());
                            System.out.println("Container recolhido: " + instn.getAidBoxes()[j].getContainers()[k].getCode());
                            instn.getAidBoxes()[j].getContainers()[k] = null;
                            nParagens++;
                            this.nPickedContainers++;
                        }
                    }
                }
            }
            this.nNonPickedContainers = numContainers - this.nPickedContainers;
            double totalDistance = this.calcTotalDistance(aidboxesUsed, nAidBoxesUsed);
            double totalDuration = this.calcTotalDuration(aidboxesUsed, nAidBoxesUsed);
            System.out.println("\n");
            ReportImpl report = new ReportImpl(1, this.nPickedContainers, totalDistance, totalDuration, this.nNonPickedContainers, instn.getVehicles().length - 1, LocalDateTime.now());
            RouteImpl route = new RouteImpl(vehicle, report);
            route.setAidBox(aidboxesUsed);
            nRoutes++;
            if (nRoutes - 1 == this.routes.length) {
                this.expandRoutesArray();
            }
            this.routes[nRoutes - 1] = route;
        }
    }

    /**
     * Método responsável por recolher os containers da instituição do tipo 'non
     * perishable food'.
     *
     * @param instn a instituição a serem recolhidos os containers.
     */
    private void getNonPerishableFoodContainers(Institution instn) throws AidBoxException {
        Vehicle[] vehicles = instn.getVehicles();
        ContainerType type = TypesManagement.findByType("non perishable food");
        int nParagens;
        int numContainers = this.getTotalContainers(instn);
        AidBox[] aidboxesUsed = new AidBox[instn.getAidBoxes().length];
        int nAidBoxesUsed = 0;

        for (int i = 0; i < vehicles.length; i++) {
            VehicleImpl vehicle = (VehicleImpl) vehicles[i];
            System.out.println("Veículo utilizado: " + vehicle.getCode());
            nParagens = 0;
            this.nNonPickedContainers = 0;
            this.nPickedContainers = 0;
            nAidBoxesUsed = 0;
            for (int j = 0; i < instn.getAidBoxes().length; j++) {
                for (int k = 0; k < instn.getAidBoxes()[j].getContainers().length; k++) {
                    if (instn.getAidBoxes()[j].getContainers()[k].getType().equals(type)) {
                        if (vehicle.pickContainer(instn.getAidBoxes()[j].getContainers()[k])) {
                            aidboxesUsed[nAidBoxesUsed] = instn.getAidBoxes()[j];
                            nAidBoxesUsed++;
                            System.out.println("" + (nParagens + 1) + "º paragem: " + instn.getAidBoxes()[j].getCode());
                            System.out.println("Container recolhido: " + instn.getAidBoxes()[j].getContainers()[k].getCode());
                            instn.getAidBoxes()[j].getContainers()[k] = null;
                            nParagens++;
                            this.nPickedContainers++;
                        }
                    }
                }

            }

            this.nNonPickedContainers = numContainers - this.nPickedContainers;
            double totalDistance = this.calcTotalDistance(aidboxesUsed, nAidBoxesUsed);
            double totalDuration = this.calcTotalDuration(aidboxesUsed, nAidBoxesUsed);
            System.out.println("\n");
            ReportImpl report = new ReportImpl(1, this.nPickedContainers, totalDistance, totalDuration, this.nNonPickedContainers, instn.getVehicles().length - 1, LocalDateTime.now());
            RouteImpl route = new RouteImpl(vehicle, report);
            route.setAidBox(aidboxesUsed);
            nRoutes++;
            if (nRoutes - 1 == this.routes.length) {
                this.expandRoutesArray();
            }
            this.routes[nRoutes - 1] = route;
        }
    }

    /**
     * Método responsável por recolher os containers da instituição do tipo
     * 'clothing'.
     *
     * @param instn a instituição a serem recolhidos os containers.
     */
    private void getClothingContainers(Institution instn) throws AidBoxException {
        Vehicle[] vehicles = instn.getVehicles();
        ContainerType type = TypesManagement.findByType("clothing");
        int nParagens;
        int numContainers = this.getTotalContainers(instn);
        AidBox[] aidboxesUsed = new AidBox[instn.getAidBoxes().length];
        int nAidBoxesUsed = 0;

        for (int i = 0; i < vehicles.length; i++) {
            VehicleImpl vehicle = (VehicleImpl) vehicles[i];
            System.out.println("Veículo utilizado: " + vehicle.getCode());
            nParagens = 0;
            this.nNonPickedContainers = 0;
            this.nPickedContainers = 0;
            nAidBoxesUsed = 0;
            for (int j = 0; i < instn.getAidBoxes().length; j++) {
                for (int k = 0; k < instn.getAidBoxes()[j].getContainers().length; k++) {
                    if (instn.getAidBoxes()[j].getContainers()[k].getType().equals(type)) {
                        if (vehicle.pickContainer(instn.getAidBoxes()[j].getContainers()[k])) {
                            aidboxesUsed[nAidBoxesUsed] = instn.getAidBoxes()[j];
                            nAidBoxesUsed++;
                            System.out.println("" + (nParagens + 1) + "º paragem: " + instn.getAidBoxes()[j].getCode());
                            System.out.println("Container recolhido: " + instn.getAidBoxes()[j].getContainers()[k].getCode());
                            instn.getAidBoxes()[j].getContainers()[k] = null;
                            nParagens++;
                            this.nPickedContainers++;
                        }
                    }
                }

            }

            this.nNonPickedContainers = numContainers - this.nPickedContainers;
            double totalDistance = this.calcTotalDistance(aidboxesUsed, nAidBoxesUsed);
            double totalDuration = this.calcTotalDuration(aidboxesUsed, nAidBoxesUsed);
            System.out.println("\n");
            ReportImpl report = new ReportImpl(1, this.nPickedContainers, totalDistance, totalDuration, this.nNonPickedContainers, instn.getVehicles().length - 1, LocalDateTime.now());
            RouteImpl route = new RouteImpl(vehicle, report);
            route.setAidBox(aidboxesUsed);
            nRoutes++;
            if (nRoutes - 1 == this.routes.length) {
                this.expandRoutesArray();
            }
            this.routes[nRoutes - 1] = route;
        }
    }

    /**
     * Método responsável por recolher os containers da instituição do tipo
     * 'medicine'.
     *
     * @param instn a instituição a serem recolhidos os containers.
     */
    private void getMedicineContainers(Institution instn) throws AidBoxException {
        Vehicle[] vehicles = instn.getVehicles();
        ContainerType type = TypesManagement.findByType("medicine");
        int nParagens;
        int numContainers = this.getTotalContainers(instn);
        AidBox[] aidboxesUsed = new AidBox[instn.getAidBoxes().length];
        int nAidBoxesUsed = 0;

        for (int i = 0; i < vehicles.length; i++) {
            VehicleImpl vehicle = (VehicleImpl) vehicles[i];
            System.out.println("Veículo utilizado: " + vehicle.getCode());
            nParagens = 0;
            this.nNonPickedContainers = 0;
            this.nPickedContainers = 0;
            nAidBoxesUsed = 0;
            for (int j = 0; i < instn.getAidBoxes().length; j++) {
                for (int k = 0; k < instn.getAidBoxes()[j].getContainers().length; k++) {
                    if (instn.getAidBoxes()[j].getContainers()[k].getType().equals(type)) {
                        if (vehicle.pickContainer(instn.getAidBoxes()[j].getContainers()[k])) {
                            aidboxesUsed[nAidBoxesUsed] = instn.getAidBoxes()[j];
                            nAidBoxesUsed++;
                            System.out.println("" + (nParagens + 1) + "º paragem: " + instn.getAidBoxes()[j].getCode());
                            System.out.println("Container recolhido: " + instn.getAidBoxes()[j].getContainers()[k].getCode());
                            instn.getAidBoxes()[j].getContainers()[k] = null;
                            nParagens++;
                            this.nPickedContainers++;
                        }
                    }
                }

            }

            this.nNonPickedContainers = numContainers - this.nPickedContainers;
            double totalDistance = this.calcTotalDistance(aidboxesUsed, nAidBoxesUsed);
            double totalDuration = this.calcTotalDuration(aidboxesUsed, nAidBoxesUsed);
            System.out.println("\n");
            ReportImpl report = new ReportImpl(1, this.nPickedContainers, totalDistance, totalDuration, this.nNonPickedContainers, instn.getVehicles().length - 1, LocalDateTime.now());
            RouteImpl route = new RouteImpl(vehicle, report);
            route.setAidBox(aidboxesUsed);
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
     * Método responsável por calcular a duração total de uma rota
     *
     * @param aidboxes as aidboxes a calcular a duração
     * @return a duração total da rota
     * @throws AidBoxException exceção a ser lançada caso a aidbox a calcular a
     * duração seja null
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
     * distância seja null
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
        double momentCapacity = container.getMeasurements()[container.getMeasurements().length].getValue();

        percentage = (momentCapacity / totalCapacity) * 100;

        return percentage;
    }

}
