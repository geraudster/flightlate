package com.zenika.flightlate.service;

import com.zenika.flightlate.config.ApplicationProperties;
import com.zenika.flightlate.domain.FlightPrediction;
import com.zenika.flightlate.domain.FlightView;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.types.DataTypes.*;

@Service
@Transactional
public class FlightislateService {

    private final Logger log = LoggerFactory.getLogger(FlightislateService.class);
    private final SparkSession spark;
    private final PipelineModel model;

    @Autowired
    public FlightislateService(ApplicationProperties properties) {
        log.info("Loading model at {}", properties.getModelLocation());
        spark = SparkSession.builder().appName("Airplane Predict").master("local[1]").getOrCreate();
        model = PipelineModel.load(properties.getModelLocation());
    }

    public FlightPrediction predictFlight(FlightView flightView) {
        //'Year', 'Month', 'DayOfMonth', 'DayOfWeek', 'DepTime', 'UniqueCarrierIndex', 'FlightNumIndex', 'OriginIndex', 'DestIndex'
        Dataset<Row> flightDf = spark.createDataFrame(Collections.singletonList(RowFactory.create(flightView.getYear(),
            flightView.getMonth(),
            flightView.getDayOfMonth(),
            flightView.getDayOfWeek(),
            flightView.getDepTime(),
            flightView.getUniqueCarrier(),
            flightView.getFlightNum(),
            flightView.getOrigin(),
            flightView.getDest())),
            createStructType(Arrays.asList(
                createStructField("Year", StringType, true),
                createStructField("Month", StringType, true),
                createStructField("DayOfMonth", StringType, true),
                createStructField("DayOfWeek", StringType, true),
                createStructField("DepTime", IntegerType, true),
                createStructField("UniqueCarrier", StringType, true),
                createStructField("FlightNum", StringType, true),
                createStructField("Origin", StringType, true),
                createStructField("Dest", StringType, true)
            )));
        flightDf.show();
        model.transform(flightDf).show();
        boolean prediction = model.transform(flightDf).select(col("prediction").cast(IntegerType)).first().getInt(0) == 1;
        FlightPrediction result = new FlightPrediction();
        result.setLate(prediction);
        return result;
    }
}
