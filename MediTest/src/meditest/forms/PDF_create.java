/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest.forms;
import com.itextpdf.text.List;
import com.itextpdf.text.Anchor;
import static com.itextpdf.text.Annotation.FILE;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.qoppa.pdf.PDFException;
import com.qoppa.pdfImages.PDFImages;
import com.sun.scenario.effect.ImageData;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javafx.scene.paint.Color;
import meditest.FXMLmainController;
import meditest.MediTest;
import meditest.db;
import meditest.kalathi_items;

/**
 *
 * @author nikolaos damianos
 */
public class PDF_create {
    int ID;
    
    String filename;
    static String our_afm,afm_pelati,onoma_pelati,onoma_farmakeiou,dieuthinsi,hmerominia_ekdosis,img;
    static Double  ekptosi,fpa_pou_apalasetai;
    private static String FILE = System.getProperty("user.dir") +"\\src\\meditest\\timologia\\pending\\";//src\meditest\timologia\pending
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    
    
    Document document;
        public PDF_create(double ekptosi,String farmakopoios)throws FileNotFoundException, DocumentException {//int ID, String filename, String our_afm, String afm_pelati, String onoma_pelati, String onoma_fsrmskeiou, String diuthinsi, String hmerominia_ekdosis, Double ekptosi, Double fpa_pou_apalasetai) throws FileNotFoundException, DocumentException {
        /*this.ID = ID;
        this.filename = filename;
        this.our_afm = our_afm;
        this.afm_pelati = afm_pelati;
        this.onoma_pelati = onoma_pelati;
        this.onoma_fsrmskeiou = onoma_fsrmskeiou;
        this.diuthinsi = diuthinsi;
        this.hmerominia_ekdosis = hmerominia_ekdosis;
        
        this.fpa_pou_apalasetai = fpa_pou_apalasetai;
        */
        this.ekptosi = ekptosi;
        int id_farmakeioy = 1;
        db DB = new db();
        String querry  = "SELECT * from farmakopoioi where id = "+farmakopoios.split("|")[0];
        ResultSet rs = DB.SelectQuery(querry);
        try {
            onoma_pelati = rs.getString("onoma") + "_" + rs.getString("epitheto");
            id_farmakeioy = rs.getInt("id_farmakeiou");
        } catch (SQLException ex) {
            Logger.getLogger(PDF_create.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        String querry2 = "SELECT * from farmakeia where id = "+ id_farmakeioy;
        rs = DB.SelectQuery(querry2);
        
        try {
            onoma_farmakeiou = rs.getString("onoma");
            afm_pelati = rs.getString("afm");
            dieuthinsi = rs.getString("odos") + "/"+rs.getString("perioxi")+"/"+rs.getString("poli")+"/"+rs.getString("dimos");
            
        } catch (SQLException ex) {
            Logger.getLogger(PDF_create.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DB.close();
        
        
        String timeStamp = new SimpleDateFormat("dd_MM_yyyy_HH_mm").format(Calendar.getInstance().getTime());
        
        
        
        
        filename =onoma_farmakeiou+"_"+onoma_pelati+"_"+timeStamp+".pdf"; //"tttt.pdf";//alagi se farmakeio_farmakopoios_hmerominia

        
        FILE=FILE+filename;
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            //addContent(document);
            document.close();
            
            
            try {
            System.out.println(FILE);  
            //String []file_pdf = Kalathi_FXMLController.Print_Url.split(".");
            PDFImages pdfDoc = new PDFImages(FILE, null);
            for (int count = 0; count < pdfDoc.getPageCount(); ++count) {
                // convert current page to PNG at a resolution of 250 dpi
                
                pdfDoc.savePageAsPNG(count, System.getProperty("user.dir") +"\\src\\meditest\\timologia\\pending\\"+filename.split("\\.")[0] + count + ".png", 250);
                img = System.getProperty("user.dir") +"\\src\\meditest\\timologia\\pending\\"+filename.split("\\.")[0] + count + ".png";
                
            }
        } catch (PDFException ex) {
            Logger.getLogger(Print_Preview_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Print_Preview_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    public Document getDocument() {
        return document;
    }
        
    
    public static String getFILE() {
        return FILE;
    }
    
    private static void addMetaData(Document document) {
        document.addTitle("Τιμολόγιο Αγοράς");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Νικόλαος Δαμιανος");
        document.addCreator("Νικόλαος Δαμιανος");
    }
    
    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        
        
        /*
        PdfPTable Imgtable = new PdfPTable(1);
        Imgtable.setWidthPercentage(60);
        
        PdfPCell cell1 = new PdfPCell();
        
        String imageFile = System.getProperty("user.dir")+"\\src\\images\\meditest_icon.png";
        

        // Creating an Image object 
        Image img = new Image("file:"+imageFile);
        
        Imgtable.setHorizontalAlignment(Element.ALIGN_LEFT);
        Imgtable.addCell(img);
        Imgtable.addCell(cell1);
        preface.add(Imgtable);
        
        
      */  
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy_HH:mm").format(Calendar.getInstance().getTime());
        Paragraph Hmer = new Paragraph(timeStamp, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL));
        Hmer.setAlignment(Paragraph.ALIGN_RIGHT);
        preface.add(Hmer);
        
        
        Paragraph Title = new Paragraph("Timologio Agoras", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
        Title.setAlignment(Paragraph.ALIGN_CENTER);
        preface.add(Title);
        
        
        addLine(preface, 1);
        
        
        addEmptyLine(preface, 1);
        
        //Paragraph Stoixeia =new Paragraph("Afm : "+"123456789" , smallBold);
        
        //Paragraph Pel_Stoxeia =new Paragraph("Afm : "+"987654321" , smallBold);//+ "afm:"+"987654123"
        
        
        
        //Pel_Stoxeia.setAlignment(Paragraph.ALIGN_RIGHT);
        //Stoixeia.add(Pel_Stoxeia);
        //preface.add(Stoixeia);
        
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);
        
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        table.addCell(getCell("Apothiki", PdfPCell.ALIGN_LEFT));
        
        table.addCell(getCell("Farmakeio", PdfPCell.ALIGN_RIGHT));
        
        table.addCell(getCell("Onoma Apothikis  : "+"MedExpress" , PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("Onoma Farmakeiou : "+onoma_farmakeiou , PdfPCell.ALIGN_RIGHT));
        
        table.addCell(getCell("Onoma Ypaliloy  : "+MediTest.getName()+" " +MediTest.getSurname() , PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("Onoma Farmakopoioy : "+onoma_pelati , PdfPCell.ALIGN_RIGHT));//alagi
        
        table.addCell(getCell("Afm : "+"123456789" , PdfPCell.ALIGN_LEFT));//alagi
        table.addCell(getCell("Afm : "+ afm_pelati , PdfPCell.ALIGN_RIGHT));
        
        table.addCell(getCell("Dieuthinsi : "+"123456789" , PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("Dieuthinsi : "+ dieuthinsi , PdfPCell.ALIGN_RIGHT));
        
        
        preface.add(table);
        addLine(preface, 1);
        
        
        //preface.add(Pel_Stoxeia);
        addEmptyLine(preface, 3);
        
        Paragraph Title_2 = new Paragraph("Proionta:", new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD));
        Title_2.setAlignment(Paragraph.ALIGN_CENTER);
        createTable(Title_2);
        
        preface.add(Title_2);
        
        double poso = 0;
        for(kalathi_items ii :FXMLmainController.List_Kalathi){
            poso+=ii.getSyn_timi();
        }
        
        Paragraph Syn_xwris_fpa = new Paragraph("Sunoliki Timi: " + poso, new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD));
        Syn_xwris_fpa.setAlignment(Paragraph.ALIGN_RIGHT);
        preface.add(Syn_xwris_fpa);
        
        Paragraph meFpa = new Paragraph("Me FPA: " + poso*1.24, new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD));
        meFpa.setAlignment(Paragraph.ALIGN_RIGHT);
        preface.add(meFpa);
        
        double ekptosi_mefpa= poso*1.24*(1-(PDF_create.ekptosi*0.01));
        double ekptosi_mefpa_kaimetaforika = ekptosi_mefpa + Kalathi_FXMLController.getMetaforika();
        
        
        addEmptyLine(preface, 2);
        Paragraph Me_Ekptosi = new Paragraph("Me Ekptwsi : "+String.valueOf(ekptosi_mefpa), new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD));
        Me_Ekptosi.setAlignment(Paragraph.ALIGN_RIGHT);
        preface.add(Me_Ekptosi);
        
        
        Paragraph Me_Metaforika = new Paragraph("Me Metaforika : "+String.valueOf(ekptosi_mefpa_kaimetaforika), new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD));
        Me_Metaforika.setAlignment(Paragraph.ALIGN_RIGHT);
        preface.add(Me_Metaforika);
        
        
        
        addEmptyLine(preface, 6);
        
        PdfPTable table_ypografi = new PdfPTable(2);
        table_ypografi.setWidthPercentage(80);
        
        table_ypografi.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        table_ypografi.addCell(getCell("Ypografi Apothikis :", PdfPCell.ALIGN_LEFT));
        
        table_ypografi.addCell(getCell("Ypografi Farmakeiou :", PdfPCell.ALIGN_RIGHT));
        
        table_ypografi.addCell(getCell(" " , PdfPCell.ALIGN_LEFT));
        table_ypografi.addCell(getCell(" " , PdfPCell.ALIGN_RIGHT));
        
        table_ypografi.addCell(getCell("______________" , PdfPCell.ALIGN_LEFT));
        table_ypografi.addCell(getCell("______________" , PdfPCell.ALIGN_RIGHT));

        
        preface.add(table_ypografi);
        
        document.add(preface);
        // Start a new page
        document.newPage();
    }

    public String getImg() {
        return img;
    }
    
    
    
    
    
    private static void createTable(Paragraph subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(6);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("ID"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("farmaka"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Ousia"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Temaxia"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Timi/temaxio"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Synoliki timi"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);
        
        
        for(kalathi_items ii :FXMLmainController.List_Kalathi){
            table.addCell(String.valueOf(ii.getMed_id()));
            table.addCell(ii.getOnoma());
            table.addCell(ii.getOusia());
            table.addCell(ii.getTemaxia());
            table.addCell(ii.getTimi());
            table.addCell(String.valueOf(ii.getSyn_timi()));
        }
        
        subCatPart.add(table);

    }

    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
    private static void addLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph("______________________________________________________________________________"));
        }
    }
    
     static PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }
     
     
}
