package com.znsio.reportPortal;

import com.intuit.karate.RuntimeHook;
import com.intuit.karate.Suite;
import com.intuit.karate.core.FeatureRuntime;
import com.intuit.karate.core.ScenarioRuntime;
import com.intuit.karate.core.Step;
import com.intuit.karate.core.StepResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KarateReportPortalHook implements RuntimeHook {
    // Overrides karate before/after methods to send results on report portal
    private RPReporter rpReporter;
    private static final Logger logger = LoggerFactory.getLogger(KarateReportPortalHook.class);

    public KarateReportPortalHook() {
        this.rpReporter = new RPReporter();
    }

    @Override
    public boolean beforeScenario(ScenarioRuntime sr) {
        return !this.rpReporter.isTemplate(sr.scenario);
    }

    @Override
    public void afterScenario(ScenarioRuntime sr) {

    }

    @Override
    public boolean beforeFeature(FeatureRuntime fr) {
        try {
            this.rpReporter.startFeature(fr.feature);
        } catch (Exception e) {
            logger.error("beforeFeature exception: {}", e.getMessage(), e);
        }

        return true;
    }

    @Override
    public void afterFeature(FeatureRuntime fr) {
        try {
            if (!this.rpReporter.isTemplate(fr.feature)) this.rpReporter.finishFeature(fr.result);
        } catch (Exception e) {
            logger.error("afterFeature exception: {}", e.getMessage(), e);
        }
    }

    @Override
    public void beforeSuite(Suite suite) {
        try {
            this.rpReporter.startLaunch();
        } catch (Exception e) {
            logger.error("beforeSuite exception: {}", e.getMessage(), e);
        }
    }

    @Override
    public void afterSuite(Suite suite) {
        try {
            this.rpReporter.finishLaunch(suite);
        } catch (Exception e) {
            logger.error("afterSuite exception: {}", e.getMessage(), e);
        }
    }

    @Override
    public boolean beforeStep(Step step, ScenarioRuntime sr) {
        return true;
    }

    @Override
    public void afterStep(StepResult result, ScenarioRuntime sr) {
    }
}