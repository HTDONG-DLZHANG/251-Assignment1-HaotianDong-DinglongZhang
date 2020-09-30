package nz.massey.assignment1.texteditor;

/**
 * @Description texteditor
 * @Author Haotian Dong
 * @Date 2020-09-29 19:15
 */
import com.sun.corba.se.impl.ior.iiop.AlternateIIOPAddressComponentImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.nashorn.tools.Shell;

import javax.jnlp.ClipboardService;
import javax.jnlp.FileContents;
import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.font.LayoutPath;
import java.io.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
    void onMenuSave(ActionEvent event) throws IOException {
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

    }

    @FXML
    void onMenuExit(ActionEvent event) {

    }

    @FXML
    void onMenuSearch(ActionEvent event) {

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