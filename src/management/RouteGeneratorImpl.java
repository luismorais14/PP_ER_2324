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

    private static int nPickedContainers = 0;

    @Override
    public Route[] generateRoutes(Institution instn) {
        this.getPerishableFoodContainers(instn);
        //todo criar método para calcular percentagem de capacidade
        //todo recolher o resto dos containers com capacidade > 80%
        //todo identificar veículo a utilizar em cada rota
        //todo identificar containers a transportar
        //todo indentificar containers que não estão a ser utilizados em aidboxes
        return null;
    }

    /**
     * Método responsável por recolher os containers da instituição do tipo 'perishable food'
     * @param instn a instituição a serem recolhidos os containers
     */
    private void getPerishableFoodContainers(Institution instn) {
        Vehicle[] vehicles = instn.getVehicles();
        ContainerType type = TypesManagement.findByType("perishable food");
        int nParagens;

        for (int i = 0; i < vehicles.length; i++) { //for que percorre os veículos
            VehicleImpl vehicle = (VehicleImpl) vehicles[i];
            System.out.println("Veículo utilizado: " + vehicle.getCode());
            nParagens = 0;
            for (int j = 0; j < instn.getAidBoxes().length; j++) { //for que percorre aidboxes
                for (int k = 0; k < instn.getAidBoxes()[j].getContainers().length; k++) { //for que percorre os containers
                    if (instn.getAidBoxes()[j].getContainers()[k].getType().equals(type)) {
                        if (vehicle.pickContainer(instn.getAidBoxes()[j].getContainers()[k])) {
                            System.out.println("" + (nParagens + 1) + "º paragem: " + instn.getAidBoxes()[j].getCode());
                            System.out.println("Container recolhido: " + instn.getAidBoxes()[j].getContainers()[k].getCode());
                            instn.getAidBoxes()[j].getContainers()[k] = null;
                            nParagens++;
                            nPickedContainers++;
                        }
                    }
                }
            }
            System.out.println("\n");
        }
    }

}
