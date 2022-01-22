import com.github.doyaaaaaken.kotlincsv.dsl.context.WriteQuoteMode
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver



// https://github.com/doyaaaaaken/kotlin-csv
object GettingStarted {

    @Throws(InterruptedException::class)
    fun test() {

        // Optional. If not specified, WebDriver searches the PATH for chromedriver.
        System.setProperty("webdriver.chrome.driver", "C:/ChromeDriver/chromedriver.exe")
        val driver: WebDriver = ChromeDriver()
        driver["https://www.csfd.sk/uzivatelia/"]
        Thread.sleep(5000) // Let the user actually see something!
        val searchBox = driver.findElement(By.name("q"))
        val acceptButtonCookies = driver.findElement(By.id("didomi-notice-agree-button"))
        acceptButtonCookies.click()

        val svkAutoriDiv = driver.findElement(By.xpath("//div[@class='column column-40']"))
        val listAutorov = svkAutoriDiv.findElements(By.xpath(".//article[@class='article article-user article-user-60']//div[@class='article-user-content-left']//a"))
        var counter = 0
        val listUrl : MutableList<String> = mutableListOf()
        for (a in listAutorov){
            println(a.getAttribute("href"))
            listUrl.add(a.getAttribute("href"))
        }

        for (autor in listAutorov.indices){

            if (listUrl[counter].equals("https://www.csfd.sk/uzivatel/34562-flipper/") || listUrl[counter].equals("https://www.csfd.sk/uzivatel/12308-t2/")){
                counter += 1
                continue
            }

            if (counter == 3){
                driver.quit()
                return
            }



            //bohuzial kvoli DOM to musim robit jak uplny **** :)
            driver[listUrl[counter]]
            counter += 1



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


            for (page in 1..numberOfPagesX10){
                val listArticles = driver.findElements(By.xpath(".//article[@class='article article-poster-88']"))

                for (el in listArticles) {
                    val articleReview = el.findElement(By.xpath(".//p"))

                    if (el.findElements(By.xpath(".//span[@class='star-rating']//span")).isEmpty()){
                        continue
                    }

                    val articleRating = el.findElement(By.xpath(".//span[@class='star-rating']//span"))

                    if (articleReview == null || articleRating == null){
                        break
                    }
                    if (articleRating.getAttribute("class").equals("stars trash")) {
                        rating = "0"
                    }

                    if (articleRating.getAttribute("class").equals("stars stars-1")) {
                        rating = "1"
                    }
                    else if (articleRating.getAttribute("class").equals("stars stars-2")) {
                        rating = "2"
                    }
                    else if (articleRating.getAttribute("class").equals("stars stars-3")) {
                        rating = "3"
                    }
                    else if (articleRating.getAttribute("class").equals("stars stars-4")) {
                        rating= "4"
                    }
                    else if (articleRating.getAttribute("class").equals("stars stars-5")) {
                        rating= "5"
                    }

                    else if (articleRating.getAttribute("class").equals("stars stars-5")) {
                        rating= "5"
                    }

                    val rows = listOf(listOf(rating))
                    csvWriter().writeAll(rows, "rating_2.csv",  append = true)
                    println(rating)
                    println(articleReview.text)
                    println("_____________________________________________________________")

                    val rows2 = listOf(listOf(articleReview.text))
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
                    writer.writeAll(rows2, "review_2.csv",  append = true)
                }

                driver.findElement(By.xpath(".//div[@class='box-more-bar']//a[@class='page-next']")).click()
            }
            driver["https://www.csfd.sk/uzivatelia/"]


            println("som done")
            Thread.sleep(5000)
        }
        // Rozumím a přijímám
        Thread.sleep(5000) // Let the user actually see something!

        driver.quit();
    }




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

        val svkAutoriDiv = driver.findElement(By.xpath("//div[@class='column column-40']"))
        val listAutorov = svkAutoriDiv.findElements(By.xpath(".//article[@class='article article-user article-user-60']//div[@class='article-user-content-left']//a"))
        var counter = 0
        val listUrl : MutableList<String> = mutableListOf()
        for (a in listAutorov){
            println(a.getAttribute("href"))
            listUrl.add(a.getAttribute("href"))
        }

        for (autor in listAutorov.indices){

            if (listUrl[counter].equals("https://www.csfd.sk/uzivatel/34562-flipper/") || listUrl[counter].equals("https://www.csfd.sk/uzivatel/12308-t2/")){
                counter += 1
                continue
            }

            if (counter == 3){
                driver.quit()
                return
            }

            //bohuzial kvoli DOM to musim robit jak uplny **** :)
            driver[listUrl[counter]]
            counter += 1





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



        var review: MutableList<String> = mutableListOf()
        var ratingg: MutableList<String> = mutableListOf()
        var reviewAndRatingList: MutableList<String> = mutableListOf()
            var counterHodnoteni = 0
            var counterRecenzii = 0
            var ratingNotFound = false
            val ratingNotFoundIndex = mutableListOf<Int>()

        for (page in 1..numberOfPagesX10){
            val listRecenzii = driver.findElements(By.xpath(".//article[@class='article article-poster-88']//p"))
            val listHodnoteni = driver.findElements(By.xpath(".//article[@class='article article-poster-88']//span[@class='star-rating']//span"))

            if (listRecenzii.size != listHodnoteni.size){
                continue
            }

            for (el in listHodnoteni) {
                counterHodnoteni+=1

                if (el.getAttribute("class").equals("stars trash")) {
                    rating = "0"
                }

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

                else if (el.getAttribute("class").equals("stars stars-5")) {
                    rating= "5"
                }

                if (rating.equals("0") ||rating.equals("1") || rating.equals("2") || rating.equals("3") || rating.equals("4") || rating.equals("5")){
                    val rows = listOf(listOf(rating))
                    csvWriter().writeAll(rows, "rating_short.csv",  append = true)
                    println(rating)
                    println("_____________________________________________________________")

                }
                else {
                    ratingNotFound = true
                    ratingNotFoundIndex.add(counterHodnoteni)
                    println("prejeb")
                }


            }

            for (el in listRecenzii) {
                counterRecenzii += 1

                if (ratingNotFound){
                    if (ratingNotFoundIndex.contains(counterRecenzii)){
                        continue
                    }
                }

                println(el.text)
                review.add(el.text)
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
                writer.writeAll(rows, "review_short.csv",  append = true)
                println("_____________________________________________________________")
            }

            if (counterRecenzii != counterHodnoteni){
                println("Doslo ku chybe ")
                println(counterRecenzii)
                println(counterHodnoteni)
            }

            counterRecenzii = 0
            counterHodnoteni = 0
            ratingNotFound = false
            ratingNotFoundIndex.clear()

            driver.findElement(By.xpath(".//div[@class='box-more-bar']//a[@class='page-next']")).click()
        }
            driver["https://www.csfd.sk/uzivatelia/"]

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
        Thread.sleep(5000)
        }
        // Rozumím a přijímám
        Thread.sleep(5000) // Let the user actually see something!

        driver.quit();
    }



    @Throws(InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        test()
//        testGoogleSearch()
    }
}