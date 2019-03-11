package scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import tools.Tools;

public class BuscarEnGoogleFailed {
    
    WebDriver driver;
    Tools tools;
    boolean continuar = true;
    
    @Test (priority = 0)
    public void Configuracion() {
        tools = new Tools();
        try {
            driver = tools.init();
        } catch (Exception e) {
            continuar = false;
            tools.skipScript(e);
        }
    }
    
    @Test(priority = 1)
    public void RealizarBusqueda() {
        if(continuar) {
            try {
                Assert.assertEquals(driver.getTitle(), "Este título no es.");
                tools.stop();
            }catch(Exception e) {
                tools.skipScript(e);
            }
        }else {
            tools.skipPreviousMethod();
        }
    }
    
}