package com.openxc;

import java.lang.InterruptedException;

import com.openxc.measurements.AcceleratorPedalPosition;
import com.openxc.measurements.BrakePedalStatus;
import com.openxc.measurements.HeadlampStatus;
import com.openxc.measurements.HighBeamStatus;
import com.openxc.measurements.FuelLevel;
import com.openxc.measurements.FuelConsumed;
import com.openxc.measurements.Odometer;
import com.openxc.measurements.FineOdometer;
import com.openxc.measurements.Latitude;
import com.openxc.measurements.Longitude;
import com.openxc.measurements.NoValueException;
import com.openxc.measurements.SteeringWheelAngle;
import com.openxc.measurements.PowertrainTorque;
import com.openxc.measurements.TransmissionGearPosition;
import com.openxc.measurements.VehicleButtonEvent;
import com.openxc.measurements.VehicleMeasurement;
import com.openxc.measurements.VehicleSpeed;
import com.openxc.measurements.UnrecognizedMeasurementTypeException;
import com.openxc.measurements.WindshieldWiperStatus;

import com.openxc.remote.RemoteVehicleService;

import com.openxc.remote.sources.trace.TraceVehicleDataSource;

import com.openxc.VehicleService;

import android.content.Intent;

import android.os.RemoteException;

import android.test.ServiceTestCase;

import android.test.suitebuilder.annotation.MediumTest;

public class MeasurementsTest extends ServiceTestCase<VehicleService> {
    VehicleService service;

    public MeasurementsTest() {
        super(VehicleService.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent startIntent = new Intent();
        startIntent.setClass(getContext(), VehicleService.class);
        startIntent.putExtra(RemoteVehicleService.DATA_SOURCE_NAME_EXTRA,
                TraceVehicleDataSource.class.getName());
        startIntent.putExtra(RemoteVehicleService.DATA_SOURCE_RESOURCE_EXTRA,
                "resource://" + R.raw.tracejson);
        service = ((VehicleService.VehicleServiceBinder)
                bindService(startIntent)).getService();
        // sleep for a moment to wait for the vehicle service to bind to the
        // remote service
        pause(200);
    }

    private void checkReceivedMeasurement(VehicleMeasurement measurement) {
        assertNotNull(measurement);
        assertFalse(measurement.isNone());
    }

    @MediumTest
    public void testGetSpeed() throws UnrecognizedMeasurementTypeException,
            NoValueException, RemoteException, InterruptedException {
        VehicleSpeed measurement = (VehicleSpeed)
                service.get(VehicleSpeed.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 42.0);
    }

    @MediumTest
    public void testGetSteeringWheelAngle()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        SteeringWheelAngle measurement = (SteeringWheelAngle)
                service.get(SteeringWheelAngle.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 94.1);
    }

    @MediumTest
    public void testGetPowertrainTorque()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        PowertrainTorque measurement = (PowertrainTorque)
                service.get(PowertrainTorque.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 232.1);
    }

    @MediumTest
    public void testGetOdometer()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        Odometer measurement = (Odometer) service.get(Odometer.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 124141.0);
    }

    @MediumTest
    public void testGetFineOdometer()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        FineOdometer measurement = (FineOdometer) service.get(
                FineOdometer.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 142.312423);
    }

    @MediumTest
    public void testGetFuelLevel()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        FuelLevel measurement = (FuelLevel)
                service.get(FuelLevel.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 71.2);
    }

    @MediumTest
    public void testGetFuelConsumed()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        FuelConsumed measurement = (FuelConsumed)
                service.get(FuelConsumed.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 81.2);
    }

    @MediumTest
    public void testGetAcceleratorPedalPosition()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        AcceleratorPedalPosition measurement = (AcceleratorPedalPosition)
                service.get(AcceleratorPedalPosition.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 14.0);
    }

    @MediumTest
    public void testGetLatitude() throws UnrecognizedMeasurementTypeException,
            NoValueException, RemoteException, InterruptedException {
        Latitude measurement = (Latitude) service.get(Latitude.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 45.123);
    }

    @MediumTest
    public void testGetLongitude() throws UnrecognizedMeasurementTypeException,
            NoValueException, RemoteException, InterruptedException {
        Longitude measurement = (Longitude) service.get(Longitude.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().doubleValue(), 120.442);
    }

    @MediumTest
    public void testGetWindshieldWiperStatus()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        pause(300);
        WindshieldWiperStatus measurement = (WindshieldWiperStatus)
                service.get(WindshieldWiperStatus.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().booleanValue(), true);
    }

    @MediumTest
    public void testGetBrakePedalStatus()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        pause(300);
        BrakePedalStatus measurement = (BrakePedalStatus)
            service.get(BrakePedalStatus.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().booleanValue(), false);
    }

    @MediumTest
    public void testGetHeadlampStatus()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        pause(300);
        HeadlampStatus measurement = (HeadlampStatus)
            service.get(HeadlampStatus.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().booleanValue(), true);
    }

    @MediumTest
    public void testGetHighBeamStatus()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        pause(300);
        HighBeamStatus measurement = (HighBeamStatus)
            service.get(HighBeamStatus.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().booleanValue(), false);
    }

    @MediumTest
    public void testGetTransmissionGearPosition()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        pause(300);
        TransmissionGearPosition measurement = (TransmissionGearPosition)
                service.get(TransmissionGearPosition.class);
        checkReceivedMeasurement(measurement);
        assertEquals(measurement.getValue().enumValue(),
                TransmissionGearPosition.GearPosition.FIRST);
    }

    @MediumTest
    public void testGetVehicleButtonEvent()
            throws UnrecognizedMeasurementTypeException, NoValueException,
            RemoteException, InterruptedException {
        pause(300);
        VehicleButtonEvent event = (VehicleButtonEvent)
                service.get(VehicleButtonEvent.class);
        checkReceivedMeasurement(event);
        assertEquals(event.getValue().enumValue(),
                VehicleButtonEvent.ButtonId.OK);
        assertEquals(event.getAction().enumValue(),
                VehicleButtonEvent.ButtonAction.PRESSED);
    }

    private void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch(InterruptedException e) {}
    }
}
