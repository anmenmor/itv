package itv.vehicleTests;

import java.util.Date;

public class Test {

    private Date testDate;
    private boolean passed;

    public Test(Date testDate) {
        this.testDate = testDate;
        this.passed = false;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
