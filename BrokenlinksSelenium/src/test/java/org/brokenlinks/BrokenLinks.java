package org.brokenlinks;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrokenLinks {

	public static void main(String[] args) throws MalformedURLException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("http://demo.guru99.com/test/newtours/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Set<String> alllBrokenlinks = new HashSet<String>();

		List<WebElement> allLinks = driver.findElements(By.tagName("a"));
		System.out.println(allLinks.size());
		for (WebElement links : allLinks) {
			String brokenURL = links.getAttribute("href");
			try {
				URL url = new URL(brokenURL);
				URLConnection openConnection = url.openConnection();
				HttpURLConnection connection = (HttpURLConnection) openConnection;
				connection.setConnectTimeout(3000);
				connection.connect();

				if (connection.getResponseCode() != 200) {
					alllBrokenlinks.add(brokenURL);
				}

				connection.disconnect();
			} catch (IOException e) {
				driver.quit();
			}
		}

		for (String alllink : alllBrokenlinks) {
			System.err.println(alllink);
		}

	}

}
