package scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import tools.Tools;

public class BuscarEnGooglePassed {
    
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
    
    @Test (priority = 1)
    public void RealizarBusqueda() {
        if(continuar) {
            try {
                Thread.sleep(2000);
                WebElement txtBuscar = driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div/div[1]/div/div[1]/input"));
                txtBuscar.clear();
                txtBuscar.sendKeys("@Var(Valor a buscar)");
                tools.screenshot("scripts", "BuscarEnGooglePassed", "RealizarBusqueda");
                tools.stop();
            }catch(Exception e) {
                tools.skipScript(e);
            }
        }else {
            tools.skipPreviousMethod();
        }
    }
    
}