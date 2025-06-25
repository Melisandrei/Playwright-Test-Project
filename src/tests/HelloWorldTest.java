import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    @Test
    public void testGoogleSearch() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            page.navigate("https://www.google.com");

            // Handle consent dialog if present
            try {
                Locator acceptButton = page.locator("button:has-text('Accept all'), button:has-text('I agree')");
                if (acceptButton.first().isVisible(new Locator.IsVisibleOptions().setTimeout(3000))) {
                    acceptButton.first().click();
                }
            } catch (PlaywrightException ignored) {}

            // Locate and use the search box
            Locator searchBox = page.locator("input[aria-label='Search']");
            if (searchBox.count() == 0) {
                System.out.println("Search box not found.");
                browser.close();
                return;
            }
            searchBox.first().click();
            searchBox.first().fill("Hello World");

            // Optionally click the search button if present
            Locator searchButton = page.locator("input[name='btnK']");
            if (searchButton.count() > 0) {
                searchButton.first().click();
            }

            browser.close();
        }
    }
}