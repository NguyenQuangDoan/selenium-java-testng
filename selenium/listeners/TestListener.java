package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("\n========================================");
        System.out.println("STARTING: " + result.getMethod().getMethodName());
        System.out.println("========================================");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ PASSED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("\n❌ FAILED: " + result.getMethod().getMethodName());
        System.out.println("========================================");
        System.out.println("FAILURE REASON:");
        System.out.println(result.getThrowable().getMessage());
        System.out.println("========================================");
        System.out.println("STACK TRACE:");
        result.getThrowable().printStackTrace();
        System.out.println("========================================\n");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⚠️ SKIPPED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   TEST SUITE: " + context.getName());
        System.out.println("╚════════════════════════════════════════╝\n");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   TEST SUMMARY");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║   Total: " + context.getAllTestMethods().length);
        System.out.println("║   Passed: " + context.getPassedTests().size());
        System.out.println("║   Failed: " + context.getFailedTests().size());
        System.out.println("║   Skipped: " + context.getSkippedTests().size());
        System.out.println("╚════════════════════════════════════════╝\n");
    }
}
