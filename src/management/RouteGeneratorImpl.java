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
        int aidboxCounter = this.priorityAidboxesCount(instn);
        ContainerType perishableFood = TypesManagement.findByType("perishable food");
        AidBox[] priorityAidboxes = new AidBox[aidboxCounter];
        int counter = 0;

        for (int i = 0; i < institution.getAidBoxes().length; i++) {
            if (institution.getAidBoxes()[i].getContainer(perishableFood).getType().equals(perishableFood)) {
                priorityAidboxes[counter] = institution.getAidBoxes()[i];
            }
        }

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

    /**
     * Método responsável por retornar a quantidade de aidboxes 'prioritárias' (que possuem containers do tipo 'perishable food')
     * @param institution instituição a procurar pelas aidboxes
     * @return o número de aidboxes prioritárias
     */
    private int priorityAidboxesCount(Institution institution) {
        ContainerType perishableFood = TypesManagement.findByType("perishable food");
        int aidboxCounter = 0;
        for (int i = 0; i < institution.getAidBoxes().length; i++) {
            if (institution.getAidBoxes()[i].getContainer(perishableFood).getType().equals(perishableFood)) {
                aidboxCounter++;
            }
        }
        return aidboxCounter;
    }
    
}
