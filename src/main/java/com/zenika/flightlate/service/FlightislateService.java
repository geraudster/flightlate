package com.zenika.flightlate.service;

import com.zenika.flightlate.domain.Flight;
import com.zenika.flightlate.domain.FlightPrediction;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.apache.spark.sql.functions.col;

@Service
@Transactional
public class FlightislateService {

    private final Logger log = LoggerFactory.getLogger(FlightislateService.class);
    private final static SparkSession spark;
    private final static PipelineModel model;

    static {
        spark = SparkSession.builder().appName("Airplane Predict").master("local[1]").getOrCreate();
        model = PipelineModel.load("/home/geraud/data/airplane-model");
    }

    public FlightPrediction predictFlight(Flight flight) {
        //'Year', 'Month', 'DayOfMonth', 'DayOfWeek', 'DepTime', 'UniqueCarrierIndex', 'FlightNumIndex', 'OriginIndex', 'DestIndex'
        Dataset<Row> flightDf = spark.createDataFrame(Arrays.asList(RowFactory.create(flight.getYear(),
            flight.getMonth(),
            flight.getDayOfMonth(),
            flight.getDayOfWeek(),
            flight.getDepTime(),
            flight.getUniqueCarrier().getCode(),
            flight.getFlightNum(),
            flight.getOrigin().getIata(),
            flight.getDest().getIata())),
            DataTypes.createStructType(Arrays.asList(
                DataTypes.createStructField("Year", DataTypes.IntegerType, true),
                DataTypes.createStructField("Month", DataTypes.IntegerType, true),
                DataTypes.createStructField("DayOfMonth", DataTypes.IntegerType, true),
                DataTypes.createStructField("DayOfWeek", DataTypes.IntegerType, true),
                DataTypes.createStructField("DepTime", DataTypes.IntegerType, true),
                DataTypes.createStructField("UniqueCarrier", DataTypes.StringType, true),
                DataTypes.createStructField("FlightNum", DataTypes.StringType, true),
                DataTypes.createStructField("Origin", DataTypes.StringType, true),
                DataTypes.createStructField("Dest", DataTypes.StringType, true)
            )));
        model.transform(flightDf).show();
        boolean prediction = model.transform(flightDf).select(col("prediction").cast(DataTypes.IntegerType)).first().getInt(0) == 1;
        FlightPrediction result = new FlightPrediction();
        result.setLate(prediction);
        return result;
    }
}
