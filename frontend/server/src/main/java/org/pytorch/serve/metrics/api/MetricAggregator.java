package org.pytorch.serve.metrics.api;

import org.pytorch.serve.metrics.format.prometheous.PrometheusMetricManager;
import org.pytorch.serve.util.ConfigManager;

public final class MetricAggregator {

    private MetricAggregator() {}

    public static void handleInferenceMetric(
            final String modelName, final String modelVersion, final String requestId) {
        ConfigManager configMgr = ConfigManager.getInstance();
        if (configMgr.isMetricApiEnable()
                && configMgr.getMetricsFormat().equals(ConfigManager.METRIC_FORMAT_PROMETHEUS)) {
            PrometheusMetricManager.getInstance().incInferCount(modelName, modelVersion, requestId);
        }
    }

    public static void handleInferenceMetric(
            final String modelName, final String modelVersion, final String requestId,
            long timeInQueue, long inferTime) {
        ConfigManager configMgr = ConfigManager.getInstance();
        if (configMgr.isMetricApiEnable()
                && configMgr.getMetricsFormat().equals(ConfigManager.METRIC_FORMAT_PROMETHEUS)) {
            PrometheusMetricManager metrics = PrometheusMetricManager.getInstance();
            metrics.incInferLatency(inferTime, modelName, modelVersion, requestId);
            metrics.incQueueLatency(timeInQueue, modelName, modelVersion, requestId);
        }
    }
}
