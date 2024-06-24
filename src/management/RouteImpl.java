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
import project.AidBoxManagement;


public class RouteImpl implements Route {

    private AidBoxManagement aidBoxManagement = new AidBoxManagement();
    private Vehicle vehicle;
    private AidBox[] route;


    /**
     * Método responsável por adicionar uma aidbox à rota
     * @param aidbox aidbox a ser adicionada
     * @throws RouteException exceção a ser lançada caso a aidbox seja null, caso a aidbox já esteja na rota ou caso a aidbox não seja compatível
     */
    @Override
    public void addAidBox(AidBox aidbox) throws RouteException {
        if (aidbox == null) {
            throw new RouteException("AidBox is null");
        }
        try {
            if (this.aidBoxManagement.verifyAidBoxExistence(aidbox)) {
                throw new RouteException("AidBox already exists");
            }
        } catch (AidBoxException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        if (!this.aidBoxManagement.verifyCompatibility(aidbox, this.vehicle)) {
            throw new RouteException("AidBox is not compatible");
        }
        this.aidBoxManagement.addAidBox(aidbox);
    }

    @Override
    public AidBox removeAidBox(AidBox aidbox) throws RouteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean containsAidBox(AidBox aidbox) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void replaceAidBox(AidBox aidbox, AidBox aidbox1) throws RouteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insertAfter(AidBox aidbox, AidBox aidbox1) throws RouteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AidBox[] getRoute() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vehicle getVehicle() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getTotalDistance() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getTotalDuration() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Report getReport() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
    
    /**
 * Verifica se duas `AidBox` são iguais.
 *
 * @param aidbox a `AidBox` a ser comparada com a `AidBox` na rota
 * @return true se as `AidBox` forem iguais, caso contrário, false
 */
    private boolean toEqualsAidBox(AidBox aidbox) {
        boolean aux = true;
        if ((aidbox == null) || (getClass() != aidbox.getClass())) {
            aux = false;
        } else {
            for (int i = 0; i < route.length; i++) {
                if (this.route[i].getCode().compareTo(aidbox.getCode()) == 0) {
                    aux = true;
                }
            }

        }
        return aux;

    }
    
}
