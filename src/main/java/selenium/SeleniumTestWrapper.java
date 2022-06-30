package selenium;

import selenium.configurations.TestConfig;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import selenium.driver.WebDriverConfig;
import utils2.ScreenshotClass;
import utils2.WebDriverProvider;
import utils2.annotations.DisableCookies;
import utils2.annotations.RepeatRule;
import utils2.annotations.UserAgent;
import utils2.annotations.browser.Browser;
import utils2.annotations.browser.BrowserDimension;
import utils2.annotations.browser.Browsers;

import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;
public abstract class SeleniumTestWrapper{

	// Config
	protected static final TestConfig testConfig = new TestConfig();
	private final WebDriverConfig webDriverConfig = new WebDriverConfig();
	protected final WebDriverProvider webDriverProvider = new WebDriverProvider(this.webDriverConfig);

	private ScreenshotClass screenshotClass = new ScreenshotClass(getDriver());

	/*@Rule
	public TestRule testRule = new TestWatcher() {

		@Override
		public void testFailed(ExtensionContext context, Throwable cause) {
			screenshotClass.takeScreenShotWhenTestFails();
		}

		/*@Override
		public Statement apply(Statement base, Description description) {
			return super.apply(base, description);
		}

		@Override
		protected void testFailed(ExtensionContext context, Throwable cause) {
			screenshotClass.takeScreenShotWhenTestFails();
		}

		@Override
		protected void starting(final Description description) {
			//String methodName = description.getClassName() + "." + description.getMethodName();
			//this.webDriverBuilder.setName(methodName);
			super.starting(description);
		}

		@Override
		protected void finished(final Description description) {
			super.finished(description);
			if (getDriver() != null) {
				screenshotClass.quit();
			}
		}*/
	//};



	@Rule
	public RepeatRule repeatRule = new RepeatRule();

	protected WebDriver getDriver() {
		return this.webDriverProvider.getDriver();
	}

	/**
	 * test class annotations
	 */
	@Before
	public void setUserAgent(){
		UserAgent userAgent = this.getClass().getAnnotation(UserAgent.class);
		if (userAgent != null) {
			webDriverProvider.useUserAgent(userAgent.value());
		}
	}

	@Before
	public void disableCookies(){
		DisableCookies cookies = this.getClass().getAnnotation(DisableCookies.class);
		if (cookies != null) {
			webDriverProvider.disableCookies(true);
		}
	}

	@Before
	public void browser() throws Exception {
		Browser browser = this.getClass().getAnnotation(Browser.class);
		if (browser != null){
			if (browser.require().length > 0 && browser.skip().length == 0){
				String browsers = concatinateBrowsers(browser.require());
				assumeTrue("only execute test against " + browsers, browsers.contains(testConfig.getBrowser()));
			}

			if (browser.skip().length > 0 && browser.require().length == 0){
				String browsers = concatinateBrowsers(browser.skip());
				assumeFalse("skip test against " + browsers, browsers.contains(testConfig.getBrowser()));
			}
		}
	}

	private String concatinateBrowsers(Browsers[] browsers){
		String concatinatedBrowsers = "";
		for(Browsers browser : browsers) concatinatedBrowsers += browser.getValue() + " & ";
		return concatinatedBrowsers.substring(0,concatinatedBrowsers.lastIndexOf("&"));
	}

	@Before
	public void browserDimension(){
		BrowserDimension browserDimension = this.getClass().getAnnotation(BrowserDimension.class);
		if (browserDimension != null) {
			getDriver().manage().window().setSize(browserDimension.value().dimension);
		}
	}

	/*
	@After
	public void closeBrowser(){
		getDriver().quit();
	}*/

}
