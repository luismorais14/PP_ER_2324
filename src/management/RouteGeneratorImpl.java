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
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.RouteGenerator;
import com.estg.pickingManagement.Vehicle;
import core.AidBoxImpl;
import core.ContainerTypeImpl;
import core.InstitutionImpl;
import core.TypesManagement;

public class RouteGeneratorImpl implements RouteGenerator {
    private final int ARRAY_SIZE = 0;

    RouteImpl[] route = new RouteImpl[ARRAY_SIZE];
    private AidBoxImpl[] routes;
    private static int nRoutes = 0;
    private int pickedContainer;
    private int nonPickedContainer;

    @Override
    public Route[] generateRoutes(Institution instn) {
        this.getPerishableFoodContainers(instn);
        //todo Recolher primeiro contentores do tipo 'perishable food'
        //todo Recolher containers com capacidade > 80% (criar método que calcula a percentagem)
        //todo ter em atenção as capacidades dos veículos;
        //todo identificar veículo a utilizar em cada rota
        //todo identificar containers a transportar
        //todo indentificar containers que não estão a ser utilizados em aidboxes
        return null;
    }

    private void getPerishableFoodContainers(Institution instn) {
        InstitutionImpl institution = (InstitutionImpl) instn;
        Vehicle[] vehicles = institution.getVehicles();
        ContainerType type = TypesManagement.findByType("perishable food");
        int capacityIndex = this.findCapacityIndex(type);

        for (int i = 0; i < vehicles.length; i++) { //for que percorre os veículos
            VehicleImpl vehicle = (VehicleImpl) vehicles[i];
            for (int j = 0; j < institution.getAidBoxes().length; j++) { //for que percorre aidboxes
                for (int k = 0; k < institution.getAidBoxes()[j].getContainers().length; k++) { //for que percorre os containers
                    if (institution.getAidBoxes()[j].getContainers()[k].getType().equals(type)) {
                        vehicle.pickContainer(institution.getAidBoxes()[j].getContainers()[k]);
                    }
                }
            }
        }


    }

    /**
     * Método responsável por procurar o index da capacidade correspondente ao tipo de container
     *
     * @param ct    o tipo a ser procurado
     * @return o index
     */
    private int findCapacityIndex(ContainerType ct) {
        ContainerTypeImpl type = (ContainerTypeImpl) ct;
        for (int i = 0; i < TypesManagement.getTypes().length; i++) {
            if (TypesManagement.getTypes()[i].compareTo(type.getType()) == 0) {
                return i;
            }
        }
        return -1;
    }

    public boolean routeContainsContainer(Route route, Container container) {
        for (AidBox aidBox : route.getRoute()) {
            if (aidBox != null) {
                for (Container routeContainer : aidBox.getContainers()) {
                    if (routeContainer != null && routeContainer.equals(container)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void calculatePickedContainers(Institution instn, Route[] routes) {
        int pickedCount = 0;

        for (AidBox aidBox : instn.getAidBoxes()) {
            if (aidBox != null) {
                for (Container container : aidBox.getContainers()) {
                    if (container != null) {
                        for (Route route : routes) {
                            if (route != null && routeContainsContainer(route, container)) {
                                pickedCount++;
                                break;
                            }
                        }
                    }
                }
            }
        }

        this.pickedContainer = pickedCount;
    }

    public void calculateNonPickedContainers(Institution instn, Route[] routes) {
        int nonPickedCount = 0;

        for (AidBox aidBox : instn.getAidBoxes()) {
            if (aidBox != null) {
                for (Container container : aidBox.getContainers()) {
                    if (container != null) {
                        boolean picked = false;
                        for (Route route : routes) {
                            if (route != null && routeContainsContainer(route, container)) {
                                picked = true;
                                break;
                            }
                        }
                        if (!picked) {
                            nonPickedCount++;
                        }
                    }
                }
            }
        }

        this.nonPickedContainer = nonPickedCount;
    }
}
