import nz.massey.assignment1.texteditor.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;


@ExtendWith(ApplicationExtension.class)
class ControllerTest {

    @BeforeAll
    public static void setup() throws Exception {
        ApplicationTest.launch(Main.class);
    }

    @Test
    void onMenuOpen(FxRobot robot) {
        robot.clickOn("#menu");
        robot.clickOn("#openfile");

    }

    @org.junit.jupiter.api.Test
    void onMenuSave(FxRobot robot) {
        robot.clickOn("#menu");
        robot.clickOn("#savefile");

    }

    @org.junit.jupiter.api.Test
    void onMenuSearch(FxRobot robot) {
        robot.clickOn("#mainarea").write("zxcvbnm");
        robot.clickOn("#search");
        robot.clickOn("#Search");
        robot.clickOn("#textfield1").write("xcv");
        robot.clickOn("#nextbutton");

    }
}