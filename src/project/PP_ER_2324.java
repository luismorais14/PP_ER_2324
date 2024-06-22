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
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Vehicle;
import management.PickingMapImpl;
import management.VehicleImpl;

import java.time.LocalDateTime;

public class PP_ER_2324 {

    public static void main(String[] args) throws AidBoxException {
        AidBoxImpl aidbox1 = new AidBoxImpl("CAIXF51", "Lousada");
        AidBoxImpl aidbox2 = new AidBoxImpl("CAIXF38", "Felgueiras");
        PickingMapImpl pm = new PickingMapImpl();
        PickingMap[] pma = {pm};
        AidBoxImpl[] originalAidBoxes = {aidbox1, aidbox2};
        MeasurementImpl mi = new MeasurementImpl(LocalDateTime.now(), 232.2);
        Measurement[] ma = {mi};
        VehicleImpl vehicle = new VehicleImpl();
        Vehicle[] va = {vehicle};
        InstitutionImpl institution = new InstitutionImpl("Cerci", pma, originalAidBoxes, ma, va);

        System.out.println("Teste método getAidBoxes:");
        for (int i = 0; i < institution.getAidBoxes().length; i++) {
            System.out.println(institution.getAidBoxes()[i]);
        }

        System.out.println("\nTeste deep Copy:");

        AidBox[] copiedAidBoxes = institution.deepCopyAidBoxes(originalAidBoxes);

        originalAidBoxes[0].setZone("Porto");
        originalAidBoxes[1].setZone("Lisboa");

        System.out.println("Original AidBoxes:");
        for (AidBoxImpl box : originalAidBoxes) {
            System.out.println(box.getZone());
        }

        System.out.println("Copied AidBoxes:");
        for (AidBox box : copiedAidBoxes) {
            System.out.println(box.getZone());
        }
    }

}
