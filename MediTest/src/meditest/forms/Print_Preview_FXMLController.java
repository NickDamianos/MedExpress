/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest.forms;



import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.regex.Pattern;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;

import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;

import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.print.PrintException;
import meditest.FXMLmainController;
import static meditest.FXMLmainController.List_Kalathi;
import meditest.db;
import static meditest.forms.Kalathi_FXMLController.getDian;
import static meditest.forms.Kalathi_FXMLController.getFarmakopoioiData;
import static meditest.forms.Kalathi_FXMLController.getMetaforika;
import static meditest.forms.Kalathi_FXMLController.remove_all_from_table;
import meditest.kalathi_items;


/**
 * FXML Controller class
 *
 * @author nikolaos damianos
 */
public class Print_Preview_FXMLController implements Initializable {

    @FXML
    private ImageView PdfViewer;
    @FXML
    private ChoiceBox<Printer> printers;
    @FXML
    private Button print_button;
    @FXML
    private WebView Pdf_View_WEB;
    
    private String Pdf_Fname;
    private String Fname;//filename
    @FXML
    private AnchorPane pre_view;
    @FXML
    private Button PDF_Button;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        PdfViewer.setImage(new Image("file:" + Kalathi_FXMLController.Print_Url));

        ObservableSet<Printer> Printers = Printer.getAllPrinters();
        for (Printer p : Printers) {
            printers.getItems().add(p);
        }
        System.out.println(Printer.getDefaultPrinter());
        printers.setValue(printers.getItems().get(0));

        Fname = Kalathi_FXMLController.Print_Url.split(Pattern.quote("\\"))[Kalathi_FXMLController.Print_Url.split(Pattern.quote("\\")).length-1];
        Fname = Fname.split("\\.")[0];
        Fname = Fname.substring(0, Fname.length()-1);
        
        Pdf_Fname = Kalathi_FXMLController.Print_Url.split("\\.")[0].substring(0, Kalathi_FXMLController.Print_Url.split("\\.")[0].length()-1) + ".pdf";
        System.out.println(Fname);
    }    

    @FXML
    private void print(ActionEvent event) throws FileNotFoundException, PrintException {
        if (printers.getValue().getName().contains("PDF")) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(pre_view.getScene().getWindow());

            if (selectedDirectory == null) {
                Path Default_Dir =Paths.get(System.getProperty("user.home") + "/Desktop/"+Fname+".pdf");
                      
                PDF_Copy(Default_Dir);
                
            } else {
                PDF_Copy(Paths.get(selectedDirectory.toString()+"\\"+Fname+".pdf"));               
            }
        } else {
            File f = new File(System.getProperty("user.dir") + "\\src\\meditest\\timologia\\pending\\");

            String[] pathnames;
            pathnames = f.list();
            //String files_Name = Kalathi_FXMLController.Print_Url.split("\\.")[0].substring(0,Kalathi_FXMLController.Print_Url.split("\\.")[0].length() -1);
            //C:\Users\nikolaos damianos\Documents\NetBeansProjects\MediTest\src\meditest\timologia\pending\tttt.pdf
            int ii = 0;
            for (String pathname : pathnames) {

                if (pathname.equals(Fname + ii + ".png")) {

                    System.out.println(pathname);
                    ImageView printableImage = new ImageView("file:" + System.getProperty("user.dir") + "\\src\\meditest\\timologia\\pending\\" + Fname + ii + ".png");
                    printImage(printableImage);
                    ii++;
                }

            }

        }
        
        
        DB_Update();
        remove_all_from_table();
        close();
        
        
    }
    
    void printImage(Node node) {

        Printer printer = printers.getValue();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        System.out.println("PageLayout: " + pageLayout);

        // Printable area
        double pWidth = pageLayout.getPrintableWidth();
        double pHeight = pageLayout.getPrintableHeight();
        System.out.println("Printable area is " + pWidth + " width and "
                + pHeight + " height.");

        // Node's (Image) dimensions
        double nWidth = node.getBoundsInParent().getWidth();
        double nHeight = node.getBoundsInParent().getHeight();
        System.out.println("Node's dimensions are " + nWidth + " width and "
                + nHeight + " height");

        // How much space is left? Or is the image to big?
        double widthLeft = pWidth - nWidth;
        double heightLeft = pHeight - nHeight;
        System.out.println("Width left: " + widthLeft
                + " height left: " + heightLeft);

        // scale the image to fit the page in width, height or both
        double scale = 0;

        if (widthLeft < heightLeft) {
            scale = pWidth / nWidth;
        } else {
            scale = pHeight / nHeight;
        }

        // preserve ratio (both values are the same)
        node.getTransforms().add(new Scale(scale, scale));

        // after scale you can check the size fit in the printable area
        double newWidth = node.getBoundsInParent().getWidth();
        double newHeight = node.getBoundsInParent().getHeight();
        System.out.println("New Node's dimensions: " + newWidth
                + " width " + newHeight + " height");

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }
    
    void PDF_Copy(Path path) {
        String Pdf_Path = Kalathi_FXMLController.Print_Url.split("\\.")[0].substring(0, Kalathi_FXMLController.Print_Url.split("\\.")[0].length()-1) + ".pdf";
        
        
        
        System.out.println("Pdf_Path : " + Pdf_Path);
        File PDF = new File(Pdf_Path);
        
        try {
            Files.copy(PDF.toPath(), path);
        } catch (IOException ex) {
            Logger.getLogger(Print_Preview_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void Save_pdf(ActionEvent event) {
        
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(pre_view.getScene().getWindow());

        if (selectedDirectory == null) {
            Path Default_Dir = Paths.get(System.getProperty("user.home") + "/Desktop/" + Fname + ".pdf");

            PDF_Copy(Default_Dir);

        } else {
            PDF_Copy(Paths.get(selectedDirectory.toString() + "\\" + Fname + ".pdf"));
        }
        
        DB_Update();
        remove_all_from_table();
        close();
        
        
    }
    
    void DB_Update(){
        //parag_before_receive  
        db DB = new db();
        String items = "";
        for(kalathi_items item : List_Kalathi){
            items+= String.valueOf(item.getMed_id()) + ":" + item.getOnoma()+":"+item.getTemaxia()+"\n";                    
        }
        
        
        String[] farmak_values = getFarmakopoioiData().split(":");//0 = id farmakopoiou , 1 = onoma farmakeiou , 2 = address
        

        
        String timeStamp = new SimpleDateFormat("dd_MM_yyyy:HH_mm").format(Calendar.getInstance().getTime());
        double kostos=0;
        
        
        
        for(kalathi_items ii :FXMLmainController.List_Kalathi){
            kostos+=ii.getSyn_timi();
        }
        kostos = kostos*1.24+getMetaforika();
        String querry = "INSERT INTO parag_before_receive (onoma_timologiou,id_dianomea,address,wra_parag,hmerominia,kostos,onoma_farmakeiou,id_farmak,Proionta)"
                    + " VALUES ('" + Pdf_Fname + "'," + getDian() + ",'" + farmak_values[2] + "','" + timeStamp.split(":")[1]
                    + "','" + timeStamp.split(":")[0] + "'," + kostos + ",'" + farmak_values[1] + "','" + farmak_values[0] + "','" + items
                    + "' );";
        
        System.out.println(querry);
        
        DB.updateDB(querry);
        
        DB.close();
        
        
        
    }
    
    void close(){
        Stage stage = (Stage) pre_view.getScene().getWindow();
        stage.close();
    } 
}
