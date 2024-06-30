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
import com.estg.core.Institution;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.RouteGenerator;
import core.AidBoxImpl;

public class RouteGeneratorImpl implements RouteGenerator {

    private final int ARRAY_SIZE = 0;
    RouteImpl[] route = new RouteImpl[this.route.length];
    private AidBoxImpl[] routes;
    private static int nRoutes = 0;
    private int pickedContainer;
    private int nonPickedContainer;

    @Override
    public Route[] generateRoutes(Institution instn) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void expandRouteArray() {
        AidBox[] newArray = new AidBox[ARRAY_SIZE + 5];
        for (int i = 0; i < nRoutes; i++) {
            newArray[i] = (AidBox) this.route[i];
        }
        this.route = (RouteImpl[]) newArray;
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
