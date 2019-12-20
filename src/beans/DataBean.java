package beans;

import model.Point;
import model.PointHistoryElement;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class DataBean {
    private List<Point> points;
    private boolean notEmpty;

    private EntityManager em = Persistence.createEntityManagerFactory( "hibernate" ).createEntityManager();

    public DataBean(){
        Query query = em.createQuery("select p from Point p");
        points = query.getResultList();
        points.sort((p1, p2) -> p1.getId() > p2.getId() ? -1 : 1);
        System.out.println(points.toString());
        setNotEmpty(true);
    }

    public List<Point> getPoints() {
        return points;
    }

    public void addPoint(Point currentPoint) {
        points.add(0, currentPoint);
        addPointToDatabase(currentPoint);
        setNotEmpty(true);
    }

    public void setNotEmpty(boolean notEmpty) {
        this.notEmpty = points.size()>0;
    }

    public boolean isNotEmpty(){
        return notEmpty;
    }

    private void addPointToDatabase(Point p){
        em.getTransaction().begin();
        em.persist(p);
        em.flush();
        em.getTransaction().commit();
        System.out.println("commited");
    }

    public void addElement(PointHistoryElement element){
        em.getTransaction().begin();
        em.persist(element);
        em.flush();
        em.getTransaction().commit();
    }

    public void updatePoint(Point p){
        if(!(p.getX()==p.getEX()&&p.getY()==p.getEY())){
            PointHistoryElement element = new PointHistoryElement(p, p.getEX(), p.getEY());
            p.addHistoryElement(element);
            addElement(element);
            p.reverseEdit();
        }
        else p.reverseEdit();
    }
}
