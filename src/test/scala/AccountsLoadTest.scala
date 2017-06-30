import java.net.HttpURLConnection

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

import scala.concurrent.duration._


class AccountsLoadTest extends Simulation {
  val rampUpTimeSecs = 5
  val testTimeSecs = 60
  val noOfUsers = 200
  val noOfRequestPerSeconds = 1000

  val baseURL = "http://localhost:8080"
  val accountResourcePath = "/accounts"

  object CreatAndLoadAccountUSD {
    val create = exec(http("CreateAccountUSD")
      .post(accountResourcePath + "/")
      .body(RawFileBody("create_account_usd.json"))
      .asJSON
      .check(jsonPath("$.id").saveAs("accountId")))

    val load = exec(http("LoadAccountUSD")
      .get(accountResourcePath + "/${accountId}")
      .check(status.is(HttpURLConnection.HTTP_OK)))
  }

  object CreateAndLoadAccountBR {
    val create = exec(http("CreatAccountBR")
      .post(accountResourcePath + "/")
      .body(RawFileBody("create_account_br.json"))
      .asJSON
      .check(jsonPath("$.id").saveAs("accountId")))

    val load = exec(http("LoadAccountBR")
      .get(accountResourcePath + "/${accountId}")
      .check(status.is(HttpURLConnection.HTTP_OK)))
  }

  object CreatAndLoadAccountEUR {
    val create = exec(http("CreateAccountEUR")
      .post(accountResourcePath + "/")
      .body(RawFileBody("create_account_eur.json"))
      .asJSON
      .check(jsonPath("$.id").saveAs("accountId")))

    val load = exec(http("LoadAccountEUR")
      .get(accountResourcePath + "/${accountId}")
      .check(status.is(HttpURLConnection.HTTP_OK)))
  }

  object LoadAccountByCurrencies {
    val usd = exec(http("LAByCurrencyUSD")
      .get(accountResourcePath + "/currency/usd")
      .check(status.is(HttpURLConnection.HTTP_OK)))
    val br = exec(http("LAByCurrencyBR")
      .get(accountResourcePath + "/currency/br")
      .check(status.is(HttpURLConnection.HTTP_OK)))
    val eur = exec(http("LAByCurrencyEUR")
      .get(accountResourcePath + "/currency/eur")
      .check(status.is(HttpURLConnection.HTTP_OK)))
  }

  val httpProtocol = http
    .baseURL(baseURL)
    .acceptHeader("application/json")
    .userAgentHeader("Gatling")

  val testScenario = scenario("LoadTest")
    .during(testTimeSecs) {
      exec(
        CreatAndLoadAccountUSD.create,
        CreatAndLoadAccountUSD.load,
        CreatAndLoadAccountEUR.create,
        CreatAndLoadAccountEUR.load,
        CreateAndLoadAccountBR.create,
        CreateAndLoadAccountBR.load,
        LoadAccountByCurrencies.eur,
        LoadAccountByCurrencies.usd,
        LoadAccountByCurrencies.br
      )
    }

  setUp(
    testScenario
      .inject(atOnceUsers(noOfUsers)))
    .throttle(
      reachRps(noOfRequestPerSeconds) in (rampUpTimeSecs seconds),
      holdFor(testTimeSecs seconds))
    .protocols(httpProtocol)
}