import com.github.doyaaaaaken.kotlincsv.dsl.context.WriteQuoteMode
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import java.io.BufferedWriter
import java.nio.file.Paths

// https://github.com/doyaaaaaken/kotlin-csv
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
        var numberOfPagesX10 = numberOfPages / 10
        var rating = ""


        if (numberOfPages % 10 != 0){
            numberOfPagesX10 += 1
        }
//
//        var review: MutableList<String> = mutableListOf()
//        var ratingg: MutableList<String> = mutableListOf()

        for (page in 1..numberOfPagesX10){
            val listRecenzii = driver.findElements(By.xpath(".//article[@class='article article-poster-88']//p"))
            for (el in listRecenzii) {
                println(el.text)
//                review.add(el.text)
                val rows = listOf(listOf(el.text))
                val writer = csvWriter {
                    charset = "windows-1250"
                    delimiter = '\t'
                    nullCode = "NULL"
                    lineTerminator = "\n"
                    outputLastLineTerminator = true
                    quote {
                        mode = WriteQuoteMode.ALL
                        char = '\''
                    }
                }
                writer.writeAll(rows, "review.csv",  append = true)
                println("_____________________________________________________________")
            }

            val listHodnoteni = driver.findElements(By.xpath(".//article[@class='article article-poster-88']//span[@class='star-rating']//span"))
            for (el in listHodnoteni) {

                if (el.getAttribute("class").equals("stars stars-1")) {
                     rating = "1"
                }
                else if (el.getAttribute("class").equals("stars stars-2")) {
                     rating = "2"
                }
                else if (el.getAttribute("class").equals("stars stars-3")) {
                     rating = "3"
                }
                else if (el.getAttribute("class").equals("stars stars-4")) {
                     rating= "4"
                }
                else if (el.getAttribute("class").equals("stars stars-5")) {
                     rating= "5"
                }

//                ratingg.add(rating)

                val rows = listOf(listOf(rating))
                csvWriter().writeAll(rows, "rating.csv",  append = true)
                println("_____________________________________________________________")
            }
            driver.findElement(By.xpath(".//div[@class='box-more-bar']//a[@class='page-next']")).click()
        }
//        val rows = listOf(ratingg, review)
//        val writer = csvWriter {
//            charset = "windows-1250"
//            delimiter = '\t'
//            nullCode = "NULL"
//            lineTerminator = "\n"
//            outputLastLineTerminator = true
//            quote {
//                mode = WriteQuoteMode.ALL
//                char = '\''
//            }
//        }
//        writer.writeAll(rows, "test.csv", append = true)



//        searchBox.sendKeys("ChromeDriver")
//        searchBox.submit()

        println("som done")
        // Rozumím a přijímám
        Thread.sleep(5000) // Let the user actually see something!

        driver.quit();
    }

    @Throws(InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        testGoogleSearch()
    }
}