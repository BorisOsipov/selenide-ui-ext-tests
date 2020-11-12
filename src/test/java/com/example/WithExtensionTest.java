package com.example;

import com.codeborne.selenide.Browser;
import com.codeborne.selenide.Config;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.webdriver.ChromeDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class WithExtensionTest {
    @Test
    void openWithChromeExtensionBySelenide() {
        Configuration.browserCapabilities = getChromeOptionsWithExtension();
        Configuration.browser = "chrome";

        open("https://www.cryptopro.ru/sites/default/files/products/cades/demopage/main.html");
        $(byText("ЭЦП в браузере – попробуйте прямо сейчас!")).should(visible);
    }

    @Test
    void openWithChromeExtensionByRemote() {
        Configuration.browserCapabilities = getChromeOptionsWithExtension();
        Configuration.browser = CustomChromeDriverFactory.class.getName();
        Configuration.remote = "http://localhost:4444/wd/hub";

        open("https://www.cryptopro.ru/sites/default/files/products/cades/demopage/main.html");
        $(byText("ЭЦП в браузере – попробуйте прямо сейчас!")).should(visible);
    }

    private ChromeOptions getChromeOptionsWithExtension() {
        String extPath = this.getClass().getClassLoader().getResource("chrome_cryptopro_ext.crx").getFile();
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File(extPath));
        return options;
    }
}

class CustomChromeDriverFactory extends ChromeDriverFactory {
    @Override
    protected String[] excludeSwitches() {
        return new String[]{"enable-automation"};
    }
}