package uk.tw.energy.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeterReadings {

	private String smartMeterId;
	private List<ElectricityReading> electricityReadings;

}
