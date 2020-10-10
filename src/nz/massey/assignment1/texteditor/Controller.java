package nz.massey.assignment1.texteditor;

/**
 * @Description texteditor
 * @Author Haotian Dong and Dinglong Zhang
 * @Date 2020-09-29 19:15
 */
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private MenuBar menubar;

    @FXML
    private Menu menu;

    @FXML
    private MenuItem newfile;

    @FXML
    public MenuItem openfile;

    @FXML
    private MenuItem savefile;

    @FXML
    private MenuItem Timeanddate;

    @FXML
    private MenuItem printfile;

    @FXML
    private MenuItem Exit;

    @FXML
    private Menu PDF;

    @FXML
    private Menu search;

    @FXML
    private Menu view;

    @FXML
    private Menu manage;

    @FXML
    private Menu help;

    @FXML
    private MenuItem about;

    @FXML
    private TextArea mainarea;

    @FXML
    private ContextMenu Rightmenu;

    @FXML
    private MenuItem Copy;

    @FXML
    private MenuItem Paste;

    @FXML
    private MenuItem Cut;

    @FXML
    void onMenuNew(ActionEvent event) throws IOException {
        Stage newwindow = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Texteditor.fxml"));
        Scene scene = new Scene(root,1000,800);
        newwindow.setTitle("Text Editor");
        newwindow.setScene(scene);
        newwindow.show();
    }


    @FXML
    void onMenuOpen(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"),
                new FileChooser.ExtensionFilter("ODT","*.odt"));
        File file = fileChooser.showOpenDialog(anchorpane.getScene().getWindow());
        if (file.getName().endsWith(".txt")) {
            //Create a file input stream object
            FileInputStream in = new FileInputStream(file);
            byte[] ts = new byte[100000];
            in.read(ts);
            //Write the contents of the file to the TextArea
            mainarea.setText(new String(ts));
            in.close();
        }
        if (file.getName().endsWith(".odt")) {
            OdfTextDocument odf = (OdfTextDocument) OdfDocument.loadDocument(file);
            String text = odf.getContentRoot().getTextContent();
            mainarea.setText(text);
        }
    }

    @FXML
    public void onMenuSave() throws IOException {
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                FileOutputStream out = new FileOutputStream(file);
                String str = mainarea.getText();
                byte[] bs = str.getBytes();// 字符串转换成字节数组
                out.write(bs);
                out.flush();
                out.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

//        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//        fileChooser.getExtensionFilters().add(extFilter);
//        PrintWriter writer = new PrintWriter(file);
//        writer.print(mainarea.getText());
//        writer.close();
    }
    @FXML
    void onMenuTime(ActionEvent event){
        //Set date format
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Display the date in the TextArea
        mainarea.setText(time.format(new Date()));
    }

    @FXML
    void onMenuPrint(ActionEvent event) {
        DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        PrintService[] printService = PrintServiceLookup.lookupPrintServices(docFlavor,aset);
        PrintService defaultservice = PrintServiceLookup.lookupDefaultPrintService();
        PrintService service = ServiceUI.printDialog(null,200,200,printService,defaultservice,docFlavor,aset);
        if (service != null) {
            try {
                javafx.scene.canvas.Canvas canvas = new javafx.scene.canvas.Canvas(250,250);
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.fillText(mainarea.getText(), 10, 10);
                PrinterJob printerJob = PrinterJob.createPrinterJob();
                printerJob.printPage(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText("Print failed");
            alert.showAndWait();
        }

    }

    @FXML
    void onMenuExit(ActionEvent event) throws IOException {
        //If the content is empty, exit directly
        if (mainarea.getText().isEmpty()) {
            System.exit(0);
        }
        //Create a new pop-up window and add YES, NO, CANCEL
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "exit without saving?",
                ButtonType.YES,
                ButtonType.NO,
                ButtonType.CANCEL
        );
        alert.setTitle("confirm");
        alert.setHeaderText(null);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
        } else if (alert.getResult() == ButtonType.NO) {
            onMenuSave();
            Platform.exit();
        }

    }


    @FXML
    void onMenuSearch(ActionEvent event) {
        search.getGraphic();
        menubar.getMenus().add(search);
        final int[] startIndex = {0};
        HBox h1 = new HBox();
        h1.setPadding(new Insets(20, 5, 20, 5));
        h1.setSpacing(5);
        javafx.scene.control.Label lable1 = new javafx.scene.control.Label("Searching content");
        javafx.scene.control.TextField tf1 = new javafx.scene.control.TextField();
        tf1.setId("textfield1");
        h1.getChildren().addAll(lable1,tf1);

        VBox v1 = new VBox();
        v1.setPadding(new Insets(20, 5, 20, 10));
        javafx.scene.control.Button btn1 = new javafx.scene.control.Button("Next");
        btn1.setId("nextbutton");
        v1.getChildren().addAll(btn1);

        HBox findRootNode = new HBox();
        findRootNode.getChildren().addAll(h1, v1);

        Stage findStage = new Stage();
        Scene scene1 = new Scene(findRootNode, 450, 90);
        findStage.setTitle("Search");
        findStage.setScene(scene1);
        findStage.setResizable(false); // Fixed window size
        findStage.show();
        btn1.setOnAction((ActionEvent e) -> {
            String textString = mainarea.getText(); // Get the string of Notepad text field
            String tfString = tf1.getText(); // Get the string to find
            if (!tf1.getText().isEmpty()) {
                if (textString.contains(tfString)) {
                    // Find method
                    if (startIndex[0] == -1) {// not found
                        Alert alert1 = new Alert(Alert.AlertType.WARNING);
                        alert1.titleProperty().set("Report");
                        alert1.headerTextProperty().set("Nothing!");
                        alert1.show();
                    }
                    startIndex[0] = mainarea.getText().indexOf(tf1.getText(), startIndex[0]);
                    if (startIndex[0] >= 0 && startIndex[0] < mainarea.getText().length()) {
                        mainarea.selectRange(startIndex[0], startIndex[0] + tf1.getText().length());
                        startIndex[0] += tf1.getText().length();
                    }
                }
                if (!textString.contains(tfString)) {
                    Alert alert1 = new Alert(Alert.AlertType.WARNING);
                    alert1.titleProperty().set("Report");
                    alert1.headerTextProperty().set("Nothing！");
                    alert1.show();
                }
            } else if (tf1.getText().isEmpty()) {
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.titleProperty().set("Error");
                alert1.headerTextProperty().set("Text is empty!");
                alert1.show();
            }
        });

    }

    @FXML
    public void onmenusearch(ActionEvent actionEvent) {
//        TextInputDialog dialog = new TextInputDialog();
//        dialog.setTitle("Search");
//        dialog.setHeaderText(null);
//        dialog.setContentText("search content:");
//
//        Optional<String> result = dialog.showAndWait();
//        if (result.isPresent()) {
//
//        }
        final int[] startIndex = {0};

        HBox h1 = new HBox();
        h1.setPadding(new Insets(20, 5, 20, 5));
        h1.setSpacing(5);
        javafx.scene.control.Label lable1 = new javafx.scene.control.Label("Searching content");
        javafx.scene.control.TextField tf1 = new javafx.scene.control.TextField();
        h1.getChildren().addAll(lable1,tf1);

        VBox v1 = new VBox();
        v1.setPadding(new Insets(20, 5, 20, 10));
        javafx.scene.control.Button btn1 = new javafx.scene.control.Button("Next");
        v1.getChildren().addAll(btn1);

        HBox findRootNode = new HBox();
        findRootNode.getChildren().addAll(h1, v1);

        Stage findStage = new Stage();
        Scene scene1 = new Scene(findRootNode, 450, 90);
        findStage.setTitle("Search");
        findStage.setScene(scene1);
        findStage.setResizable(false); // Fixed window size
        findStage.show();
        btn1.setOnAction((ActionEvent e) -> {
            String textString = mainarea.getText(); // Get the string of Notepad text field
            String tfString = tf1.getText(); // Get the string to find
            if (!tf1.getText().isEmpty()) {
                if (textString.contains(tfString)) {
                    // Find method
                    if (startIndex[0] == -1) {// not found
                        Alert alert1 = new Alert(Alert.AlertType.WARNING);
                        alert1.titleProperty().set("Report");
                        alert1.headerTextProperty().set("Nothing!");
                        alert1.show();
                    }
                    startIndex[0] = mainarea.getText().indexOf(tf1.getText(), startIndex[0]);
                    if (startIndex[0] >= 0 && startIndex[0] < mainarea.getText().length()) {
                        mainarea.selectRange(startIndex[0], startIndex[0] + tf1.getText().length());
                        startIndex[0] += tf1.getText().length();
                    }
                }
                if (!textString.contains(tfString)) {
                    Alert alert1 = new Alert(Alert.AlertType.WARNING);
                    alert1.titleProperty().set("Report");
                    alert1.headerTextProperty().set("Nothing！");
                    alert1.show();
                }
            } else if (tf1.getText().isEmpty()) {
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.titleProperty().set("Error");
                alert1.headerTextProperty().set("Text is empty!");
                alert1.show();
            }
        });


    }

    @FXML
    void onmenupdf(ActionEvent actionEvent) {
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                FileOutputStream out = new FileOutputStream(file);
                com.itextpdf.text.Document document = new com.itextpdf.text.Document();
                String text = mainarea.getText();
                PdfWriter.getInstance(document,out);
                document.open();
                document.add(new Paragraph(text));
                document.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    @FXML
    void onmenuopenpdf(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File file = fileChooser.showOpenDialog(anchorpane.getScene().getWindow());
        if (file != null) {
            PdfReader reader = new PdfReader(String.valueOf(file));
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);
            StringBuffer buffer = new StringBuffer();
            TextExtractionStrategy strategy;
            for(int i = 1;i <= reader.getNumberOfPages();i++){
                strategy = parser.processContent(i,new SimpleTextExtractionStrategy());
                buffer.append(strategy.getResultantText());
            }
            mainarea.setText(buffer.toString());
//            Runtime.getRuntime().exec("rundll32 url.dll FileProtocolHandler"+file.getAbsolutePath());
        }
    }
    @FXML
    void onMenuView(ActionEvent event) {

    }

    @FXML
    void onMenuManage(ActionEvent event) {

    }

    @FXML
    void onMenuAbout(ActionEvent event) {
        //Create a message dialog box without a title
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //set the alert's title
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("Dinglong Zhang 19029922" +
                "\n" +
                "Haotian Dong 19029770");
        alert.showAndWait();

    }

    //Textarea has a ready-made copy, cut, paste method, which can be called directly
    @FXML
    void onMenuCopy(ActionEvent event) {
        mainarea.copy();
    }

    @FXML
    void onMenuCut(ActionEvent event) {
        mainarea.cut();
    }

    @FXML
    void onMenuPaste(ActionEvent event) {
        mainarea.paste();
    }

}