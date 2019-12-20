package beans;

import model.Point;
import model.PointHistoryElement;
import org.primefaces.event.SlideEndEvent;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

public class AreaCheckerBean implements Serializable {
    private Double x;
    private Double y;
    private Double xCanvas;
    private Double yCanvas;
    private String resizeResult;
    private Double r;

    private DataBean bean;
    private UserNameBean userNameBean;

    @PostConstruct
    public void postConstruct(){
        try {
            if(userNameBean.getName()==null || userNameBean.getName().equals("")){
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            }
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }

    }

    public Double getxCanvas() {
        return xCanvas;
    }

    public void setxCanvas(Double xCanvas) {
        this.xCanvas = xCanvas;
    }

    public Double getyCanvas() {
        return yCanvas;
    }

    public void setyCanvas(Double yCanvas) {
        this.yCanvas = yCanvas;
    }

    public AreaCheckerBean(){
        r=3.0;
        x=0.0;
    }

    public Double getX() {
        return x;
    }


    public void setX(Double x) {
        this.x = x;
        //System.out.println("x setted, value " + x);
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
        //System.out.println("y setted, value " + y);
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
        //System.out.println("r setted, value " + r);
    }

    public String getResizeResult() {
        return resizeResult;
    }

    public void setResizeResult(String resizeResult) {
        this.resizeResult = resizeResult;
    }

    public void check() {
        Point p = new Point(getR(), userNameBean.getName());
        PointHistoryElement element = new PointHistoryElement(p, getX(), getY());
        p.addHistoryElement(element);
        bean.addPoint(p);
        bean.addElement(element);
        resizeResult = p.getIsCheck();
    }

    public void canvasCheck(){
        Point p = new Point(getR(), userNameBean.getName());
        PointHistoryElement element = new PointHistoryElement(p, xCanvas, yCanvas);
        p.addHistoryElement(element);
        bean.addPoint(p);
        bean.addElement(element);
        resizeResult = p.getIsCheck();
    }

    public void resizeCheck(){
        Point p = new Point(getR(), userNameBean.getName());
        p.addHistoryElement(new PointHistoryElement(p, xCanvas, yCanvas));
        resizeResult = p.getIsCheck();
    }

    public void setBean(DataBean bean){
        this.bean=bean;
    }

    public boolean getReady(){
        return x!=null && y!=null && r!=null;
    }

    public void handleSlider(SlideEndEvent event){
        setY((double)event.getValue());
    }

    public void setUserNameBean(UserNameBean userNameBean) {
        this.userNameBean = userNameBean;
    }
    public UserNameBean getUserNameBean() {
        return userNameBean;
    }

}
