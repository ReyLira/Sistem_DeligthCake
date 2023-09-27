package controller;

import dao.ProductoImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Producto;
import lombok.Data;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

@Data
@Named(value = "GraficosC")
@SessionScoped
public class GraficosC implements Serializable {

    private ProductoImpl dao;
    private List<Producto> lstGraficos;
    private BarChartModel barModel2;

 
    @PostConstruct
    public void construir (){
        try {
            createBarModel2();
        } catch (Exception ex) {
            Logger.getLogger(GraficosC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createBarModel2() throws Exception {
        barModel2 = new BarChartModel();
        ChartData data = new ChartData();
        
        lstGraficos = new ArrayList<>();
        dao = new ProductoImpl();
        
        lstGraficos = dao.Graficos();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Cantidad");
        barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        barDataSet.setBorderColor("rgb(255, 99, 132)");
        barDataSet.setBorderWidth(1);
        List<Number> cantidad = new ArrayList<>();
        List<String> producto = new ArrayList<>();

        for (Producto prod : lstGraficos) {
            cantidad.add(prod.getSTOPRO());
            producto.add(prod.getNOMPRO());
        }
        
        
        barDataSet.setData(cantidad);
        data.addChartDataSet(barDataSet);
        data.setLabels(producto);
        barModel2.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title() {
        };
        title.setDisplay(true);
        title.setText("Cantidad de Productos");
        options.setTitle(title);

        barModel2.setOptions(options);
    }
    
    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                "Item Index: " + event.getItemIndex() + ", DataSet Index:" + event.getDataSetIndex());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}

