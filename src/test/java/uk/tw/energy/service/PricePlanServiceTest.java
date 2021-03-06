package uk.tw.energy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.tw.energy.SeedingApplicationDataConfiguration;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.PricePlan;

public class PricePlanServiceTest {

	private static final String PRICE_PLAN_ID = "price-plan-0";
	private static final String SMART_METER_ID = "smart-meter-0";

	private List<ElectricityReading> electricityReading = new ArrayList<>();

	private SeedingApplicationDataConfiguration seedingApplicationDataConfiguration = new SeedingApplicationDataConfiguration();
	private PricePlanService pricePlanService;

	private List<PricePlan> pricePlans = new ArrayList<>();

	@Mock
	private MeterReadingService meterReadingService;

	@BeforeEach
	public void setUp() {
		electricityReading.add(new ElectricityReading(
				LocalDate.of(2021, 03, 03).atStartOfDay(ZoneId.systemDefault()).toInstant(), new BigDecimal("100")));
		
		electricityReading.add(new ElectricityReading(
				LocalDate.of(2021, 03, 04).atStartOfDay(ZoneId.systemDefault()).toInstant(), BigDecimal.TEN));
		
		electricityReading.add(new ElectricityReading(
				LocalDate.of(2021, 02, 03).atStartOfDay(ZoneId.systemDefault()).toInstant(), BigDecimal.ONE));
		
		MockitoAnnotations.initMocks(this);
		pricePlans = seedingApplicationDataConfiguration.pricePlans();

		pricePlanService = new PricePlanService(pricePlans, meterReadingService);
	}

	@Test
	public void givenTheSmartMeterIdAndPlanReturnsTheCostUsageForLastWeekId() throws Exception {
		when(meterReadingService.getReadings(SMART_METER_ID)).thenReturn(Optional.of(electricityReading));
		assertThat(pricePlanService.getLastWeekMeterUsageCost(SMART_METER_ID, PRICE_PLAN_ID))
				.isEqualByComparingTo(new BigDecimal("20"));
	}
}
