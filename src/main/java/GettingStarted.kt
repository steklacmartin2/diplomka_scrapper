import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver


object GettingStarted {
    @Throws(InterruptedException::class)
    fun testGoogleSearch() {

        // Optional. If not specified, WebDriver searches the PATH for chromedriver.
        System.setProperty("webdriver.chrome.driver", "C:/ChromeDriver/chromedriver.exe")
        val driver: WebDriver = ChromeDriver()
        driver["https://www.csfd.sk/uzivatelia/"]
        Thread.sleep(5000) // Let the user actually see something!
        val searchBox = driver.findElement(By.name("q"))
        val acceptButtonCookies = driver.findElement(By.id("didomi-notice-agree-button"))
        acceptButtonCookies.click()

        val test = driver.findElement(By.xpath("//div[@class='box-content box-content-striped-articles']"))
        val test2 =  test.findElement(By.xpath(".//a[@class='user-title-name']")).click()

        val buttonDiv = driver.findElement((By.xpath("//div[@class='box-header-action']")))
        buttonDiv.findElement(By.xpath(".//a[@class='button']")).click()

        val numberOfPagesString = driver.findElement((By.xpath("//header[@class='box-header']//span"))).text.subSequence(startIndex = 1,
                endIndex = driver.findElement((By.xpath("//header[@class='box-header']//span"))).text.length - 1).filter { !it.isWhitespace() }

        val numberOfPages: Int = Integer.parseInt(numberOfPagesString.toString())

        for (page in 1..numberOfPages){
            val listRecenzii = driver.findElements(By.xpath(".//article[@class='article article-poster-88']//p"))
            for (el in listRecenzii) {
                println(el.text)
                println("_____________________________________________________________")
            }

            val listHodnoteni = driver.findElements(By.xpath(".//article[@class='article article-poster-88']//span[@class='star-rating']//span"))
            for (el in listHodnoteni) {
                println(el.getAttribute("class"))
                println("_____________________________________________________________")
            }

            val goNextpage = driver.findElement(By.xpath(".//div[@class='box-more-bar']//a[@class='page-next']")).click()
        }



//        val recenzieUzivatelaList = recenzieUzivatelaElement.findElements(By.className("article article-poster-88"))
//
//        for (i in recenzieUzivatelaList){
//            val text = i.findElement(By.xpath(".//p")).text
//            val ratingHolder = i.findElement(By.xpath(".//span[@class='star-rating']"))
////            val rating = ratingHolder.findElement(By.xpath(".//span[@class='stars stars-4']")).size
//
//        }


//        searchBox.sendKeys("ChromeDriver")
//        searchBox.submit()

        // Rozumím a přijímám
        Thread.sleep(5000) // Let the user actually see something!

//        driver.quit();
    }

    @Throws(InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        testGoogleSearch()
    }
}