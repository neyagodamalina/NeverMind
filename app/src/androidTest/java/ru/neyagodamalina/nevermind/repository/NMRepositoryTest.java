package ru.neyagodamalina.nevermind.repository;

import android.util.Log;

import org.junit.Rule;
import org.junit.Test;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import ru.neyagodamalina.nevermind.InitDatabaseForTest;
import ru.neyagodamalina.nevermind.LiveDataTestUtil;
import ru.neyagodamalina.nevermind.util.Constants;

import static org.junit.Assert.*;

public class NMRepositoryTest  extends InitDatabaseForTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void startTask() {
        NMRepository repository = new NMRepository(database);
        try {
            Log.d(Constants.LOG_TAG, "Count projects = " + LiveDataTestUtil.getValue(repository.getAllProjects()).size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stopTask() {
    }
}