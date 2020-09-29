package nz.massey.assignment1.texteditor;

/**
 * @Description texteditor
 * @Author Dinglong Zhang
 * @Date 2020-09-28 19:56
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javax.jnlp.ClipboardService;
import javax.jnlp.FileContents;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.font.LayoutPath;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;

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
    void onMenuNew(ActionEvent event) {

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
    void onMenuSave(ActionEvent event) {

    }

    @FXML
    void onMenuTime(ActionEvent event) {

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

    }

    @FXML
    void onMenuCopy(ActionEvent event) {
        //Get the system clipboard
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        //Get selected content
        String text = mainarea.getSelectedText();
        content.putString(text);
        //Put selected content on clipboard
        clipboard.setContent(content);

    }

    @FXML
    void onMenuCut(ActionEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        String text = mainarea.getSelectedText();
        content.putString(text);
        clipboard.setContent(content);
        //Empty the textArea of the content that has been placed on the clipboard
        

    }

    @FXML
    void onMenuPaste(ActionEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        Transferable content = (Transferable) clipboard.getContent(null);
        if (content != null) {
            if (content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                if (mainarea.getSelectedText() != null) {
                    mainarea.replaceSelection(String.valueOf(content));
                } else {
                    int mouseposition = mainarea.getCaretPosition();
                    mainarea.insertText(mouseposition, String.valueOf(content));
                }
            }
        }
    }

}