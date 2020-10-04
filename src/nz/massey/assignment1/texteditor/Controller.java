package nz.massey.assignment1.texteditor;

/**
 * @Description texteditor
 * @Author Haotian Dong and Dinglong Zhang
 * @Date 2020-09-29 19:15
 */
import com.sun.corba.se.impl.ior.iiop.AlternateIIOPAddressComponentImpl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.nashorn.tools.Shell;

import javax.jnlp.ClipboardService;
import javax.jnlp.FileContents;
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.Button;
import java.awt.Label;
import java.awt.TextField;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.font.LayoutPath;
import java.io.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

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
    private MenuItem openfile;

    @FXML
    private MenuItem savefile;

    @FXML
    private MenuItem Timeanddate;

    @FXML
    private MenuItem printfile;

    @FXML
    private MenuItem Exit;

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

    private File file;
    @FXML
    void onMenuOpen(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File file = fileChooser.showOpenDialog(anchorpane.getScene().getWindow());
        if (file != null) {
            //Create a file input stream object
            FileInputStream in = new FileInputStream(file);
            byte[] ts = new byte[100000];
            in.read(ts);
            //Write the contents of the file to the TextArea
            mainarea.setText(new String(ts));
            in.close();
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
                DocPrintJob DPJ = service.createPrintJob();
                FileInputStream FIS = new FileInputStream(String.valueOf(mainarea));
            }catch (FileNotFoundException fe) {
                fe.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText("Print failed");
            alert.showAndWait();
        }

    }

    @FXML
    void onMenuExit(ActionEvent event) throws IOException {
        if (mainarea.getText().isEmpty()) {
            Platform.exit();
        }

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


    }


    @FXML
    public void onmenusearch(ActionEvent actionEvent) {
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
        findStage.setResizable(false); // 固定窗口大小
        findStage.show();
        btn1.setOnAction((ActionEvent e) -> {
            String textString = mainarea.getText(); // 获取记事本文本域的字符串
            String tfString = tf1.getText(); // 获取要查找的字符串
            if (!tf1.getText().isEmpty()) {
                if (textString.contains(tfString)) {
                    // 查找方法
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
//        TextInputDialog dialog = new TextInputDialog();
//        dialog.setTitle("Search");
//        dialog.setHeaderText(null);
//        dialog.setContentText("search content:");
//
//        Optional<String> result = dialog.showAndWait();
//        if (result.isPresent()) {
//
//        }

//        HBox hBox = new HBox();
//        hBox.setPadding(new Insets(20));
//        hBox.setSpacing(4);
//        Label label = new Label("search content:");
//        TextField tf = new TextField();
//        hBox.getChildren().addAll(label,tf);
//
//        VBox vBox = new VBox();
//        vBox.setPadding(new Insets(20));
//        Button btn = new Button("search next");
//        vBox.getChildren().add(btn);
//
//        HBox findrootnode = new HBox();
//        findrootnode.getChildren().addAll(hBox,vBox);
//
//        Stage stage = new Stage();
//        Scene scene = new Scene(findrootnode,450,90);
//        stage.setTitle("search");
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();

        
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
        alert.setContentText("Dinglong 19029922" +
                "\n" +
                "Haotian Dong");
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