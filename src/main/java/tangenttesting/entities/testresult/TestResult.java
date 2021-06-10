package main.java.tangenttesting.entities.testresult;
import main.java.tangenttesting.entities.Enums;
import main.java.tangenttesting.entities.TestEntity;

public class TestResult
{
    private TestEntity testData;
    private Enums.ResultStatus testStatus;
    private String errorMessage;
    private long testDuration;

    public TestResult(TestEntity testData, Enums.ResultStatus testStatus, String errorMessage, long testDuration)
    {
        this.testData = testData;
        this.testStatus = testStatus;
        this.errorMessage = errorMessage;
        this.testDuration = testDuration;
    }

    public void setTestEntity(TestEntity testdata)
    {
        this.testData = testdata;
    }

    public void setTestStatus(Enums.ResultStatus status)
    {
        this.testStatus = status;
    }

    public void setErrorMessage(String error)
    {
        this.errorMessage = error;
    }

    public void setTestDuration(long duration)
    {
        this.testDuration = duration;
    }

    public TestEntity getTestEntity()
    {
        return this.testData;
    }

    public Enums.ResultStatus getResultStatus()
    {
        return this.testStatus;
    }

    public String getErrorMessage()
    {
        return this.errorMessage;
    }

    public long getTestDuration()
    {
        return this.testDuration;
    }

    public String calculateFormattedTestTime() {
        String formattedTestDuration = "";
        long tSec;
        long tMin = 0;
        long tHours = 0;

        tSec = this.testDuration;

        if (tSec > 60) {
            while (tSec > 60) {
                tMin += 1;
                tSec -= 60;

            }
        }

        if (tMin > 60) {
            while (tMin > 60) {
                tHours += 1;
                tMin -= 60;
            }
        }

        if (tHours > 0) {
            formattedTestDuration += String.valueOf(tHours) + " Hour(s), ";
        }
        if (tMin > 0) {
            formattedTestDuration += String.valueOf(tMin) + " Minute(s), ";
        }
        if (tSec > 0) {
            formattedTestDuration += String.valueOf(tSec) + " Second(s)";
        }

        return formattedTestDuration;
    }
}
