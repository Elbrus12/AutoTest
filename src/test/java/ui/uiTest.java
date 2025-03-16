package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;



public class uiTest {
    private WebDriver driver;

    // Конфигурации перед прогон тест-кейса
    @BeforeEach
    public void setUp() throws InterruptedException{
        //Путь до драйвера, настройка окна
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1400,800));
    }

    // Кейс с бронированием номера
    @Test
    public void testSendBook() throws InterruptedException {
        // Переходим на главную страницу хрома
        driver.get("https://automationintesting.online/");

        // Ожидаем загрузки страницы (опционально)
        Thread.sleep(2000);

        //Кликаем на предложение
        driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div/div[3]/button")).click();

        // Ожидаем загрузки страницы (опционально)
        Thread.sleep(2000);

        //Выбираем дату которая 10
        driver.findElement(By.cssSelector("body > div:nth-child(1) > div > div:nth-child(4) > div > div:nth-child(2) > div.col-sm-6 > div > div.rbc-month-view > div:nth-child(4) > div.rbc-row-content > div > div:nth-child(2) > button"))
                .click();

        // Ожидаем загрузки страницы (опционально)
        Thread.sleep(2000);

        // Firstname вводим
        driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[2]/div[3]/div[1]/input"))
                .sendKeys("Test");


        // Lastname вводим
        driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[2]/div[3]/div[2]/input"))
                .sendKeys("Testov");

        // Email ввод
        driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[2]/div[3]/div[3]/input"))
                .sendKeys("Test@test.com");

        // Вводим Phone
        driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[2]/div[3]/div[4]/input"))
                .sendKeys("888888888888");

        // Нажимаем на Book
        driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[2]/div[3]/button[2]"))
                .click();

        // Ожидаем загрузки страницы (опционально)
        Thread.sleep(2000);

    }

    // Кейс по отправки обратной связи
    @Test
    public void testSendSubmit() throws InterruptedException {
        // Переходим на главную страницу хрома
        driver.get("https://automationintesting.online/");

        // Ожидаем загрузки страницы (опционально)
        Thread.sleep(2000);

        // Делаем скрол до нужного элемента
        WebElement element = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);

        // Ожидаем загрузки страницы (опционально)
        Thread.sleep(2000);

        // Заполняем форму отправки и нажимает отправить
        driver.findElement(By.cssSelector("#name"))
                .sendKeys("Test");

        driver.findElement(By.cssSelector("#email"))
                .sendKeys("Test@test.com");

        driver.findElement(By.cssSelector("#phone"))
                .sendKeys("88888888");

        driver.findElement(By.cssSelector("#subject"))
                .sendKeys("ExampleText@mail.ru");

        driver.findElement(By.cssSelector("#description"))
                .sendKeys("Example Text, <LSQFQSXG!@111242141 1222121           24444   sss fff ee [[");

        driver.findElement(By.cssSelector("#submitContact")).click();

        Thread.sleep(2000);

    }

    // Кейс по телефонам, вход в учетную запись
    @Test
    public void testStoreShopLogIn() throws InterruptedException{
        // Открытие сайта
        driver.get("https://www.demoblaze.com/");

        // Таймер
        Thread.sleep(2000);

        //Login
        driver.findElement(By.xpath("/html/body/nav/div[1]/ul/li[5]/a")).click();

        // Таймер
        Thread.sleep(2000);

        // Заполнение логопасов
        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[1]/input"))
                .sendKeys("Test");

        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[2]/input"))
                .sendKeys("Test");

        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/button[2]"))
                .click();

        // Таймер
        Thread.sleep(2000);
    }

    // Кейс с покупкой телефона
    @Test
    public void testStoreShopGetPhone() throws InterruptedException{
        // Открытие сайта
        driver.get("https://www.demoblaze.com/");

        // Таймер
        Thread.sleep(2000);

        //Login
        driver.findElement(By.xpath("/html/body/nav/div[1]/ul/li[5]/a")).click();

        // Таймер
        Thread.sleep(2000);

        // Заполнение логопасов
        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[1]/input"))
                .sendKeys("Test");

        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[2]/input"))
                .sendKeys("Test");

        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/button[2]"))
                .click();

        // Таймер
        Thread.sleep(2000);

        // Проверяем наличие алерта и обрабатываем его
        try {
            Alert alert = driver.switchTo().alert();

            // Если нужно принять (нажать OK)
            alert.accept();

            // Или отменить (нажать Cancel)
            // alert.dismiss();

        } catch (NoAlertPresentException e) {
            System.out.println("Алерта не обнаружено.");
        }


        // Таймер
        Thread.sleep(2000);

        // Нажатия на нужный телефон
        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[1]/button/span")).click();

        // Таймер
        Thread.sleep(2000);

        driver.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/div[1]/div/div/h4/a")).click();

        // Таймер
        Thread.sleep(2000);

        // Переход к покупкам
        driver.findElement(By.xpath("/html/body/div[5]/div/div[2]/div[2]/div/a")).click();

        // Таймер
        Thread.sleep(2000);

        // Проверяем наличие алерта и обрабатываем его
        try {
            Alert alert = driver.switchTo().alert();

            // Если нужно принять (нажать OK)
            alert.accept();

            // Или отменить (нажать Cancel)
            // alert.dismiss();

        } catch (NoAlertPresentException e) {
            System.out.println("Алерта не обнаружено.");
        }

        // Таймер
        Thread.sleep(2000);

        // Переход к покупкам
        driver.findElement(By.xpath("/html/body/nav/div/div/ul/li[4]/a")).click();

        // Таймер
        Thread.sleep(2000);

        driver.findElement(By.xpath("/html/body/div[6]/div/div[2]/button")).click();

        // Таймер
        Thread.sleep(2000);

        // Заполнение данных для покупки
        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[1]/input")).sendKeys("Test");

        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[2]/input")).sendKeys("Test");

        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[3]/input")).sendKeys("Test");

        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[4]/input")).sendKeys("Test");

        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[5]/input")).sendKeys("11");

        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/form/div[6]/input")).sendKeys("2029");

        driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/button[2]")).click();

        // Таймер
        Thread.sleep(2000);

        driver.findElement(By.cssSelector("body > div.sweet-alert.showSweetAlert.visible > div.sa-button-container > div > button"))
                .click();

        // Таймер
        Thread.sleep(2000);

    }


    // Действия после тест-кейса
    @AfterEach
    public void tearDown() {
        // Закрываем браузер после завершения тестов
        driver.quit();
    }

}
