import java.net.HttpURLConnection

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

import scala.concurrent.duration._


class AccountsLoadTest extends Simulation {
  val rampUpTimeSecs = 5
  val testTimeSecs = 30
  val noOfUsers = 300
  val noOfRequestPerSeconds = 1500

  val baseURL = "http://localhost:8080"
  val accountPostResourcePath = "/accounts"

  object CreateAccounts {
    val request = exec(http("CreateAccounts")
      .post(accountPostResourcePath + "/batch")
      .body(RawFileBody("accounts.json")).asJSON
      .check(status.is(HttpURLConnection.HTTP_OK)))
  }

  object LoadAccounts {
    val request = exec(http("LoadAccounts")
      .get(accountPostResourcePath + "/")
      .check(status.is(HttpURLConnection.HTTP_OK)))
  }

  val httpProtocol = http
    .baseURL(baseURL)
    .acceptHeader("application/json")
    .userAgentHeader("Gatling")

  val testScenario = scenario("LoadTest")
    .during(testTimeSecs) {
      exec(
        roundRobinSwitch(
          CreateAccounts.request,
          LoadAccounts.request
        )
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