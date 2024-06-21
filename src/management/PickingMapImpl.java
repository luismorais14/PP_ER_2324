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

import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Route;
import java.time.LocalDateTime;


public class PickingMapImpl implements PickingMap {

    private final int ARRAY_SIZE = 10;

    private LocalDateTime date;
    private Route[] route;

    /**
     * Construtor de PickingMap
     *
     */
    public PickingMapImpl() {
        this.date = null;
        this.route = new Route[ARRAY_SIZE];
    }

    /**
     * Construtor de PickingMap
     *
     * @param date data do PickingMap
     * @param route rotas do PickingMap
     */
    public PickingMapImpl(LocalDateTime date, Route[] route) {
        this.date = date;
        this.route = route;
    }

    /**
     * Metodo responsável por especificar a data do PickingMap
     *
     * @param date data do PickingMap
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Metodo responsável por especificar uma rota
     *
     * @param route um array de objetos Route representando a nova rota
     */
    public void setRoute(Route[] route) {
        this.route = route;
    }

    /**
     * Metodo responsável por retornar a data do PickingMap
     *
     * @return a data de um PickingMap
     */
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Metodo responsável por retornar as rotas
     *
     * @return um array de rotas
     */
    @Override
    public Route[] getRoutes() {
        return this.route;
    }

    /**
     * Retorna uma representação em String da rota.
     *
     * @param route um array de rotas
     * @return uma String que representa a rota ou null se a rota for null
     */
    private String showRoute(Route[] route) {
        if (route == null) {
            return "null";
        }
        String result = "";
        for (int i = 0; i < route.length; i++) {
            if (route[i] != null) {
                result += route[i].toString() + "\n";
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Date: " + this.getDate()
                + "\nMeasurement Value: " + showRoute(this.route);
    }

}
