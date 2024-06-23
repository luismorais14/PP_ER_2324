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

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Vehicle;
import management.PickingMapImpl;
import management.VehicleImpl;

import java.time.LocalDateTime;

public class PP_ER_2324 {

    public static void main(String[] args) throws AidBoxException, ContainerException {
        AidBoxImpl aidbox1 = new AidBoxImpl("CAIXF51", "Lousada");
        AidBoxImpl aidbox2 = new AidBoxImpl("CAIXF33", "Felgueiras");
        PickingMapImpl pm = new PickingMapImpl();
        PickingMap[] pma = {pm};
        AidBoxImpl[] originalAidBoxes = {aidbox1, aidbox2};
        MeasurementImpl mi = new MeasurementImpl(LocalDateTime.now(), 232.2);
        Measurement[] ma = {mi};
        VehicleImpl vehicle = new VehicleImpl();
        Vehicle[] va = {vehicle};
        InstitutionImpl institution = new InstitutionImpl("Cerci", pma, originalAidBoxes, ma, va);
        ContainerTypeImpl type = new ContainerTypeImpl();
        ContainerType type1 = (ContainerType) type;

        System.out.println("Teste método getAidBoxes:");
        for (int i = 0; i < institution.getAidBoxes().length; i++) {
            System.out.println(institution.getAidBoxes()[i]);
        }

        System.out.println("\nTeste deep Copy:");

        // Create a container and an AidBox
        ContainerImpl originalContainer = new ContainerImpl(type1, 500 , ma, "N1EI");
        aidbox2.addContainer(originalContainer);

        // Retrieve a deep copy of the container from the AidBox
        ContainerImpl copiedContainer = (ContainerImpl) institution.getContainer(aidbox2, type1);

        // Modify the original container
        originalContainer.setCapacity(20);

        // Check if the copied container is unaffected
        System.out.println("Original Container Quantity: " + originalContainer.getCapacity());
        System.out.println("Copied Container Quantity: " + copiedContainer.getCapacity());
    }

}
