package uk.tw.energy.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import uk.tw.energy.service.AccountService;
import uk.tw.energy.service.PricePlanService;

@RestController
public class MeterUsageController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private PricePlanService pricePlanService;

	@GetMapping(value = "/usage/{meterId}")
	public ResponseEntity<BigDecimal> getUsage(@PathVariable("meterId") String meterId) {
		
		String plan = accountService.getPricePlanIdForSmartMeterId(meterId);
		if (plan == null || "".equals(plan)) {
			throw new RuntimeException("Plan not found for meter Id " + meterId);
		}

		return new ResponseEntity<>(pricePlanService.getLastWeekMeterUsageCost(meterId, plan), HttpStatus.OK);
	}

}
